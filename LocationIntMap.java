import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.HashMap;

/**
 * @author Hao Yu
 */
public class LocationIntMap
{

    private static BiMap locationToIntMap;
    private static BiMap intToLocationMap;

    public LocationIntMap(){
        locationToIntMap = HashBiMap.create();
        locationToIntMap.put("Study", 12);
        locationToIntMap.put("Hall", 13);
        locationToIntMap.put("Lounge", 14);
        locationToIntMap.put("Library", 15);
        locationToIntMap.put("Billiard Room", 16);
        locationToIntMap.put("Dining Room", 17);
        locationToIntMap.put("Conservatory", 18);
        locationToIntMap.put("Ballroom", 19);
        locationToIntMap.put("Kitchen", 20);
        locationToIntMap.put("Study-Hall", 21);

        locationToIntMap.put("Hall-Lounge", 22);
        locationToIntMap.put("Library-Billiard Room", 23);
        locationToIntMap.put("Billiard Room-Dining Room", 24);
        locationToIntMap.put("Conservatory-Ballroom", 25);
        locationToIntMap.put("Ballroom-Kitchen", 26);
        locationToIntMap.put("Study-Library", 27);
        locationToIntMap.put("Hall-Billiard Room", 28);
        locationToIntMap.put("Lounge-Dining Room", 29);
        locationToIntMap.put("Library-Conservatory", 30);
        locationToIntMap.put("Billiard Room-Ballroom", 31);
        locationToIntMap.put("Dining Room-Kitchen", 32);

        intToLocationMap = locationToIntMap.inverse();

    }

    public BiMap getLocationToIntMap() { return locationToIntMap; }
    public BiMap getIntToLocationMap() { return intToLocationMap; }
}
