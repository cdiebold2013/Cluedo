import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Hao Yu
 */
public class TokenLocation
{

    public static HashMap gameBoardCoordinates()
    {

        HashMap map = new HashMap();
         map.put(22, new Coordinates(210, 30));
        map.put(29, new Coordinates(270, 90));
        map.put(26, new Coordinates(210,270));
        map.put(25, new Coordinates(90,270));
        map.put(30, new Coordinates(30,210));
        map.put(27, new Coordinates(30,90));

        map.put(21, new Coordinates(90,30));
        map.put(32, new Coordinates(270,210));
        map.put(28, new Coordinates(150,90));
        map.put(24, new Coordinates(210,150));
        map.put(31, new Coordinates(150,210));
        map.put(23, new Coordinates(90,150));

        map.put(12, new Coordinates(6, 30) );
        map.put(13, new Coordinates(126, 30));
        map.put(14, new Coordinates(246, 30) );
        map.put(15, new Coordinates(6, 150));
        map.put(16, new Coordinates(126, 150));
        map.put(17, new Coordinates(246, 150));
        map.put(18, new Coordinates(6, 270));
        map.put(19, new Coordinates(126, 270));
        map.put(20, new Coordinates(246, 270));
        return map;

    }


}
