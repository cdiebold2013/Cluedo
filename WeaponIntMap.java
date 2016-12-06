import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @author Hao Yu
 */
public class WeaponIntMap
{
    private static BiMap weaponToIntMap;
    private static BiMap intToWeaponMap;

    public WeaponIntMap()
    {
        weaponToIntMap = HashBiMap.create();
        weaponToIntMap.put("Miss Scarlet", 0);
        weaponToIntMap.put("Col Mustard", 1);
        weaponToIntMap.put("Mrs White", 2);
        weaponToIntMap.put("Mr Green", 3);
        weaponToIntMap.put("Mrs Peacock", 4);
        weaponToIntMap.put("Prof Plum", 5);

        intToWeaponMap = weaponToIntMap.inverse();

    }

    public BiMap getWeaponToIntMap() { return weaponToIntMap; }

    public BiMap getIntToWeaponMap() { return intToWeaponMap; }

}
