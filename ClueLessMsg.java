import java.io.Serializable;

/*
 *
 * @author PD
 */
public class ClueLessMsg implements Serializable
{

    static final int MESSAGE = 0,   //client & Server text messages
            LOGOUT = 1,    //client
            WHOISIN = 2,   //client
            //JOINGAME = 3,  //This case might not be necessary since the players have joined the game and been assigned characters and cards when they connected to the server
            MOVE = 4, //client
            SUGGEST = 5,   //client
            ACCUSE = 6,    //client
            PROVE = 7,     //client
            YOURTURN = 8, //server to client. the msg is the an arraylist of integers, the first element indicates allowed actions, the following ones are allowed moves
            GAMEHISTORY = 9, //server to client, the msg is the game history AND the locations of game pieces to be updated in client's GUI. The two types of messages can be separated by a signal symbol.
            ERROR = 12,    //server
            STATE = 13;    //server
    //Thirteen comma delimited numbers for location
    //first number is the activeplayer

    private int type;
    private String message;
    private int playerID;
    private int locationID;
    private boolean isTurn;
    private String userName;


    ClueLessMsg(int type, String message)
    {
        this.type = type;
        this.message = message;
        //initialize playerID
        //initialize locationID
        isTurn = false;
    } //end constructor

    public int getType()
    {
        return type;
    } //end method getType

    public void setType(int type){
        this.type = type;
    }

    public String getMessage()
    {
        return message;
    } //end method getMessage

    public void setMessage(String msg) { this.message = msg; }

    public int getPlayerID()
    {
        return playerID;
    }

    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }

    public int getLocationID()
    {
        return locationID;
    }

    public void setLocationID(int newLoc)
    {
        this.locationID = newLoc;
    }

    public void setTurn(boolean turn) {this.isTurn = turn;}

    public boolean getTurn() { return isTurn; }

    public void setUserName(String name) { userName = name;}

    public String getUserName() { return  userName; }



} //end class ClueLessMsg

