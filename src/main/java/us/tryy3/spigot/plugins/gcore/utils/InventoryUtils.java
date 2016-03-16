package us.tryy3.spigot.plugins.gcore.utils;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tryy3 on 2016-03-16.
 */
public class InventoryUtils {
    public static boolean addItemToInventory(Inventory inventory, ItemStack itemStack) {
        return addItemToInventory(inventory, itemStack, null);
    }

    public static boolean addItemToInventory(Inventory inventory, ItemStack itemStack, Location location) {
        Map<Integer, ItemStack> drops = inventory.addItem(itemStack);

        if (drops.isEmpty()) return true;
        if (location == null) return false;
        for (ItemStack item : drops.values()) {
            location.getWorld().dropItem(location, item);
        }
        return true;
    }

    public static boolean fillInventory(Inventory inventory, ItemStack stack) {
        ItemStack dummyStack = stack.clone();
        dummyStack.setAmount(1);
        for (int i = 0; i < stack.getAmount(); i++) {
            HashMap<Integer, ItemStack> drops = inventory.addItem(dummyStack);
            if (!(drops.isEmpty())) return false;
        }
        return true;
    }
}
