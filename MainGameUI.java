
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

import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * @author Hao Yu
 *
 */

public class MainGameUI extends Application
{

    private Stage window;
    private ToggleGroup group1;
    private ToggleGroup group2;
    private Button disproveButton;
    private Button disproveButton2;
    private Button moveButton;
    private static Circle[] charTokens;
    private Label characterLB;
    private Label idLB;
    private Scene scene1;
    private Scene scene2;
    private Circle[] charTokens2;
    private HashMap charToTokenMap;
    private HashMap tokenToCoordinates;

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage primaryStage)
    {

        window = primaryStage;
        window.setTitle("Clueless");

        //For starting the game
        Label serverLB = new Label("Please enter server address:");
        TextField serverTF = new TextField();
        VBox serverVB = new VBox();
        serverVB.setSpacing(5);
        serverVB.getChildren().addAll(serverLB, serverTF);

        Button startButton = new Button("Start game");
        startButton.setDisable(true);
        startButton.setOnAction(event -> {
            window.setScene(scene2);

        });

        Button joinButton = new Button("Join game");
        joinButton.setOnAction(event -> {
            //connect server
            window.setScene(scene2);
        });

        VBox box = new VBox();
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        box.getChildren().addAll(serverVB, joinButton, startButton);
        scene1 = new Scene(box, 300, 200);


        //main game scene

        setCharTokens();
        setCharTokens2();
        CharacterTokens map1 = new CharacterTokens();
        charToTokenMap = map1.charToTokensMap();

        TokenLocation map2 = new TokenLocation();
        tokenToCoordinates = map2.gameBoardCoordinates();

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

        charHB.setSpacing(10);
        characterLB.setText("Miss Scarlet");   //set this label accordingly
        characterLB.setFont(Font.font("Verdana", 15));

        charHB.getChildren().addAll(youAreLB, characterLB, charTokens2[0]);
        charHB.setAlignment(Pos.CENTER_LEFT);
        Label playerIDLB = new Label("Player ID:");
        idLB = new Label();
        idLB.setText("1");
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
        String[] cards = {"Mrs White", "Col Mustard", "Prof Plum", "Rope", "Revolver", "Kitchen"}; //input cards information here
        VBox cardsRBVBox = setCardsRB(cards);
        Label turnStatus2LB = new Label("Your turn to disprove");

        disproveButton = new Button("Disprove");
        disproveButton.setOnAction(event -> {
        });

        disproveButton2 = new Button("Unable to disprove");
        disproveButton2.setOnAction(event -> {
        });

        HBox disproveHB = new HBox();
        disproveHB.getChildren().addAll(turnStatus2LB, disproveButton, disproveButton2);
        disproveHB.setVisible(true);                           //when it's the player's turn to disprove
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
        roomCB.getItems().addAll("Lounge", "Dining room", "Kitchen", "Hall", "Biiliard room", "Ballroom", "Study", "Library", "Conservatory");

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
        makeSuggButton.setDisable(true);                          //set this property accordingly
        makeSuggButton.setOnAction(event -> {
        });

        Button makeAccuButton = new Button("Make accusation");
        makeAccuButton.setDisable(true);
        makeAccuButton.setOnAction(event -> {
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
        String[] moves = {"Conservatory", "Hall-lounge", "Lounge-dining room"};
        VBox movesSection = setPossibleMovesRB(moves, false);
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
        Group group = new Group();
        for (Rectangle square : squares)
            group.getChildren().add(square);
        for (Line line : hLines)
            group.getChildren().add(line);
        for (Line line : vLines)
            group.getChildren().add(line);
        int[] charArray = {0, 1, 2, 3, 4, 5};
        int[] locationArray = {10, 17, 14, 13, 18, 15};
        placeTokens(charArray, locationArray);
        for (int i = 0; i < 6; i++)
            group.getChildren().add(charTokens[i]);
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
        TextArea gameHistoryTA = new TextArea();
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

    public VBox setCardsRB(String[] cards)
    {
        int numOfCards = cards.length;
        RadioButton[] cardsRB = new RadioButton[numOfCards];
        group1 = new ToggleGroup();
        for (int i = 0; i < numOfCards; i++) {
            cardsRB[i] = new RadioButton();
            cardsRB[i].setText(cards[i]);
            cardsRB[i].setToggleGroup(group1);
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
        } else secondRowCardsRB = null;
        VBox cardsRBVBox = new VBox();
        cardsRBVBox.setSpacing(8);
        cardsRBVBox.getChildren().addAll(cardsRBHBox, secondRowCardsRB);
        return cardsRBVBox;
    }

    public VBox setPossibleMovesRB(String[] moves, boolean ableToMove)
    {
        int numOfMoves = moves.length;
        Label possibleMovesLB = new Label("Possible Moves");
        possibleMovesLB.setFont(Font.font("Verdana", 15));
        HBox moveChoiceBox = new HBox();
        moveChoiceBox.setAlignment(Pos.CENTER);
        group2 = new ToggleGroup();
        RadioButton[] movesRB = new RadioButton[numOfMoves];
        for (int i = 0; i < numOfMoves; i++) {
            movesRB[i] = new RadioButton();
            movesRB[i].setToggleGroup(group2);
            movesRB[i].setText(moves[i]);
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
        movesSection.getChildren().addAll(movesVB, leftSeparator3);
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

    public static Circle[] getTokens()
    {
        return charTokens;
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

    private void placeTokens(int[] characters, int[] locations)
    {
        int num = characters.length;
        for (int i = 0; i < num; i++) {
            Coordinates coordinates = (Coordinates) tokenToCoordinates.get(locations[i]);
            charTokens[i] = (Circle) charToTokenMap.get(characters[i]);
            int x = coordinates.getX();
            int y = coordinates.getY();
            System.out.println(x + " " + y);
            charTokens[i].setCenterX(x);
            charTokens[i].setCenterY(y);
        }

    }
}
