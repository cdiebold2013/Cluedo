import com.google.common.collect.BiMap;

import java.util.*;

/**
 * @author PD, HY
 */
public class Game
{
    private CaseFile casefile;
    private ArrayList[] adjList;     //adjacency list of all the locations: an array of lists.
    //private int numOfPlayers;
    private ArrayList<Integer> rooms;
    private Node[] nodes;
    private ServerGUI gui;
    private BiMap intToCardMap;

    public Game(ServerGUI gui)
    {
        this.gui = gui;
        casefile = new CaseFile();
        rooms = new ArrayList<>();
        CardsIntMap map = new CardsIntMap();
        intToCardMap = map.getIntToCardMap();
        for (int i = 12; i < 21; i++)
            rooms.add(i);
        adjList = new ArrayList[33];
        for (int i = 0; i < 33; i++) {
            adjList[i] = new ArrayList<Node>();
        }
        nodes = new Node[33];
        for (int i = 0; i < 33; i++) {
            nodes[i] = new Node(i);

        }
        //construct the adjacency list. Note that the indices of the linked list in the linkedlist array correspond to the location IDs of the rooms and hallways
        adjList[12].add(nodes[21]);
        adjList[12].add(nodes[20]);
        adjList[12].add(nodes[27]);

        adjList[13].add(nodes[21]);
        adjList[13].add(nodes[22]);
        adjList[13].add(nodes[28]);

        adjList[14].add(nodes[18]);
        adjList[14].add(nodes[22]);
        adjList[14].add(nodes[29]);

        adjList[15].add(nodes[27]);
        adjList[15].add(nodes[23]);
        adjList[15].add(nodes[30]);

        adjList[16].add(nodes[23]);
        adjList[16].add(nodes[24]);
        adjList[16].add(nodes[28]);
        adjList[16].add(nodes[31]);

        adjList[17].add(nodes[24]);
        adjList[17].add(nodes[29]);
        adjList[17].add(nodes[32]);

        adjList[18].add(nodes[14]);
        adjList[18].add(nodes[25]);
        adjList[18].add(nodes[30]);

        adjList[19].add(nodes[25]);
        adjList[19].add(nodes[26]);
        adjList[19].add(nodes[31]);

        adjList[20].add(nodes[12]);
        adjList[20].add(nodes[26]);
        adjList[20].add(nodes[32]);

        adjList[21].add(nodes[12]);
        adjList[21].add(nodes[13]);
        adjList[22].add(nodes[13]);
        adjList[22].add(nodes[14]);
        adjList[23].add(nodes[15]);
        adjList[23].add(nodes[16]);
        adjList[24].add(nodes[16]);
        adjList[24].add(nodes[17]);
        adjList[25].add(nodes[18]);
        adjList[25].add(nodes[19]);
        adjList[26].add(nodes[19]);
        adjList[26].add(nodes[20]);
        adjList[27].add(nodes[12]);
        adjList[27].add(nodes[15]);
        adjList[28].add(nodes[13]);
        adjList[28].add(nodes[16]);
        adjList[29].add(nodes[14]);
        adjList[29].add(nodes[17]);
        adjList[30].add(nodes[15]);
        adjList[30].add(nodes[18]);
        adjList[31].add(nodes[16]);
        adjList[31].add(nodes[19]);
        adjList[32].add(nodes[17]);
        adjList[32].add(nodes[20]);

    } //end constructor Game

    public void startGame(ArrayList<Player> players)
    {
        // choose n number of random starting locations
        //int[] locations = new Random().ints(0, 33).distinct().limit(players.size()).toArray();
        int[] iniLocations = {22, 29, 26, 25, 30, 27};
        ArrayList<Integer> list = new ArrayList<>();
        // assign cards and starting locations to each player
        // update game history with players joining
        int numberOfPlayers = players.size();
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = players.get(i);
            list.add(iniLocations[i]);
            nodes[i].setOccupied(true);
            assignCards(players);
            player.setLocationID(iniLocations[i]);
            player.setGamePieceLocations(list);
            player.setGameHistoryUpdate(player.getUserName() + " joined the game and has cards assigned." + "\n" + "Miss Scarlet's turn.");
            player.setInitialSetup(true);
            player.setStarted(false);
        }

        // set turn to first player, and provide possible move options
        Player player1 = players.get(0);
        player1.setTurn(true);
        player1.setAllowedActions(allowedActions(player1));
    } //end method StartGame

    public void endGame()
    {

    } //end method EndGame

    public void assignCards(ArrayList<Player> players)
    {
        // still needs to be written - only have code here to make 'startGame' work
        int num = players.size();
        int[] file = casefile.getCaseFile();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            list.add(i);
        }
        list.remove((Integer) file[0]);
        list.remove((Integer) file[1]);
        list.remove((Integer) file[2]);
        Collections.shuffle(list);
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        ArrayList<Integer> list3 = new ArrayList<>();
        ArrayList<Integer> cards = new ArrayList<Integer>();
        if (num == 3) {
            for (int i = 0; i < 6; i++)
                list1.add(list.get(i));
            for (int i = 6; i < 11; i++)
                list2.add(list.get(i));
            for (int i = 12; i < 17; i++)
                list3.add(list.get(i));
            players.get(0).setCards(list1);
            players.get(1).setCards(list2);
            players.get(2).setCards(list3);

        }
        if (num == 4) {

        }

    }

    public ArrayList<Integer> moveOptions(Player player)
    {
        ArrayList<Integer> options = new ArrayList<>();
        int locationID = player.getLocationID();
        ArrayList validLocationList = adjList[locationID];
        int size = validLocationList.size();
        for (int i = 0; i < size; i++) {
            Node ithNode = (Node) validLocationList.get(i);
            if (!ithNode.occupied) options.add(ithNode.location);
        }

        return options;
    }

    public void processMove(ArrayList<Player> players, int playerID, int newLoc)
    {
        //set the player's old location unoccupied
        int numOfPlayers = players.size();
        Player movedPlayer = players.get(playerID);
        movedPlayer.setTurn(false);
        System.out.println("The new location for player" + playerID + "is " + newLoc);
        ArrayList<Integer> gamePieceLocations = movedPlayer.getGamePieceLocations();
        gamePieceLocations.remove(playerID);
        gamePieceLocations.add(playerID, newLoc);
        int oldLoc = movedPlayer.getLocationID();
        nodes[oldLoc].setOccupied(false);
        movedPlayer.setLocationID(newLoc);
        String characterName = (String) intToCardMap.get(playerID);
        String locationName = (String) intToCardMap.get(newLoc);

        for (int i = 0; i < numOfPlayers; i++) {
            Player player = players.get(i);
            player.setGameHistoryUpdate(characterName + " moved to " + locationName + "\n");
            player.setGamePieceLocations(gamePieceLocations);
            player.setInitialSetup(false);

        }


        //set the player's new location occupied

        nodes[newLoc].setOccupied(true);
        movedPlayer.setMoved(false);
    }

    public void processSuggestion(Player player, int suspect, int weapon, int room)
    {

    } //end method processSuggestion

public boolean processAccusation(Player players, int suspect, int weapon, int room)
    {
        //gui.writeLog("Casefile is " + casefile.getCaseFile());
        gui.writeLog("Casefile is " + casefile.getCaseFile());
        Boolean match = casefile.matchCaseFile(suspect, weapon, room);
        if (match) {   //we have a winner
            return true;
        } else {
            //did not match
            return false;
        } //end if else match
            //end if else//end if else match
        } //end method processAccusation



        public void setPlayerTurn()
    {
        //set the ClueLessMsg object's type to YOURTURN
        //set the ClueLessMsg object's isTurn attribute to true
        //set the ClueLessMsg object's message to the arraylist obtained by allowedActions()
    }public ArrayList<Integer> allowedActions(Player player)
    {
        int locationID = player.getLocationID();
        ArrayList<Integer> moves = moveOptions(player);
        //the first element of moves is to signal what type of actions are allowed for this player
        if (rooms.contains(locationID)) moves.add(0); //if the player is in a room, 0 indicates he can move or suggest
        else moves.add(1);  //if the player is in the hallway, 1 indicates he can only move
        //either 0 or 1 implies the player can accuse
        return moves;
    }

    public void setTurnToDisprove()
    {
        //decide whose turn it is to disprove
    }

    public void processDisprove(Player player, int card)
    {
        //process disproves
    }

    public ArrayList<Integer> gamePieceLocations()
    {
        //generate locations for the game pieces based on the result in processMove()
        return null;
    }

    public String gameHistoryUpdate(String update)
    {
        //generate game history update
        return null;
    }

    private class Node
    {

        int location;
        boolean occupied;

        Node(int location, boolean occupied)
        {
            this.location = location;
            this.occupied = occupied;
        }

        Node(int location)
        {
            this(location, false);
        }

        public void setOccupied(boolean occupied)
        {
            this.occupied = occupied;
        }
    }

} //end class game
