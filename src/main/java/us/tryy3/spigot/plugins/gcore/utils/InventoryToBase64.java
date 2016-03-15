package us.tryy3.spigot.plugins.gcore.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dennis.planting on 3/15/2016.
 */
public class InventoryToBase64 {
    protected InventoryToBase64() {
    }

    /**
     * Serialization an Inventory. Note that this does not save the armor content for a
     * PlayerInventory.
     *
     * @param inv The inventory to serialize
     * @return A JSONArray representing the serialized Inventory.
     */
    public static JSONArray serializeInventory(Inventory inv) {
        JSONArray inventory = new JSONArray();
        for (int i = 0; i < inv.getSize(); i++) {
            JSONObject value = SingleItemSerialization.serializeItemInInventory(inv.getItem(i), i);
            if (value != null) {
                inventory.put(value);
            }
        }
        return inventory;
    }
}
