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
        locationToIntMap.put("Study", 0);
        locationToIntMap.put("Hall", 1);
        locationToIntMap.put("Lounge", 2);
        locationToIntMap.put("Library", 3);
        locationToIntMap.put("Billiard Room", 4);
        locationToIntMap.put("Dining Room", 5);
        locationToIntMap.put("Conservatory", 6);
        locationToIntMap.put("Ballroom", 7);
        locationToIntMap.put("Kitchen", 8);
        locationToIntMap.put("Study-Hall", 9);

        locationToIntMap.put("Hall-Lounge", 10);
        locationToIntMap.put("Library-Billiard Room", 11);
        locationToIntMap.put("Billiard Room-Dining Room", 12);
        locationToIntMap.put("Conservatory-Ballroom", 13);
        locationToIntMap.put("Ballroom-Kitchen", 14);
        locationToIntMap.put("Study-Library", 15);
        locationToIntMap.put("Hall-Billiard Room", 16);
        locationToIntMap.put("Lounge-Dining Room", 17);
        locationToIntMap.put("Library-Conservatory", 18);
        locationToIntMap.put("Billiard Room-Ballroom", 19);
        locationToIntMap.put("Dining Room-Kitchen", 20);

        intToLocationMap = locationToIntMap.inverse();

    }

    public BiMap getLocationToIntMap() { return locationToIntMap; }
    public BiMap getIntToLocationMap() { return intToLocationMap; }
}
