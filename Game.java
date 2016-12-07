import java.util.ArrayList;

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

    public Game()
    {

        casefile = new CaseFile();
        rooms = new ArrayList<>();
        for(int i=0; i<9; i++)
            rooms.add(i);
        adjList = new ArrayList[21];
        for (int i = 0; i < 21; i++) {
            adjList[i] = new ArrayList<Node>();
        }
        nodes = new Node[21];
        for (int i = 0; i < 21; i++) {
            nodes[i] = new Node(i);

        }
        //construct the adjacency list. Note that the indices of the linked list in the linkedlist array correspond to the location IDs of the rooms and hallways
        adjList[0].add(nodes[9]);
        adjList[0].add(nodes[8]);
        adjList[0].add(nodes[15]);
        adjList[1].add(nodes[9]);
        adjList[1].add(nodes[10]);
        adjList[1].add(nodes[16]);
        adjList[2].add(nodes[6]);
        adjList[2].add(nodes[10]);
        adjList[2].add(nodes[17]);
        adjList[3].add(nodes[15]);
        adjList[3].add(nodes[11]);
        adjList[3].add(nodes[18]);
        adjList[4].add(nodes[11]);
        adjList[4].add(nodes[12]);
        adjList[4].add(nodes[16]);
        adjList[4].add(nodes[19]);
        adjList[5].add(nodes[12]);
        adjList[5].add(nodes[17]);
        adjList[5].add(nodes[20]);
        adjList[6].add(nodes[2]);
        adjList[6].add(nodes[13]);
        adjList[6].add(nodes[18]);
        adjList[7].add(nodes[13]);
        adjList[7].add(nodes[14]);
        adjList[7].add(nodes[19]);
        adjList[8].add(nodes[0]);
        adjList[8].add(nodes[14]);
        adjList[8].add(nodes[20]);
        adjList[9].add(nodes[0]);
        adjList[9].add(nodes[1]);
        adjList[10].add(nodes[1]);
        adjList[10].add(nodes[2]);
        adjList[11].add(nodes[3]);
        adjList[11].add(nodes[4]);
        adjList[12].add(nodes[4]);
        adjList[12].add(nodes[5]);
        adjList[13].add(nodes[6]);
        adjList[13].add(nodes[7]);
        adjList[14].add(nodes[7]);
        adjList[14].add(nodes[8]);
        adjList[15].add(nodes[0]);
        adjList[15].add(nodes[3]);
        adjList[16].add(nodes[1]);
        adjList[16].add(nodes[4]);
        adjList[17].add(nodes[2]);
        adjList[17].add(nodes[5]);
        adjList[18].add(nodes[3]);
        adjList[18].add(nodes[6]);
        adjList[19].add(nodes[4]);
        adjList[19].add(nodes[7]);
        adjList[20].add(nodes[5]);
        adjList[20].add(nodes[8]);

    } //end constructor Game

    public void startGame()
    {
        //assign playerID according to the client id in the ServerManager class
        //assign initial location
    } //end method StartGame

    public void endGame()
    {

    } //end method EndGame

    public void assignCards()
    {

    } //end method assignCards

    public ArrayList<Integer> moveOptions(ClueLessMsg player)
    {
        ArrayList<Integer> options = new ArrayList<>();
        int locationID = player.getLocationID();
        ArrayList validLocationList = adjList[locationID];
        int size = validLocationList.size();
        for(int i=0; i<size; i++ ){
            Node ithNode = (Node)validLocationList.get(i);
            if(!ithNode.occupied) options.add(ithNode.location);
        }

        return options;
    }

    public void processMove(ClueLessMsg player, int newLoc)
    {
       //set the player's old location unoccupied
        int oldLoc = player.getLocationID();
        nodes[oldLoc].setOccupied(false);

        //set the player's new location occupied
        player.setLocationID(newLoc);
        nodes[newLoc].setOccupied(true);

    }

    public void processSuggestion()
    {

    } //end method processSuggestion 

    public boolean processAccusation(int suspect, int weapon, int room)
    {
        System.out.println("Game:" + suspect + " " + weapon + " " + room);

        Boolean match = casefile.matchCaseFile(suspect, weapon, room);
        if (match) {   //we have a winner
            return true;
        } else { //did not match
            return false;
        } //end if else match
    } //end method processAccusation 

    public void setPlayerTurn()
    {
        //set the ClueLessMsg object's type to YOURTURN
        //set the ClueLessMsg object's isTurn attribute to true
        //set the ClueLessMsg object's message to the arraylist obtained by allowedActions()
    }

    public ArrayList<Integer> allowedActions(ClueLessMsg player){
        int locationID = player.getLocationID();
        ArrayList<Integer> moves = new ArrayList<>();
        //the first element of moves is to signal what type of actions are allowed for this player
        if(rooms.contains(locationID)) moves.add(0); //if the player is in a room, 0 indicates he can move or suggest
        else moves.add(1);  //if the player is in the hallway, 1 indicates he can only move
        //either 0 or 1 implies the player can accuse
        moves.addAll(moveOptions(player));
        return moves;
    }

    public void setTurnToDisprove(){
        //decide whose turn it is to disprove
    }

    public ArrayList<Integer> gamePieceLocations(){
        //generate locations for the game pieces based on the result in processMove()
        return null;
    }

    public String gameHistoryUpdate(ClueLessMsg player){
        //generate game history update
        return null;
    }
} //end class game
