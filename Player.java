/**
 * Created by Hao on 12/7/2016.
 */
public class Player
{
    private int playerID;
    private int locationID;
    private int[] cards;
    private boolean isTurn;
    private boolean isTurnToDisprove;
    private String userName;
    private String gameHistoryUpdate;
    private int[] gamePieceLocations;
    private int[] moveOptions;
    private int[] accusation;
    private int[] suggestoin;

    public void setGameHistoryUpdate(String msg)
    {
        this.gameHistoryUpdate = msg;
    }

    public void setGamePieceLocations(int[] locations){
        this.gamePieceLocations = gamePieceLocations;
    }

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

    public boolean getTurn()
    {
        return isTurn;
    }

    public void setTurn(boolean turn)
    {
        this.isTurn = turn;
    }

    public boolean getTurnToDisprove()
    {
        return isTurnToDisprove;
    }

    public void setTurnToDisprove(boolean turn)
    {
        this.isTurnToDisprove = turn;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String name)
    {
        userName = name;
    }

    public void setCards(int[] cards)
    {
        this.cards = cards;
    }

    public int[] getCards(){
        return  cards;
    }

    public void setMoveOptions(int[] moveOptions){
        this.moveOptions = moveOptions;
    }

    public int[] getMoveOptions(){
        return moveOptions;
    }

    public void setAccusation(int[] accusation){
        this.accusation = accusation;
    }

    public int[] getAccusation(){
        return accusation;
    }

    public void setSuggestoin(int[] suggestoin){
        this.suggestoin = suggestoin;
    }

    public int[] getSuggestoin(){
        return suggestoin;
    }


}
