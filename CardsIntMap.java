import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Created by Hao on 12/8/2016.
 */
public class CardsIntMap
{
    private static BiMap<String, Integer> cardToIntMap;
    private static BiMap<Integer, String> intToCardMap;

    public CardsIntMap()
    {
        cardToIntMap = HashBiMap.create();
        //characters 0-5
        cardToIntMap.put("Miss Scarlet", 0);
        cardToIntMap.put("Col Mustard", 1);
        cardToIntMap.put("Mrs White", 2);
        cardToIntMap.put("Mr Green", 3);
        cardToIntMap.put("Mrs Peacock", 4);
        cardToIntMap.put("Prof Plum", 5);

        //weapons 6-11
        cardToIntMap.put("Rope", 6);
        cardToIntMap.put("Lead pipe", 7);
        cardToIntMap.put("Knife ", 8);
        cardToIntMap.put("Wrench", 9);
        cardToIntMap.put("Candlestick", 10);
        cardToIntMap.put("Revolver", 11);

        //locations 12-32, 12-20 are rooms, 21-32 are hallways
        cardToIntMap.put("Study", 12);
        cardToIntMap.put("Hall", 13);
        cardToIntMap.put("Lounge", 14);
        cardToIntMap.put("Library", 15);
        cardToIntMap.put("Billiard room", 16);
        cardToIntMap.put("Dining room", 17);
        cardToIntMap.put("Conservatory", 18);
        cardToIntMap.put("Ballroom", 19);
        cardToIntMap.put("Kitchen", 20);

        cardToIntMap.put("Study-Hall", 21);
        cardToIntMap.put("Hall-Lounge", 22);
        cardToIntMap.put("Library-Billiard Room", 23);
        cardToIntMap.put("Billiard Room-Dining Room", 24);
        cardToIntMap.put("Conservatory-Ballroom", 25);
        cardToIntMap.put("Ballroom-Kitchen", 26);
        cardToIntMap.put("Study-Library", 27);
        cardToIntMap.put("Hall-Billiard Room", 28);
        cardToIntMap.put("Lounge-Dining Room", 29);
        cardToIntMap.put("Library-Conservatory", 30);
        cardToIntMap.put("Billiard Room-Ballroom", 31);
        cardToIntMap.put("Dining Room-Kitchen", 32);

        intToCardMap = cardToIntMap.inverse();

    }
    public BiMap getCardToIntMap() {return cardToIntMap; }

    public BiMap getIntToCardMap() {return intToCardMap; }

}
