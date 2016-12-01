import java.util.Random;

/**
 * This class sets the case file for the game and provides a method for testing
 * an accusation against the case file
 * @author PD
 */
public class CaseFile {
    private final int suspect, weapon, room;
    
    CaseFile () {
        
       Random random = new Random();
       suspect = random.nextInt(5);
       weapon  = random.nextInt(5);
       room    = random.nextInt(8);     
    } //end constructor CaseFile
    
    //See if the accusation matches the casefile
    public boolean matchCaseFile( int suspect, int weapon, int room) {    
        System.out.println("CaseFile:" + suspect + " " + weapon + " " + room);
        return this.suspect==suspect && this.weapon==weapon && this.room==room;
    } //end method
    
    /* For diagnostics only */
    public void getCaseFile () {
        System.out.println("suspect=" + suspect + "  "
                + "weapon=" + weapon + " room=" + room);
    } //end method getCaseFile
    
} //end cass CaseFile
