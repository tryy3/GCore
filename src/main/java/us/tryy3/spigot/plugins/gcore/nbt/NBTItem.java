package us.tryy3.spigot.plugins.gcore.nbt;

import org.bukkit.inventory.ItemStack;

public class NBTItem {
    private ItemStack bukkitItem;

    public NBTItem (ItemStack item) {
        bukkitItem = item.clone();
    }

    public ItemStack getItem() {
        return bukkitItem;
    }

    public void setString (String key, String text) {
        bukkitItem = NBTReflectionUtils.setString(bukkitItem, key, text);
    }

    public String getString (String key) {
        return NBTReflectionUtils.getString(bukkitItem, key);
    }

    public void setInteger (String key, Integer intNumber) {
        bukkitItem = NBTReflectionUtils.setInt(bukkitItem, key, intNumber);
    }

    public Integer getInteger (String key) {
        return NBTReflectionUtils.getInt(bukkitItem, key);
    }

    public void setDouble (String key, Double d) {
        bukkitItem = NBTReflectionUtils.setDouble(bukkitItem, key, d);
    }

    public Double getDouble (String key) {
        return NBTReflectionUtils.getDouble(bukkitItem, key);
    }

    public void setBoolean (String key, Boolean b) {
        bukkitItem = NBTReflectionUtils.setBoolean(bukkitItem, key, b);
    }

    public Boolean getBoolean (String key) {
        return NBTReflectionUtils.getBoolean(bukkitItem, key);
    }

    public Boolean hasKey (String key) {
        return NBTReflectionUtils.hasKey(bukkitItem, key);
    }
}