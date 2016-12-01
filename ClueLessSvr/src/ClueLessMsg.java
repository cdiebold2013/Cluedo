import java.io.Serializable;
/*
 *
 * @author PD
 */
public class ClueLessMsg implements Serializable {
    
    static final int MESSAGE=0,   //client & Server text messages            
                     LOGOUT=1,    //client
                     WHOISIN=2,   //client
                     JOINGAME=3,  //client
                     MOVE=4,      //client                    
                     SUGGEST=5,   //client                    
                     ACCUSE=6,    //client                    
                     PROVE=7,     //client
                     ERRROR=8,    //server
                     STATE=13;    //server
                                  //Thirteen comma delimited numbers for location
                                  //first number is the activeplayer
          
    private int type;
    private String message;
        
        
    ClueLessMsg(int type, String message) {
        this.type = type;
        this.message = message;
    } //end constructor
    
    int getType(){
        return type;
    } //end method getType
    
    String getMessage() {
        return message;
    } //end method getMessage  
    
} //end class ClueLessMsg

