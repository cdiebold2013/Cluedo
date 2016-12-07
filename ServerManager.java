import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Hao on 12/6/2016.
 */
public class ServerManager
{

    private final int port;
    private final ServerGUI gui;
    private boolean runFlag;
    private ServerSocket serverSocket;
    private ArrayList<Client> clients;
    private static int uniqueId;
    private Game game;

    public ServerManager(Integer port, ServerGUI gui)
    {
        this.port = port;
        this.gui = gui;
        clients = new ArrayList<Client>();
        game = new Game();
        uniqueId = 0;

    } //end constructor with port


    public void start()
    {
        gui.writeLog("Starting server on port " + port);
        runFlag = true; //keep running until false

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (runFlag) {
                gui.writeLog("Waiting for client connections...");
                Socket socket = serverSocket.accept();
                gui.writeLog("New Connection");

                if (!runFlag) break; //exit if the flag changes to false
                Client c = new Client(socket);
                gui.writeLog("added new client " + c);
                clients.add(c);
                c.start();
            } //end while
            //Close everything
            serverSocket.close();
            for (int i = 0; i < clients.size(); i++) {
                Client c = clients.get(i);
                c.input.close();
                c.output.close();
                c.socket.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerManager.class.getName()).log(Level.SEVERE, null, ex);
        } //end try catch

    } //end method start

    protected void stop() throws IOException
    {
        gui.writeLog("Stopping server");
        runFlag = false;
        Socket socket = new Socket("localhost", port);

    } //end method stop

    /**
     * This method is mainly used for updating game history. It sends all clients their individual ClueLessMsg object with type GAMEHISTORY.
     * @param players
     */
    private synchronized void broadcast(ClueLessMsg[] players)
    {

        for (int i = clients.size(); i >= 0; i--) {
            Client ct = clients.get(i);
            // try to write to the Client if it fails remove it from the list
            ct.sendPlayerObj(players[i]);
        } //end for i
    } //end method broadcast

    // for a client who logoff using the LOGOUT message
   /* This part may not be necessary since we don't have the quite option in our game (a quite option will greatly complicate the game logic)
   synchronized void remove(int id)
    {
        // scan the array list until we found the Id
        for (int i = 0; i < clients.size(); ++i) {
            Client ct = clients.get(i);
            // found it
            if (ct.id == id) {
                clients.remove(i);
                return;
            } //end if
        } //end for i
    } //end method remove*/


    class Client extends Thread
    {

        Socket socket;
        ObjectInputStream input;
        ObjectOutputStream output;
        String username;
        ClueLessMsg cm;
        int id;

        Client(Socket socket)
        {
            this.socket = socket;
            id = uniqueId++;
            gui.writeLog("Socket " + socket.getLocalSocketAddress());
            try {
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                username = (String) input.readObject();
                gui.writeLog(username + " has connected");

            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } //end try catch
        } //end client constructor socket argument

        @Override
        public void run()
        {
            boolean runFlag = true;
            while (runFlag) {
                try {
                    cm = (ClueLessMsg) input.readObject();

                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                } //end try catch

                String message = cm.getMessage();
               gui.writeLog("From " + username + " message= " + message);

               /* This part may not be necessary. The client side doesn't not need to be informed that the server got its msg
               try {
                    output.writeObject(new ClueLessMsg(ClueLessMsg.MESSAGE, "GOT YOUR MSG " + "   " + message + "\n"));
                    // break to do the disconnect
                } catch (IOException ex) {
                    Logger.getLogger(ServerManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                */



              /* Main processing for messages begins*/
                switch (cm.getType()) {
                    case ClueLessMsg.MOVE:
                        //process move
                        break;
                    case ClueLessMsg.ACCUSE:
                        gui.writeLog("Processing accuse");
                        List<Integer> list = new ArrayList<Integer>();
                        for (String s : cm.getMessage().split(","))
                            list.add(Integer.parseInt(s));
                        Boolean match = game.processAccusation(list.get(0), list.get(1), list.get(2));
                        if (!match) {
                            sendPlayerObj(new ClueLessMsg(ClueLessMsg.ACCUSE, "ACCUSATION FAILED" + "\n"));
                            gui.writeLog("Accusation Failed");
                        } else {
                            sendPlayerObj(new ClueLessMsg(ClueLessMsg.ACCUSE, "ACCUSATION SUCCESS" + "\n"));
                            gui.writeLog("ACCUSATION Success. We have a winner" + "\n");

                        }

                        break;

                    /*
                    Many more cases here
                     */


                    //This case might not be necessary since the players have joined the game and been assigned characters and cards when they started the game
                    //case ClueLessMsg.JOINGAME:
                    //int requestedCharacter = Integer.parseInt(cm.getMessage());
                        //see if it is available
                       // break;
                } //end switch

              /* Main processing for messages ends*/


            } //end while runFlag is true

            try {
                close();
            } catch (IOException ex) {
                Logger.getLogger(ServerManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } // end run method

        private boolean sendPlayerObj(ClueLessMsg player)
        {
            if (!socket.isConnected()) {
                try {
                    close();
                } catch (IOException ex) {

                }
                return false;
            }
            try {
                output.writeObject(player);
            } catch (IOException e) {
                gui.writeLog("Exception writing to client: " + e);

            } //end try catch
            return true;
        } //end method sendMessage


        private void close() throws IOException
        {

            if (output != null) output.close();
            if (input != null) input.close();
            if (socket != null) socket.close();

        } //end method close

    } //end class Client

}
