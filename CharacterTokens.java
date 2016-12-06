import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;

/**
 * @author Hao Yu
 */
public class CharacterTokens
{
    public static HashMap charToTokensMap()
    {
       Circle[] tokens = MainGameUI.getTokens();

      HashMap tokenMap = new HashMap();
        tokenMap.put(0, tokens[0]);
        tokenMap.put(1, tokens[1]);
        tokenMap.put(2, tokens[2]);
        tokenMap.put(3, tokens[3]);
        tokenMap.put(4, tokens[4]);
        tokenMap.put(5, tokens[5]);

        return  tokenMap;
    }
}
