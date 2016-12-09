import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author PD
 * @author Hao
 * @version 1.5
 * 
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
  //  private Player player;

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
          //  Logger.getLogger(ServerManager.class.getName()).log(Level.SEVERE, null, ex);
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
    private synchronized void broadcast()
    {
        for (int i = clients.size(); i >= 0; i--) {
            Client ct = clients.get(i);
            ct.sendPlayerObj();
        } //end for i
    } //end method broadcast
    
    
     private synchronized void broadcastJoinedPlayer(String joinedPlayer)
    {
       
        for (int i = 0; i < clients.size(); i++) {
           
            Client ct = clients.get(i);
            
         //   gui.writeLog("UserName: " + ct.player.getUserName() + " JoinedPlayer: " + ct.player.getJoinedPlayer() );
            
            
           
            ct.player.setJoinedPlayer(joinedPlayer);
            ct.player.setJoin(true);
            
            gui.writeLog("Broadcast Method: Notifying " + ct.player.getUserName() + " that player " + ct.player.getJoinedPlayer() + " joined game");
            
            
            ct.sendPlayerObj();
            
            ct.player.setJoinedPlayer(null);
            ct.player.setJoin(false);
            
        } //end for i
    } //end method broadcast
    
    
    private synchronized void broadcastGameHistory(String GameHistoryUpdate)
    {
       
        for (int i = 0; i < clients.size(); i++) {
           
            Client ct = clients.get(i);
            
                  
           
            ct.player.setGameHistoryUpdate(GameHistoryUpdate);
           
            
            gui.writeLog("Broadcast Method: Game History " + ct.player.getGameHistoryUpdate());
            
            
            ct.sendPlayerObj();
            
            ct.player.setGameHistoryUpdate(null);
            
            
        } //end for i
    } //end method broadcastgameHistory
    
    private synchronized void broadcastInitialSetup ()
    {
       
        for (int i = 0; i < clients.size(); i++) {
           
            Client ct = clients.get(i);
           
                   
            ct.player.setInitialSetup(true);
            ct.player.setGamePieceLocations(game.gamePieceLocations());
            ct.player.setGameHistoryUpdate("Game Started");   
         //    ct.player.setCards(game.assignCards(ct.player.getPlayerID()));
            gui.writeLog("Broadcast Method: Initial Setup" + ct.player.getGameHistoryUpdate());
            
            
            ct.sendPlayerObj();
            
            ct.player.setGameHistoryUpdate(null);
            ct.player.setInitialSetup(false);
            ct.player.setStarted(false);
            
        } //end for i
    } //end method broadcastgameHistory
    
     
    private synchronized void sendCurrentPlayers (Client id){
        Client myCT = id;
        
        for(int i= 0; i<clients.size(); i++){
               if (myCT.id!=i) {
               
               Client ct = clients.get(i); 
               String joinedPlayer = ct.player.getUserName() + "," + ct.player.getPlayerID();
               
               myCT.player.setJoinedPlayer(joinedPlayer);
               myCT.player.setJoin(true); 
               myCT.sendPlayerObj();
               myCT.player.setJoinedPlayer(null);
               myCT.player.setJoin(false); 
               } //end if 
                
        } //end for loop
        
    } //end method sendCurrentPlayers
     
     

    private void notifyPlayer(Player player){

        for(int i= 0; i<clients.size(); i++){
            if(i== player.getPlayerID())
            {
                Client ct = clients.get(i);
                ct.sendPlayerObj();
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
        Player inPlayer; //incoming player from client
        int id;  //auto assigned character

        Client(Socket socket)
        {
           
            this.socket = socket;
            id = uniqueId++; //assign in order based upon connection order
            gui.writeLog("Socket " + socket.getLocalSocketAddress());
            try {
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                username = (String) input.readObject();
                gui.writeLog(username + " has connected as character " + id);

            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } //end try catch
            
             player = new Player(id, username);
            
        } //end client constructor socket argument

        @Override
        public void run()
        {
            boolean runFlag = true;
            
            
            //notify all clients that I joined the game
            broadcastJoinedPlayer(player.getUserName() + "," + player.getPlayerID()); 
           
            //notify me of all clients current joined to game
            sendCurrentPlayers(this);
            
            
            
           
            
            while (runFlag) {
                try {
                    inPlayer = (Player) input.readObject();
                } catch (IOException | ClassNotFoundException ex) {
                 //   Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                } //end try catch

                /* Process initial start of game */ 
                if(inPlayer.isStarted()) {broadcastInitialSetup();} 
               
               
                
                
                //Process an accusation 
                if (inPlayer.isAccused()) {
                  
                   ArrayList<Integer> accusation = inPlayer.getAccusation();                  
                   if (game.processAccusation(accusation.get(0), accusation.get(1), accusation.get(2))) {
                       //declare winner game is over
                       broadcastGameHistory("WINNER," + player.getPlayerID());
                   } else  {
                       //accused failed and set active flag for this player 
                       player.setActive(false);
                       broadcastGameHistory("ACCUSEFAILED," + player.getPlayerID());
                       
                       //set the turn for the nextplayer
                       int nextTurn;
                       if (id==clients.size()) {
                           nextTurn = 0;
                        
                       } else {
                           nextTurn =id + 1;
                       }
                       
                       //set isTurn on each player object
                       
                       
                   } //end if else
                   
                } //end if isAccused
                
                
                if(inPlayer.isMoved()) {
                    
                    
                    
                } //end if isMoved
                
                
                
                if(inPlayer.isSuggested()) {
                    
                    
                    
                } //end if isSuggested
                
                
                if(inPlayer.isDisproved()) {
                    
                    
                    
                } //end if isDisproved
                
                
                


                try {
                    close();
                } catch (IOException ex) {
                  // Logger.getLogger(ServerManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } // end run method

        private boolean sendPlayerObj()
        {
            if (!socket.isConnected()) {
                try {
                    close();
                } catch (IOException ex) {

                }
                return false;
            }
            try {
             //   gui.writeLog("sendPlayerObj Method: Notifying " + player.getUserName() + " that player " + player.getJoinedPlayer() + " joined game");
            
               
                output.writeObject(player);
                output.reset(); //Must have this or same msg is sent each time
            } catch (IOException e) {
                gui.writeLog("Exception writing to client: " + e);

            } //end try catch
            return true;
        } //end method sendMessage
        
        private Player getPlayerObj() {
            return player;
        }


        private void close() throws IOException
        {
            System.out.println("Closing connection " + player.getUserName());
            if (output != null) output.close();
            if (input != null) input.close();
            if (socket != null) socket.close();

        } //end method close

    } //end class Client

}
