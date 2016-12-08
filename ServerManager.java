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
    private synchronized void broadcast(Player[] players)
    {

        for (int i = clients.size(); i >= 0; i--) {
            Client ct = clients.get(i);
            // try to write to the Client if it fails remove it from the list
            ct.sendPlayerObj(players[i]);
        } //end for i
    } //end method broadcast

    private void notifyPlayer(Player player){

        for(int i= 0; i<clients.size(); i++){
            if(i== player.getPlayerID())
            {
                Client ct = clients.get(i);
                ct.sendPlayerObj(player);
            }
        }
    }

    class Client extends Thread
    {

        Socket socket;
        ObjectInputStream input;
        ObjectOutputStream output;
        String username;
        Player player;
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
                    player = (Player) input.readObject();
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                } //end try catch


                /*
                processing the received player object here
                 */


                try {
                    close();
                } catch (IOException ex) {
                    Logger.getLogger(ServerManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } // end run method

        private boolean sendPlayerObj(Player player)
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
