import java.util.ArrayList;

/**
 * Created by Hao on 12/7/2016.
 */
public class Player
{
    private int playerID;
    private int locationID;
    private String userName;
    private boolean join;
    private String joinedPlayer;        //the joined player's username
    private ArrayList<Integer> cards;
    private boolean isTurn;
    private boolean isTurnToDisprove;
    private String gameHistoryUpdate;
    private ArrayList<Integer> gamePieceLocations;
    private ArrayList<Integer> allowedActions;
    private ArrayList<Integer> accusation;
    private ArrayList<Integer> suggestoin;
    private boolean moved;
    private boolean suggested;
    private boolean accused;
    private boolean active;          //after a failed accusation, the player is inactive
    private int disprovedCard;
    private boolean disproved;
    

public Player(int playerID, String username){
        this.playerID = playerID;
        this.userName = username;
       //all statuses are false by default
        join = false;
        isTurn = false;
        isTurnToDisprove = false;
        moved =false;
        suggested = false;
        accused = false;
        active = false;
        disproved = false;
    }


    public String getJoinedPlayer()
    {
        return joinedPlayer;
    }

    public void setJoinedPlayer(String joinedPlayer)
    {
        this.joinedPlayer = joinedPlayer;
    }

    public boolean isJoin()
    {
        return join;
    }

    public void setJoin(boolean join)
    {
        this.join = join;
    }

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

    public void setCards(ArrayList<Integer> cards)
    {
        this.cards = cards;
    }

    public ArrayList<Integer>getCards(){
        return  cards;
    }

    public void setMoveOptions(ArrayList<Integer> allowedActions){
        this.allowedActions = allowedActions;
    }

    public ArrayList<Integer> getAllowedActions(){
        return allowedActions;
    }

    public void setAccusation(ArrayList<Integer> accusation){
        this.accusation = accusation;
    }

    public ArrayList<Integer> getAccusation(){
        return accusation;
    }

    public void setSuggestoin(ArrayList<Integer> suggestoin){
        this.suggestoin = suggestoin;
    }

    public ArrayList<Integer> getSuggestoin(){
        return suggestoin;
    }

    public boolean isMoved()
    {
        return moved;
    }

    public void setMoved(boolean moved)
    {
        this.moved = moved;
    }

    public boolean isAccused()
    {
        return accused;
    }

    public void setAccused(boolean accused)
    {
        this.accused = accused;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public boolean isSuggested()
    {

        return suggested;
    }

    public void setSuggested(boolean suggested)
    {
        this.suggested = suggested;
    }

    public void setDisprovedCard(int disprovedCard){
        this.disprovedCard = disprovedCard;
    }

    public int getDisprovedCard(){
        return disprovedCard;
    }

    public void setDisproved(boolean disprovedCard){
        this.disproved = disproved;
    }

    public boolean getDisproved(){
        return disproved;
    }
}
