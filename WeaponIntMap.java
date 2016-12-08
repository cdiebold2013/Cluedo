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
        weaponToIntMap.put("Rope", 6);
        weaponToIntMap.put("Lead pipe", 7);
        weaponToIntMap.put("Knife ", 8);
        weaponToIntMap.put("Wrench", 9);
        weaponToIntMap.put("Candlestick", 10);
        weaponToIntMap.put("Revolver", 11);

        intToWeaponMap = weaponToIntMap.inverse();

    }

    public BiMap getWeaponToIntMap() { return weaponToIntMap; }

    public BiMap getIntToWeaponMap() { return intToWeaponMap; }

}
