import java.util.ArrayList;
import java.io.Serializable;
import java.util.Collections;
import java.util.Random;

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
    private int newLocation;


    private ArrayList<Integer> characters;
    private ArrayList<Integer> weapons;
    private ArrayList<Integer> locations;
    private ArrayList<Integer> caseFile2;


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

    public void setCards(ArrayList<Integer> cards, Integer numofPlayer)
    {
      //creating separate locations weapons and character array lists for assigning cards and adding them to the array lists
      ArrayList<Integer> characters = new ArrayList<Integer>();
      ArrayList<Integer> weapons = new ArrayList<Integer>();
      ArrayList<Integer> locations = new ArrayList<Integer>();
      ArrayList<Integer> caseFile2 = new ArrayList<Integer>();

      characters.add(0);
      characters.add(1);
      characters.add(2);
      characters.add(3);
      characters.add(4);
      characters.add(5);
      weapons.add(6);
      weapons.add(7);
      weapons.add(8);
      weapons.add(9);
      weapons.add(10);
      weapons.add(11);
      locations.add(12);
      locations.add(12);
      locations.add(13);
      locations.add(14);
      locations.add(15);
      locations.add(16);
      locations.add(17);
      locations.add(18);
      locations.add(19);
      locations.add(20);
      locations.add(21);
      locations.add(22);
      locations.add(23);
      locations.add(24);
      locations.add(25);
      locations.add(26);
      locations.add(27);
      locations.add(28);
      locations.add(29);
      locations.add(30);
      locations.add(31);
      locations.add(32);
      //shuffling cards
      Collections.shuffle(characters);
      Collections.shuffle(weapons);
      Collections.shuffle(locations);

      //setting case file
      caseFile2.add(weapons.get(0));
      caseFile2.add(characters.get(0));
      caseFile2.add(locations.get(0));
      weapons.remove(0);
      characters.remove(0);
      locations.remove(0);

      for(int i = 1; i <= numofPlayer; i++) {
        cards.add(drawCard());
      }
      System.out.println(cards);

      this.cards = cards;
    }
    public Integer drawWeapon() {
      Integer toReturn = null;

      toReturn = weapons.get(0);
      weapons.remove(0);

      return toReturn;
    }
    public Integer drawCharacter() {
      Integer toReturn = null;

      toReturn = characters.get(0);
      characters.remove(0);

      return toReturn;
    }
    public Integer drawRoom() {
      Integer toReturn = null;

      toReturn = locations.get(0);
      locations.remove(0);

      return toReturn;
    }
    private Integer drawCard() {
      Random random = new Random();
      Integer toReturn = null;
      //this randomizes what cards are drawn
        while(toReturn == null && !allEmpty()){
          int i = random.nextInt(3)+1;

          switch (i){
              case 1:
                  if(!weapons.isEmpty())
                      toReturn = drawWeapon();
                  break;
              case 2:
                  if(!locations.isEmpty())
                      toReturn = drawRoom();
                  break;

              case 3:
                  if(!characters.isEmpty())
                     toReturn = drawCharacter();
                  break;
          }
        }
      return toReturn;
    }
    public boolean allEmpty() {
      return (weapons.isEmpty() && locations.isEmpty() && characters.isEmpty());
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

    public int getNewLocation() {return newLocation;}

    public void setNewLocation(int newLocation) {this.newLocation = newLocation; }

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
        active = true;
        disproved = false;
    }


}
