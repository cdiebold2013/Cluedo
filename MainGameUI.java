import com.sun.javafx.fxml.expression.Expression;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;


/**
 * @author Hao Yu
 */

public class MainGameUI extends Application
{

    private static Circle[] charTokens;
    protected TextArea statusTA;
    protected BiMap cardIntMap;
    protected BiMap intCardMap;
    protected Label idLB;
    private Stage window;
    private ToggleGroup group1;
    private ToggleGroup group2;
    private Button disproveButton;
    private Button disproveButton2;
    private Button moveButton;
    private Scene scene1;
    private Scene scene2;
    private Circle[] charTokens2;
    protected HashMap charToTokenMap;
    protected HashMap tokenToCoordinates;
    private boolean connected;
    private Button joinButton;
    private TextField portTF;
    private TextField serverTF;
    private TextField userNameTF;
    private String character;
    private int playerID;
    private VBox cardsRBVBox;
    private int currLocation;
    private ClientManager client;
    private Player player;
    private HBox moveButtonHBox;
    protected Label characterLB;
    protected Group group;
    protected TextArea gameHistoryTA;

    public static void main(String[] args)
    {
        launch(args);
    }

    public static Circle[] getTokens()
    {
        return charTokens;
    }

    public void start(Stage primaryStage)
    {


        window = primaryStage;
        window.setTitle("Clueless");

        //For starting the game
        Label serverLB = new Label("Please enter server address:");
        serverTF = new TextField();
        serverTF.setText("127.0.0.1");
        serverTF.setMaxSize(120, 22);
        Label portLB = new Label("Please enter port number:");
        portTF = new TextField();
        portTF.setText("1500");
        portTF.setMaxSize(60, 22);
        Label userNameLB = new Label("Please enter your username:");
        userNameTF = new TextField();
        userNameTF.setMaxSize(120, 22);
        VBox serverVB = new VBox();
        serverVB.setSpacing(5);
        serverVB.setAlignment(Pos.TOP_CENTER);
        serverVB.getChildren().addAll(serverLB, serverTF, portLB, portTF, userNameLB, userNameTF);


        joinButton = new Button("Join game");
        joinButton.setOnAction(event -> {
            //connect server
            String username = userNameTF.getText().trim();
            String server = serverTF.getText().trim();
            if (server.length() == 0) {
                AlertBox.display("Please enter a server address.");
                return;
            }
            // empty or invalid port numer, ignore it
            String portNumber = portTF.getText().trim();
            if (portNumber.length() == 0) {
                AlertBox.display("Please enter a port number.");
                return;
            }
            if (username.length() == 0) {
                AlertBox.display("Please enter a user name.");
                return;
            }
            int port;
            try {
                port = Integer.parseInt(portNumber);
            } catch (Exception en) {
                return;   // nothing I can do if port number is not valid
            }
            // try creating a new Client with this GUI
            client = new ClientManager(server, port, username, this);
            // test if we can start the Client
            if (!client.start()) {
                AlertBox.display("Error connecting the server.");
                return;
            }
            connected = true;

            // disable login button
            joinButton.setDisable(true);
            serverTF.setEditable(false);
            portTF.setEditable(false);
            userNameTF.setEditable(false);
            //window.setScene(scene2);
        });

        Button startButton = new Button("Start game");
        startButton.setDisable(false);
        startButton.setOnAction(event -> {

            player = client.getPlayer();
            player.setStarted(true);
            client.sendMessage(player);
           startButton.setDisable(true);
            window.setScene(scene2);

        });

        Label statusLB = new Label("Connection status:");
        statusTA = new TextArea();


        VBox box = new VBox();
        box.setPadding(new Insets(25));
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.getChildren().addAll(serverVB, joinButton, startButton, statusLB, statusTA);

        scene1 = new Scene(box, 300, 450);


        //main game scene

        setCharTokens();
        //setCharTokens2();

        CharacterTokens map1 = new CharacterTokens();
        charToTokenMap = map1.charToTokensMap();

        TokenLocation map2 = new TokenLocation();
        tokenToCoordinates = map2.gameBoardCoordinates();

        CardsIntMap map3 = new CardsIntMap();
        cardIntMap = map3.getCardToIntMap();
        intCardMap = map3.getIntToCardMap();

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));


        FlowPane leftStack = new FlowPane();
        leftStack.setOrientation(Orientation.VERTICAL);
        leftStack.setVgap(30);
        leftStack.setPadding(new Insets(10));
        leftStack.setPrefWrapLength(700);

        //Identity
        Label youAreLB = new Label("You are:");
        characterLB = new Label();
        HBox charHB = new HBox();
        charHB.setSpacing(10);     //set this label accordingly
        characterLB.setFont(Font.font("Verdana", 15));

        charHB.getChildren().addAll(youAreLB, characterLB);
        charHB.setAlignment(Pos.CENTER_LEFT);
        Label playerIDLB = new Label("Player ID:");
        idLB = new Label();

        HBox idHB = new HBox();
        idHB.getChildren().addAll(playerIDLB, idLB);
        idHB.setSpacing(10);//set this label accordingly
        idHB.setAlignment(Pos.CENTER_LEFT);
        HBox charBox = new HBox();
        charBox.setAlignment(Pos.CENTER_LEFT);
        charBox.setSpacing(30);
        charBox.getChildren().addAll(charHB, idHB);

        Separator leftSeparator1 = new Separator();
        VBox charSection = new VBox();
        charSection.setSpacing(30);
        charSection.getChildren().addAll(charBox, leftSeparator1);
        leftStack.getChildren().add(charSection);

        //Cards
        Label cardsLB = new Label("Your Cards");
        cardsLB.setFont(Font.font("Verdana", 15));

        cardsRBVBox = new VBox();
        group1 = new ToggleGroup();
        cardsRBVBox.setSpacing(8);
        cardsRBVBox.setVisible(false);

        /*ArrayList<Integer> cards = new ArrayList<>();
        cards.add(4);
        cards.add(8);
        cards.add(15);                                                          //display cards

        cardsRBVBox = setCardsRB(cards);*/



        Label turnStatus2LB = new Label("Your turn to disprove");
        disproveButton = new Button("Disprove");
        disproveButton.setOnAction(event ->                                     //disprove buttons
        {
                if (group1.getSelectedToggle() == null)
                    player.setDisprovedCard(-1);
                else {
                    int m = (Integer) group1.getSelectedToggle().getUserData();
                    System.out.println(m);
                }
            player.setDisproved(true);
            client.sendMessage(player);
            disproveButton.setDisable(true);

        });

        disproveButton2 = new Button("Unable to disprove");
        disproveButton2.setOnAction(event ->
        {
            player.setDisproved(true);
            player.setDisprovedCard(-1);
            client.sendMessage(player);
            disproveButton2.setDisable(true);
        });

        HBox disproveHB = new HBox();
        disproveHB.getChildren().addAll(turnStatus2LB, disproveButton, disproveButton2);
        disproveHB.setVisible(true);                                                    //when it's the player's turn to disprove


        disproveHB.setPadding(new Insets(5));
        disproveHB.setSpacing(20);
        disproveHB.setAlignment(Pos.CENTER_RIGHT);
        VBox cardsVB = new VBox();
        cardsVB.getChildren().addAll(cardsLB, cardsRBVBox, disproveHB);
        cardsVB.setAlignment(Pos.CENTER);
        cardsVB.setSpacing(20);

        Separator leftSeparator2 = new Separator();
        VBox cardsSection = new VBox();
        cardsSection.setSpacing(30);
        cardsSection.getChildren().addAll(cardsVB, leftSeparator2);
        leftStack.getChildren().add(cardsSection);

        //Make suggestion and make accusation
        Label suggOrAccuLB = new Label("Suggestion or Accusation");
        suggOrAccuLB.setFont(Font.font("Verdana", 15));
        ComboBox<String> characterCB = new ComboBox<>();
        characterCB.setPrefSize(100, 22);
        characterCB.getItems().addAll("Miss Scarlet", "Col Mustard", "Mrs White", "Mr Green", "Mrs Peacock", "Prof Plum");

        ComboBox<String> weaponCB = new ComboBox<>();
        weaponCB.setPrefSize(100, 22);
        weaponCB.getItems().addAll("Rope", "Lead pipe", "Knife", "Wrench", "Candlestick", "Revolver");

        ComboBox<String> roomCB = new ComboBox<>();
        roomCB.setPrefSize(100, 22);
        roomCB.getItems().addAll("Lounge", "Dining room", "Kitchen", "Hall", "Billiard room", "Ballroom", "Study", "Library", "Conservatory");

        Label charLB2 = new Label("Character:");
        Label weaponLB2 = new Label("Weapon:");
        Label roomLB2 = new Label("Room:");

        VBox charVB2 = new VBox();
        charVB2.setSpacing(8);
        VBox weaponVB2 = new VBox();
        weaponVB2.setSpacing(8);
        VBox roomVB2 = new VBox();
        roomVB2.setSpacing(8);

        charVB2.getChildren().addAll(charLB2, characterCB);
        weaponVB2.getChildren().addAll(weaponLB2, weaponCB);
        roomVB2.getChildren().addAll(roomLB2, roomCB);

        HBox suggAccuHB = new HBox();
        suggAccuHB.getChildren().addAll(charVB2, weaponVB2, roomVB2);
        suggAccuHB.setSpacing(20);

        Button makeSuggButton = new Button("Make suggestion");
        makeSuggButton.setDisable(false);                               //set this property accordingly

        makeSuggButton.setOnAction(event ->                           //button for making suggestion
        {
           if(verifyComboBoxInput(characterCB, weaponCB, roomCB)) {
               ArrayList<Integer> values = getSuggAccuValues(characterCB, weaponCB, roomCB);
               //validate room
               int currLocation = player.getLocationID();
               if (currLocation == values.get(2))
                   AlertBox.display("The suggested room has to be the room you are in.");
               else {
                   player.setSuggested(true);
                   player.setSuggestoin(values);
                   client.sendMessage(player);
                   makeSuggButton.setDisable(true);

               }
           }  //send player object

            else
                AlertBox.display("Please select a value to suggest.");

        });

        Button makeAccuButton = new Button("Make accusation");
        makeAccuButton.setDisable(false);
        makeAccuButton.setOnAction(event ->
        {
            if(verifyComboBoxInput(characterCB, weaponCB, roomCB)) {
                ArrayList<Integer> values = getSuggAccuValues(characterCB, weaponCB, roomCB);
                player = client.getPlayer();
                    player.setAccused(true);
                    player.setAccusation(values);
                client.sendMessage(player);
                makeAccuButton.setDisable(true);  //making accusation

            }
            else
                AlertBox.display("Please select a value to accuse.");
        });

        HBox suggAccuButtonHB = new HBox();
        suggAccuButtonHB.getChildren().addAll(makeSuggButton, makeAccuButton);
        suggAccuButtonHB.setSpacing(50);
        suggAccuButtonHB.setAlignment(Pos.CENTER);
        Separator leftSeparator4 = new Separator();
        VBox suggAccuVB = new VBox();
        suggAccuVB.getChildren().addAll(suggOrAccuLB, suggAccuHB, suggAccuButtonHB, leftSeparator4);
        suggAccuVB.setAlignment(Pos.CENTER);
        suggAccuVB.setSpacing(30);
        leftStack.getChildren().add(suggAccuVB);

        //Possible moves
        moveButton = new Button("Make move");
        moveButtonHBox = new HBox();
        moveButtonHBox.setAlignment(Pos.CENTER);
        moveButton.setOnAction(e ->{
            //send player object
        });
        ArrayList<Integer> moves = new ArrayList<>();                            //extract moves from player object
        moves.add(14);
        moves.add(20);
        moves.add(32);
        VBox movesSection = setPossibleMovesRB(moves, true);
        leftStack.getChildren().add(movesSection);
        pane.setLeft(leftStack);



        //middle section of the screen
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);

        //create game board
        FlowPane middleStack = new FlowPane();
        middleStack.setVgap(15);
        middleStack.setOrientation(Orientation.VERTICAL);
        middleStack.setPrefWrapLength(700);
        VBox midUpper = new VBox();
        midUpper.setPadding(new Insets(10));
        midUpper.setAlignment(Pos.CENTER);
        midUpper.setSpacing(5);
        Label gameBoardLB = new Label("Game Board");
        gameBoardLB.setFont(Font.font("Verdana", 15));
        Rectangle[] squares = createRooms();                                    //create rooms
        Line[] hLines = createHorizontalLines();
        Line[] vLines = createVerticalLines();
        group = new Group();
        for (Rectangle square : squares)
            group.getChildren().add(square);
        for (Line line : hLines)
            group.getChildren().add(line);
        for (Line line : vLines)
            group.getChildren().add(line);


        Text[] roomNames = new Text[9];
        roomNames[0] = new Text(3, 12, "Study");
        roomNames[1] = new Text(123, 12, "Hall");
        roomNames[2] = new Text(243, 12, "Lounge");
        roomNames[3] = new Text(3, 132, "Library");
        roomNames[4] = new Text(123, 132, "Billiard");
        roomNames[5] = new Text(243, 132, "Dining");
        roomNames[6] = new Text(0, 252, "Conservatory");
        roomNames[6].setFont(Font.font(10));
        roomNames[7] = new Text(123, 252, "Ball");
        roomNames[8] = new Text(243, 252, "Kitchen");

        for (Text room : roomNames)
            group.getChildren().add(room);

        midUpper.getChildren().addAll(gameBoardLB, group);

        VBox midLower = new VBox();
        midLower.setPadding(new Insets(10));
        Label gameHistoryLB = new Label("Game History");
        gameHistoryLB.setFont(Font.font("Verdana", 15));
       gameHistoryTA = new TextArea();                                               //Game history text area
        gameHistoryTA.setPrefSize(350, 250);
        gameHistoryTA.setEditable(false);
        midLower.getChildren().addAll(gameHistoryLB, gameHistoryTA);
        midLower.setAlignment(Pos.CENTER);
        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.VERTICAL);

        middleStack.getChildren().addAll(midUpper, midLower);
        middleStack.setPadding(new Insets(10));

        HBox middleSeg = new HBox();
        middleSeg.getChildren().addAll(separator, middleStack, separator2);
        pane.setCenter(middleSeg);

        FlowPane rightStack = new FlowPane();
        rightStack.setPadding(new Insets(20));
        rightStack.setVgap(15);
        rightStack.setOrientation(Orientation.VERTICAL);
        rightStack.setPrefWrapLength(700);
        Label noteBookLB = new Label("Detective Notebook");
        noteBookLB.setFont(Font.font("Verdana", 15));

        VBox nbCharacters = new VBox();
        nbCharacters.setAlignment(Pos.CENTER_LEFT);
        nbCharacters.setSpacing(5);
        Label nbCharactersLB = new Label("Characters:");
        nbCharacters.getChildren().add(nbCharactersLB);

        RadioButton[] charRB = new RadioButton[6];
        for (int i = 0; i < 6; i++)
            charRB[i] = new RadioButton();

        charRB[0].setText("Miss Scarlet");
        charRB[1].setText("Col Mustard");
        charRB[2].setText("Mrs White");
        charRB[3].setText("Mr Green");
        charRB[4].setText("Mrs Peacock");
        charRB[5].setText("Prof Plum");
        for (RadioButton rb : charRB)
            nbCharacters.getChildren().add(rb);

        VBox nbWeapons = new VBox();
        nbWeapons.setAlignment(Pos.CENTER_LEFT);
        nbWeapons.setSpacing(5);
        Label nbWeaponsLB = new Label("Weapons:");
        nbWeapons.getChildren().add(nbWeaponsLB);

        RadioButton[] weaponRB = new RadioButton[6];
        for (int i = 0; i < 6; i++)
            weaponRB[i] = new RadioButton();

        weaponRB[0].setText("Rope");

        weaponRB[1].setText("Lead pipe");

        weaponRB[2].setText("Knife");

        weaponRB[3].setText("Wrench");

        weaponRB[4].setText("Candlestick");

        weaponRB[5].setText("Revolver");
        for (RadioButton rb : weaponRB)
            nbWeapons.getChildren().add(rb);

        VBox nbRooms = new VBox();
        nbRooms.setAlignment(Pos.CENTER_LEFT);
        nbRooms.setSpacing(5);
        Label nbRoomsLB = new Label("Rooms:");
        nbRooms.getChildren().add(nbRoomsLB);

        RadioButton[] roomRB = new RadioButton[9];
        for (int i = 0; i < 9; i++)
            roomRB[i] = new RadioButton();

        roomRB[0].setText("Lounge");
        roomRB[1].setText("Dining room");
        roomRB[2].setText("Kitchen");
        roomRB[3].setText("Hall");
        roomRB[4].setText("Billiard room");
        roomRB[5].setText("Ballroom");
        roomRB[6].setText("Study");
        roomRB[7].setText("Library");
        roomRB[8].setText("Conservatory");
        for (RadioButton rb : roomRB)
            nbRooms.getChildren().add(rb);

        rightStack.getChildren().addAll(noteBookLB, nbCharacters, nbWeapons, nbRooms);
        rightStack.setAlignment(Pos.TOP_CENTER);
        pane.setRight(rightStack);

        scene2 = new Scene(pane);
        window.setScene(scene1);
        window.show();

    }

    public void setCardsRB(ArrayList<Integer> cards)
    {
        int numOfCards = cards.size();
        String[] cardsStr = new String[numOfCards];
        RadioButton[] cardsRB = new RadioButton[numOfCards];

        for (int i = 0; i < numOfCards; i++) {
            cardsStr[i] = (String) intCardMap.get(cards.get(i));
            cardsRB[i] = new RadioButton();
            cardsRB[i].setText(cardsStr[i]);
            cardsRB[i].setToggleGroup(group1);
            cardsRB[i].setUserData(cards.get(i));
        }

        HBox cardsRBHBox = new HBox();
        if (numOfCards > 4) {
            cardsRBHBox.setAlignment(Pos.CENTER_LEFT);
            cardsRBHBox.setSpacing(5);
            for (int i = 0; i < 4; i++)
                cardsRBHBox.getChildren().add(cardsRB[i]);
        } else {
            cardsRBHBox.setAlignment(Pos.CENTER_LEFT);
            cardsRBHBox.setSpacing(8);
            for (int i = 0; i < numOfCards; i++)
                cardsRBHBox.getChildren().add(cardsRB[i]);
        }
        HBox secondRowCardsRB = new HBox();
        secondRowCardsRB.setAlignment(Pos.CENTER_LEFT);
        secondRowCardsRB.setSpacing(8);
        if (numOfCards > 4) {
            for (int i = 4; i < numOfCards; i++)
                secondRowCardsRB.getChildren().add(cardsRB[i]);
            cardsRBVBox.getChildren().addAll(cardsRBHBox, secondRowCardsRB);
        } else cardsRBVBox.getChildren().add(cardsRBHBox);


    }

   /* public void setCards(ArrayList<Integer> cards){
        cardsRBVBox = setCardsRB(cards);

    }*/

    public VBox setPossibleMovesRB(ArrayList<Integer> moves, boolean ableToMove)
    {
        int numOfMoves = moves.size();
        String[] movesStr = new String[numOfMoves];
        Label possibleMovesLB = new Label("Possible Moves");
        possibleMovesLB.setFont(Font.font("Verdana", 15));
        HBox moveChoiceBox = new HBox();
        moveChoiceBox.setAlignment(Pos.CENTER);
        group2 = new ToggleGroup();
        RadioButton[] movesRB = new RadioButton[numOfMoves];
        for (int i = 0; i < numOfMoves; i++) {
            movesStr[i] = (String) intCardMap.get(moves.get(i));
            movesRB[i] = new RadioButton();
            movesRB[i].setToggleGroup(group2);
            movesRB[i].setText(movesStr[i]);
            movesRB[i].setUserData(moves.get(i));
        }
        for (RadioButton rb : movesRB)
            moveChoiceBox.getChildren().addAll(rb);

        moveButton = new Button("Make move");
        moveButton.setOnAction(event -> {
        });   //user action

        HBox makeMoveHB = new HBox();
        makeMoveHB.getChildren().add(moveButton);
        makeMoveHB.setAlignment(Pos.BOTTOM_CENTER);
        moveChoiceBox.setSpacing(6);
        moveChoiceBox.setPadding(new Insets(5));

        VBox movesVB = new VBox();
        movesVB.getChildren().addAll(possibleMovesLB, moveChoiceBox, makeMoveHB);
        movesVB.setAlignment(Pos.CENTER);
        movesVB.setVisible(ableToMove);  //when it's the player's turn to move
        movesVB.setSpacing(15);

        Separator leftSeparator3 = new Separator();
        VBox movesSection = new VBox();
        movesSection.setSpacing(30);
        movesSection.getChildren().addAll(movesVB, moveButtonHBox, leftSeparator3);
        return movesSection;

    }

    public void setCharTokens()
    {
        charTokens = new Circle[6];
        for (int i = 0; i < 6; i++) {
            charTokens[i] = new Circle();
            charTokens[i].setRadius(5);
            charTokens[i].setStroke(Color.BLACK);
            charTokens[i].setStrokeWidth(0.6);
        }
        charTokens[0].setFill(Color.RED);
        charTokens[1].setFill(Color.YELLOW);
        charTokens[2].setFill(Color.WHITE);
        charTokens[3].setFill(Color.GREEN);
        charTokens[4].setFill(Color.BLUE);
        charTokens[5].setFill(Color.PURPLE);
    }

    public void setCharTokens2()
    {
        charTokens2 = new Circle[6];
        for (int i = 0; i < 6; i++) {
            charTokens2[i] = new Circle();
            charTokens2[i].setRadius(5);
            charTokens2[i].setStroke(Color.BLACK);
            charTokens2[i].setStrokeWidth(0.8);
        }
        charTokens2[0].setFill(Color.RED);
        charTokens2[1].setFill(Color.YELLOW);
        charTokens2[2].setFill(Color.PURPLE);
        charTokens2[3].setFill(Color.GREEN);
        charTokens2[4].setFill(Color.WHITE);
        charTokens2[5].setFill(Color.BLUE);
    }

    private Rectangle[] createRooms()
    {
        Rectangle[] squares = new Rectangle[9];
        squares[0] = new Rectangle(0, 0, 60, 60);
        squares[1] = new Rectangle(120, 0, 60, 60);
        squares[2] = new Rectangle(240, 0, 60, 60);
        squares[3] = new Rectangle(0, 120, 60, 60);
        squares[4] = new Rectangle(120, 120, 60, 60);
        squares[5] = new Rectangle(240, 120, 60, 60);
        squares[6] = new Rectangle(0, 240, 60, 60);
        squares[7] = new Rectangle(120, 240, 60, 60);
        squares[8] = new Rectangle(240, 240, 60, 60);
        for (int i = 0; i < 9; i++) {
            squares[i].setStroke(Color.BLACK);
            squares[i].setFill(null);
            squares[i].setStrokeWidth(1);
        }

        return squares;
    }


    private Line[] createHorizontalLines()
    {
        Line[] hLines = new Line[12];
        hLines[0] = new Line(60, 20, 120, 20);
        hLines[1] = new Line(60, 40, 120, 40);
        hLines[2] = new Line(180, 20, 240, 20);
        hLines[3] = new Line(180, 40, 240, 40);
        hLines[4] = new Line(60, 140, 120, 140);
        hLines[5] = new Line(60, 160, 120, 160);
        hLines[6] = new Line(180, 140, 240, 140);
        hLines[7] = new Line(180, 160, 240, 160);
        hLines[8] = new Line(60, 260, 120, 260);
        hLines[9] = new Line(60, 280, 120, 280);
        hLines[10] = new Line(180, 260, 240, 260);
        hLines[11] = new Line(180, 280, 240, 280);
        for (int i = 0; i < 12; i++) {
            hLines[i].setStroke(Color.BLACK);
            hLines[i].setStrokeWidth(1);
        }

        return hLines;
    }

    private Line[] createVerticalLines()
    {
        Line[] vLines = new Line[12];
        vLines[0] = new Line(20, 60, 20, 120);
        vLines[1] = new Line(40, 60, 40, 120);
        vLines[2] = new Line(140, 60, 140, 120);
        vLines[3] = new Line(160, 60, 160, 120);
        vLines[4] = new Line(260, 60, 260, 120);
        vLines[5] = new Line(280, 60, 280, 120);
        vLines[6] = new Line(20, 180, 20, 240);
        vLines[7] = new Line(40, 180, 40, 240);
        vLines[8] = new Line(140, 180, 140, 240);
        vLines[9] = new Line(160, 180, 160, 240);
        vLines[10] = new Line(260, 180, 260, 240);
        vLines[11] = new Line(280, 180, 280, 240);
        for (int i = 0; i < 12; i++) {
            vLines[i].setStroke(Color.BLACK);
            vLines[i].setStrokeWidth(1);
        }

        return vLines;
    }

    public void placeTokens(ArrayList<Integer> locations)
    {
       int num = locations.size();
        for (int i = 0; i < num; i++) {
            Coordinates coordinates = (Coordinates) tokenToCoordinates.get(locations.get(i));
            charTokens[i] = (Circle) charToTokenMap.get(i);
            int x = coordinates.getX();
            int y = coordinates.getY();
            charTokens[i].setCenterX(x);
            charTokens[i].setCenterY(y);
        }

        for (int i = 0; i < num; i++)
            group.getChildren().add(charTokens[i]);

    }

    public void connectionFailed()
    {
        joinButton.setDisable(false);
        userNameTF.setEditable(true);
        portTF.setEditable(true);
        serverTF.setEditable(true);
        connected = false;
    }

    public void setCharacter(String character)
    {
        this.character = character;
    }

    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }

    public int getCurrLocation()
    {
        return currLocation;
    }

    public void setCurrLocation(int currLocation)
    {
        this.currLocation = currLocation;
    }

    private ArrayList<Integer> getSuggAccuValues(ComboBox<String> characters, ComboBox<String> weapons, ComboBox<String> rooms)
    {
        ArrayList<Integer> list = new ArrayList<>();
        int ch = (Integer) cardIntMap.get(characters.getValue());

        int wp = (Integer) cardIntMap.get(weapons.getValue());
        int rm = (Integer) cardIntMap.get(rooms.getValue());
        System.out.println(rm);
        list.add(ch);
        list.add(wp);
        list.add(rm);
        return list;
    }

    private boolean verifyComboBoxInput(ComboBox<String> characters, ComboBox<String> weapons, ComboBox<String> rooms){
        if(characters.getValue() == null || weapons.getValue() == null || rooms.getValue() == null )
            return false;

        return true;
    }

    public VBox getCardsRBVBox()
    {
        return cardsRBVBox;
    }



}
