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
        characterToIntMap.put("Miss Scarlet", 1);
        characterToIntMap.put("Col Mustard", 2);
        characterToIntMap.put("Mrs White", 3);
        characterToIntMap.put("Mr Green", 4);
        characterToIntMap.put("Mrs Peacock", 5);
        characterToIntMap.put("Prof Plum", 6);

        intToCharacterMap = characterToIntMap.inverse();

    }

    public BiMap getCharacterToIntMap() {return characterToIntMap; }

    public BiMap getIntToCharacterMap() {return intToCharacterMap; }

    public static void main(String[] arg){
        BiMap intToCharacterMap = new CharacterInt().getIntToCharacterMap();
        System.out.println(intToCharacterMap.get(4));
    }
}
