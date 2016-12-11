import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Created by Hao on 12/6/2016.
 */
public class ClientManager
{
    private ObjectInputStream sInput;        // to read from the socket
    private ObjectOutputStream sOutput;        // to write on the socket
    private Socket socket;

    private MainGameUI gameUI;
    private Player player;

    // the server, the port and the username
    private String server, username;
    private int port;
    // private StringBuilder stringBuilder = new StringBuilder();
    private VBox cardsRBVBox;
    private VBox moveSections;

    /*
     *  Constructor called by console mode
     *  server: the server address
     *  port: the port number
     *  username: the username
     */
    public ClientManager(String server, int port, String username)
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
        // will send as a String. All other messages will be ClueLessMsg objects
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

    public void sendMessage(Player player)
    {
        try {
            sOutput.writeObject(player);
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

    public Player getPlayer()
    {
        return player;
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

                    player = (Player) sInput.readObject();
                    System.out.println(player.getUserName() + " is received");

                    //  System.out.println("Client has received player object from server");
                    if (player == null) {
                        System.out.print("> ");
                    } else {
                        //process the received player object here

                        /*
                        if some player joined the game. Update the UI
                         */
                        if (player.isJoin()) {
                            String username = player.getJoinedPlayer();

                            Platform.runLater(new Runnable()
                            {
                                @Override
                                public void run()
                                {

                                    gameUI.statusTA.appendText(username + " has joined the game.\n");
                                }
                            });

                            player.setJoin(false);
                        }
                        //all the other statuses:
                        else {
                            //if the game just starts, update character name, playeriD and cards
                            if (player.isInitialSetup()) {

                                int id = player.getPlayerID();
                                ArrayList<Integer> list = player.getCards();

                                    Platform.runLater(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {

                                            gameUI.characterLB.setText((String) gameUI.intCardMap.get(id));
                                            gameUI.idLB.setText(Integer.toString(id));
                                            gameUI.setCardsRB(list);
                                            cardsRBVBox = gameUI.getCardsRBVBox();
                                            cardsRBVBox.setVisible(true);
                                            gameUI.window.setScene(gameUI.scene2);

                                        }
                                    });
                                    player.setInitialSetup(false);
                                }
                            if (player.isTurn()) {

                                ArrayList<Integer> list = player.getAllowedActions();
                                //System.out.println(list.toString()
                                if(list != null) {
                                    if (list.get(list.size() - 1) == 1) {

                                        Platform.runLater(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                if (list.size() > 1) {
                                                    ArrayList<Integer> sublist = new ArrayList<Integer>(list.subList(0, list.size() - 1));
                                                    gameUI.setPossibleMovesRB(sublist);
                                                    moveSections = gameUI.getMovesSection();
                                                    moveSections.setVisible(true);

                                                } else gameUI.notAbleToMove.setVisible(true);
                                            }


                                        });
                                        player.setTurn(false);
                                    }
                                    if (list.get(list.size() - 1) == 0) {
                                        Platform.runLater(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                if (list.size() > 1) {
                                                    ArrayList<Integer> sublist = new ArrayList<Integer>(list.subList(0, list.size() - 1));
                                                    gameUI.setPossibleMovesRB(sublist);
                                                    moveSections = gameUI.getMovesSection();
                                                    moveSections.setVisible(true);

                                                } else gameUI.notAbleToMove.setVisible(true);
                                                gameUI.makeSuggButton.setDisable(false);
                                            }

                                        });
                                        player.setTurn(false);
                                    }
                                }
                            }

                            if (player.isTurnToDisprove()) {

                                Platform.runLater(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        gameUI.disproveButton.setDisable(false);
                                        gameUI.disproveButton.setOnAction(event ->                                     //disprove buttons
                                        {
                                            if (gameUI.group1.getSelectedToggle() == null)
                                                player.setDisprovedCard(-1);
                                            else {
                                                int m = (Integer) gameUI.group1.getSelectedToggle().getUserData();              //extract disproved card
                                                System.out.println("Card "+ m + "is disproved.");
                                            }
                                            player.setDisproved(true);
                                            sendMessage(player);


                                        });

                                        gameUI.disproveButton2 = new Button("Unable to disprove");
                                        gameUI.disproveButton2.setDisable(true);
                                        gameUI.disproveButton2.setOnAction(event ->
                                        {

                                            player.setDisproved(true);
                                            player.setDisprovedCard(-1);
                                            sendMessage(player);

                                        });
                                    }

                                });
                            }

                            if (player.isDisproved()) {

                            }
                            if (!player.isActive()) {

                                /*Platform.runLater(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        int id = player.getPlayerID();
                                        gameUI.charTokens[id].setVisible(false);

                                    }

                                });*/

                            }

                            //game board and game history
                            ArrayList<Integer> gamePieceLoc = player.getGamePieceLocations();
                            System.out.println(gamePieceLoc);
                            String gameUpdate = player.getGameHistoryUpdate();
                            System.out.println(gameUpdate);


                                Platform.runLater(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        gameUI.placeTokens(gamePieceLoc);
                                        gameUI.gameHistoryTA.appendText(gameUpdate + "\n");
                                    }
                                });

                            }

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