import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Hao on 12/6/2016.
 */
public class ClientManager
{
    private ObjectInputStream sInput;        // to read from the socket
    private ObjectOutputStream sOutput;        // to write on the socket
    private Socket socket;

    private MainGameUI gameUI;
    private ClueLessMsg clueMsg;

    // the server, the port and the username
    private String server, username;
    private int port;

    /*
     *  Constructor called by console mode
     *  server: the server address
     *  port: the port number
     *  username: the username
     */
    ClientManager(String server, int port, String username)
    {
        // which calls the common constructor with the GUI set to null
        this(server, port, username, null);
    }

    /*
     * Constructor call when used from a GUI
     * in console mode the ClienGUI parameter is null
     */
    ClientManager(String server, int port, String username, MainGameUI gameUI)
    {
        this.server = server;
        this.port = port;
        this.username = username;
        // save if we are in GUI mode or not
        this.gameUI = gameUI;
    }

    public boolean start()
    {
// try to connect to the server
        try {
            socket = new Socket(server, port);
        }
        // if it failed not much I can so
        catch (Exception ec) {
            displayMsg("Error connectiong to server:" + ec);
            return false;
        }

        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        displayMsg(msg);

		/* Creating both Data Stream */
        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            displayMsg("Exception creating new Input/output Streams: " + eIO);
            return false;
        }

        // creates the Thread to listen from the server
        new ListenFromServer().start();
        // Send our username to the server this is the only message that we
        // will send as a String. All other messages will be ChatMessage objects
        try {
            sOutput.writeObject(username);
        } catch (IOException eIO) {
            displayMsg("Exception doing login : " + eIO);
            disconnect();
            return false;
        }
        // success we inform the caller that it worked
        return true;
    }

    private void displayMsg(String msg)
    {
            System.out.println(msg);      // println in console mode

    }

    public void sendMessage(ClueLessMsg msg)
    {
        try {
            sOutput.writeObject(msg);
        } catch (IOException e) {
            displayMsg("Exception writing to server: " + e);
        }
    }

    /*
    * When something goes wrong
    * Close the Input/Output streams and disconnect not much to do in the catch clause
    */
    private void disconnect()
    {
        try {
            if (sInput != null) sInput.close();
        } catch (Exception e) {
        } // not much else I can do
        try {
            if (sOutput != null) sOutput.close();
        } catch (Exception e) {
        } // not much else I can do
        try {
            if (socket != null) socket.close();
        } catch (Exception e) {
        } // not much else I can do

        // inform the GUI
        if (gameUI != null)
           gameUI.connectionFailed();

    }

    /*
     * a class that waits for the message from the server and append them to the JTextArea
     * if we have a GUI or simply System.out.println() it in console mode
     */
    private class ListenFromServer extends Thread
    {

        public void run()
        {
            while (true) {
                try {

                    //String msg = (String) sInput.readObject();
                    clueMsg = (ClueLessMsg) sInput.readObject();

                    if (clueMsg == null) {
                        System.out.print("> ");
                    } else {
                        //code here needs to be modified to include reading the type in addition to message of the incoming object.
                    }
                } catch (IOException e) {
                   displayMsg("Server has close the connection: " + e);
                    if (gameUI != null)
                        gameUI.connectionFailed();
                    break;
                }
                // can't happen with a String object but need the catch anyhow
                catch (ClassNotFoundException e2) {
                }
            }
        }
    }


}
