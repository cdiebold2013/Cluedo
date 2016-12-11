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
       suspect = random.nextInt(6);      //random number from 0 to 5
       weapon  = 6 + random.nextInt(6);      //random number from 6 to 11
       room    = 12 + random.nextInt(9);     //random number from 12 to 20
    } //end constructor CaseFile
    
    //See if the accusation matches the casefile
    public boolean matchCaseFile( int suspect, int weapon, int room) {    
        System.out.println("CaseFile:" + suspect + " " + weapon + " " + room);
        return this.suspect==suspect && this.weapon==weapon && this.room==room;
    } //end method
    
    /* For diagnostics only */
    public String getCaseFileResult () {
        return "suspect=" + suspect + "  "
                + "weapon=" + weapon + " room=" + room;
        
    }

    public int[] getCaseFile(){
        int[] file = new int[]{suspect, weapon, room};
        return file;
    }//end method getCaseFile
    
} //end cass CaseFile
