import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @author Hao Yu
 */
public class CharacterInt
{
    private static BiMap<String, Integer> characterToIntMap;
    private static BiMap<Integer, String> intToCharacterMap;

    public CharacterInt()
    {
        characterToIntMap = HashBiMap.create();
        characterToIntMap.put("Miss Scarlet", 0);
        characterToIntMap.put("Col Mustard", 1);
        characterToIntMap.put("Mrs White", 2);
        characterToIntMap.put("Mr Green", 3);
        characterToIntMap.put("Mrs Peacock", 4);
        characterToIntMap.put("Prof Plum", 5);

        intToCharacterMap = characterToIntMap.inverse();

    }

    public BiMap getCharacterToIntMap() {return characterToIntMap; }

    public BiMap getIntToCharacterMap() {return intToCharacterMap; }

    public static void main(String[] arg){
        BiMap intToCharacterMap = new CharacterInt().getIntToCharacterMap();
        System.out.println(intToCharacterMap.get(4));
    }
}
