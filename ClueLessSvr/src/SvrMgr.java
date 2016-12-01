
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
 * Class SvrMgr manages the server components of the clueless game
 * socket code based upon code from
 *      https://docs.oracle.com/javase/tutorial/networking/sockets/
 *      http://www.dreamincode.net/forums/topic/259777-a-simple-chat-program-
 *             with-clientserver-gui-optional/
 * @author PD
 * @version 1.0
 * 
 */
public class SvrMgr {
  
    private final int port;
    private final SvrMgrGUI gui;
    private boolean runFlag;
    private ServerSocket serverSocket;
    private ArrayList<Client> clients;
    private static int uniqueId;
    private Game game= new Game();
    public SvrMgr (Integer port, SvrMgrGUI gui ) {
       this.port = port;
       this.gui = gui;
       clients= new ArrayList<Client>(); 
      // game = new Game();
       
    } //end constructor with port 
    
    
    
   
    
    public void start() {
        gui.writeLog("Starting server on port " + port);
        runFlag=true; //keep running until false
       
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(runFlag) {              
               gui.writeLog("Waiting for client connections...");
               Socket socket = serverSocket.accept();
               gui.writeLog("New Connection");
               
               if (!runFlag)break; //exit if the flag changes to false
               Client c = new Client(socket);
               gui.writeLog("added new client " + c);
               clients.add(c);
               c.start();              
            } //end while
            //Close everything
            serverSocket.close();
            for (int i = 0; i < clients.size(); ++i) {
                Client c = clients.get(i);
                c.input.close();
                c.output.close();
                c.socket.close();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(SvrMgr.class.getName()).log(Level.SEVERE, null, ex);
        } //end try catch
       
    } //end method start

    protected void stop() throws IOException {      
        gui.writeLog("Stopping server");
        runFlag= false;
        Socket socket = new Socket("localhost", port);
        
    } //end method stop
    
    private synchronized void broadcast(ClueLessMsg msg) {
		gui.writeLog("Broadcasting " + msg.getMessage());     	
		
		for(int i = clients.size(); --i >= 0;) {
			Client ct = clients.get(i);
			// try to write to the Client if it fails remove it from the list
			if(!ct.sendMessage(msg)) {
				clients.remove(i);
				gui.writeLog("Client " + ct.username + " removed.");
			} //end if
		} //end for i
	} //end method broadcast

    // for a client who logoff using the LOGOUT message
    synchronized void remove(int id) {
            // scan the array list until we found the Id
            for(int i = 0; i < clients.size(); ++i) {
                    Client ct = clients.get(i);
                    // found it
                    if(ct.id == id) {
                            clients.remove(i);
                            return;
                    } //end if
            } //end for i
    } //end method remove
        
    
    class Client extends Thread {

       Socket socket;
       ObjectInputStream input;
       ObjectOutputStream output;
       String username;
       int character;
       String date;
       ClueLessMsg cm;
       int id;

       Client(Socket socket) {
          this.socket = socket;
          id = ++uniqueId;
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
        public void run() {
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

              try {
                  output.writeObject(new ClueLessMsg(ClueLessMsg.MESSAGE, "GOT YOUR MSG " + "   "  + message + "\n"));
                  // break to do the disconnect
              } catch (IOException ex) {
                  Logger.getLogger(SvrMgr.class.getName()).log(Level.SEVERE, null, ex);
              }

              
              
              /* Main processing for messages begins*/
              switch(cm.getType()) {
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
                          sendMessage(new ClueLessMsg(ClueLessMsg.ACCUSE, "ACCUSATION FAILED" + "\n"));
                          gui.writeLog("Accusation Failed");
                      } else {
                          sendMessage(new ClueLessMsg(ClueLessMsg.ACCUSE, "ACCUSATION SUCCESS" + "\n")); 
                          gui.writeLog( "ACCUSATION Success. We have a winner" + "\n");
                          
                      }
                      
                      break;
                  case ClueLessMsg.JOINGAME:
                      int requestedCharacter = Integer.parseInt(cm.getMessage());
                      //see if it is available
                      
                      
                      break;
              } //end switch
              
              /* Main processing for messages ends*/
              
              
              
          } //end while runFlag is true
          
          
          
          
          
          remove (id);
           try {
               close();
           } catch (IOException ex) {
               Logger.getLogger(SvrMgr.class.getName()).log(Level.SEVERE, null, ex);
           }
        } // end run method

        private boolean sendMessage(ClueLessMsg msg) {
            if(!socket.isConnected()) {
                try {
                    close();
                } catch (IOException ex) {
                    
                }
                    return false;
            }        
            try {
                    output.writeObject(msg);
            }
            catch(IOException e) {
                    gui.writeLog("Exception writing to client: " + e);
                    
            } //end try catch
            return true;
        } //end method sendMessage
    
        
        private void close() throws IOException {
           
            if(output != null) output.close();
            if(input != null) input.close();
            if(socket != null) socket.close();
           
	} //end method close

       
        
    } //end class Client
    
    
	
        
        
} //end class SvrMgr
