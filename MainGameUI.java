
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class MainGameUI extends Application
{

    private Stage window;
    private ToggleGroup group1;
    private ToggleGroup group2;
    private Button disproveButton;
    private Button disproveButton2;
    private Button moveButton;
    private Circle[] charTokens;
    private Label characterLB;
    private Label idLB;
    private Scene scene1;
    private Scene scene2;

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
        startButton.setOnAction(event -> {window.setScene(scene2);});

        Button joinButton = new Button("Join game");
        joinButton.setOnAction(event -> {window.setScene(scene2);});

        VBox box = new VBox();
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        box.getChildren().addAll(serverVB, startButton, joinButton);
        scene1 = new Scene(box, 300, 200);



        //main game scene
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
        Circle[] tokens = setCharTokens(1);
        tokens[0].setFill(Color.RED);
        charHB.getChildren().addAll(youAreLB, characterLB, tokens[0]);
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
        String[] cards = {"Mrs White", "Col Mustard", "Prof Plum", "Rope", "Revolver", "Kitchen" }; //input cards information here
        VBox cardsRBVBox = setCardsRB(cards);
        Label turnStatus2LB = new Label("Your turn to disprove");

        disproveButton = new Button("Disprove");
             disproveButton.setOnAction(event -> {});

        disproveButton2 = new Button("Unable to disprove");
             disproveButton2.setOnAction(event -> {});

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
        makeSuggButton.setOnAction(event -> {});

        Button makeAccuButton = new Button("Make accusation");
        makeAccuButton.setDisable(true);
        makeAccuButton.setOnAction(event -> {});

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

        FlowPane middleStack = new FlowPane();
        middleStack.setVgap(15);
        middleStack.setOrientation(Orientation.VERTICAL);
        middleStack.setPrefWrapLength(700);
        VBox midUpper = new VBox();
        midUpper.setPadding(new Insets(10));

        Label gameBoardLB = new Label("Game Board:");
        Rectangle r = new Rectangle();
        r.setFill(Color.WHITE);
        r.setX(50);
        r.setY(50);
        r.setWidth(300);
        r.setHeight(300);

        midUpper.getChildren().addAll(gameBoardLB, r);

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
        middleSeg.setSpacing(10);
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
        }
        else {
            cardsRBHBox.setAlignment(Pos.CENTER_LEFT);
            cardsRBHBox.setSpacing(8);
            for (int i = 0; i < numOfCards; i++)
                cardsRBHBox.getChildren().add(cardsRB[i]);
        }
        HBox secondRowCardsRB = new HBox();
        secondRowCardsRB.setAlignment(Pos.CENTER_LEFT);
        secondRowCardsRB.setSpacing(8);
        if(numOfCards>4) {
            for (int i = 4; i < numOfCards; i++)
                secondRowCardsRB.getChildren().add(cardsRB[i]);
        }
        else secondRowCardsRB = null;
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
        moveButton.setOnAction(event -> {});   //user action

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

    public Circle[] setCharTokens(int num)
    {
        charTokens = new Circle[num];
        for(int i=0; i<num; i++)
        {
            charTokens[i] = new Circle();
            charTokens[i].setRadius(5);

            charTokens[i].setStroke(Color.BLACK);
            charTokens[i].setStrokeWidth(1);
        }
        return charTokens;
    }

    public HBox charNameToken(String name){
        return null;
    }

}
