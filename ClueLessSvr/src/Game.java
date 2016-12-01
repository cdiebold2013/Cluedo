
/**
 *
 * @author PD
 */
public class Game {
    CaseFile casefile = new CaseFile();
    
    
    
    Game () {
     casefile.getCaseFile();
       
      
    } //end constructor Game
    
    public void startGame () {
        
    } //end method StartGame
    
    public void endGame () {
        
    } //end method EndGame
    
    public void assignCards () {
        
    } //end method assignCards
    
    public boolean processMove (int Character, int curLoc, int newLoc) {
       return true;  //if move successful
    } //end method processMove
    
    public void processSuggestion (){
        
    } //end method processSuggestion 
    
    public boolean processAccusation (int suspect, int weapon, int room) {
        System.out.println("Game:" + suspect + " " + weapon + " " + room);
                
        Boolean match = casefile.matchCaseFile(suspect, weapon, room);
        if (match) {   //we have a winner
           return true;
        } else { //did not match
           return false;         
        } //end if else match
    } //end method processAccusation 
    
    public void setPlayerTurn() {
        
    } //end method setPlayerTurn
    
} //end class game
