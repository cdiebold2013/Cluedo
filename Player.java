import java.util.ArrayList;
import java.io.Serializable;

/**
 * Created by Hao on 12/7/2016.
 */
public class Player implements Serializable
{
    private int playerID;
    private int locationID;
    private String userName;

    private boolean join;
    private String joinedPlayer;
    private boolean started;                 //when the player clicks start button, this boolean variable will be set true
    private ArrayList<Integer> cards;
    private boolean initialSetup;           //the server's startGame() sets this boolean variable true to signal the client that the incoming
                                            // object contains initial setup information
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
    private int disprovedCard;       //set to -1 if there is no diproved card
    private boolean disproved;


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

    public void setLocationID(int locationID)
    {
        this.locationID = locationID;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public boolean isJoin()
    {
        return join;
    }

    public void setJoin(boolean join)
    {
        this.join = join;
    }

    public String getJoinedPlayer()
    {
        return joinedPlayer;
    }

    public void setJoinedPlayer(String joinedPlayer)
    {
        this.joinedPlayer = joinedPlayer;
    }

    public boolean isStarted()
    {
        return started;
    }

    public void setStarted(boolean started)
    {
        this.started = started;
    }

    public ArrayList<Integer> getCards()
    {
        return cards;
    }

    public void setCards(ArrayList<Integer> cards)
    {
        this.cards = cards;
    }

    public boolean isInitialSetup()
    {
        return initialSetup;
    }

    public void setInitialSetup(boolean initialSetup)
    {
        this.initialSetup = initialSetup;
    }

    public boolean isTurn()
    {
        return isTurn;
    }

    public void setTurn(boolean turn)
    {
        isTurn = turn;
    }

    public boolean isTurnToDisprove()
    {
        return isTurnToDisprove;
    }

    public void setTurnToDisprove(boolean turnToDisprove)
    {
        isTurnToDisprove = turnToDisprove;
    }

    public String getGameHistoryUpdate()
    {
        return gameHistoryUpdate;
    }

    public void setGameHistoryUpdate(String gameHistoryUpdate)
    {
        this.gameHistoryUpdate = gameHistoryUpdate;
    }

    public ArrayList<Integer> getGamePieceLocations()
    {
        return gamePieceLocations;
    }

    public void setGamePieceLocations(ArrayList<Integer> gamePieceLocations)
    {
        this.gamePieceLocations = gamePieceLocations;
    }

    public ArrayList<Integer> getAllowedActions()
    {
        return allowedActions;
    }

    public void setAllowedActions(ArrayList<Integer> allowedActions)
    {
        this.allowedActions = allowedActions;
    }

    public ArrayList<Integer> getAccusation()
    {
        return accusation;
    }

    public void setAccusation(ArrayList<Integer> accusation)
    {
        this.accusation = accusation;
    }

    public ArrayList<Integer> getSuggestoin()
    {
        return suggestoin;
    }

    public void setSuggestoin(ArrayList<Integer> suggestoin)
    {
        this.suggestoin = suggestoin;
    }

    public boolean isMoved()
    {
        return moved;
    }

    public void setMoved(boolean moved)
    {
        this.moved = moved;
    }

    public boolean isSuggested()
    {
        return suggested;
    }

    public void setSuggested(boolean suggested)
    {
        this.suggested = suggested;
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

    public int getDisprovedCard()
    {
        return disprovedCard;
    }

    public void setDisprovedCard(int disprovedCard)
    {
        this.disprovedCard = disprovedCard;
    }

    public boolean isDisproved()
    {
        return disproved;
    }

    public void setDisproved(boolean disproved)
    {
        this.disproved = disproved;
    }

    public Player(int playerID, String username)
    {
        this.playerID = playerID;
        this.userName = username;
        initialSetup = false;
        join = false;

        started = false;
        isTurn = false;
        isTurnToDisprove = false;
        moved = false;
        suggested = false;

        accused = false;
        active = false;
        disproved = false;
    }


}