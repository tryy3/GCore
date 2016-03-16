package us.tryy3.spigot.plugins.gcore.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.candy.GeneratorBlock;
import us.tryy3.spigot.plugins.gcore.nbt.NBTItem;
import us.tryy3.spigot.plugins.gcore.utils.InventoryUtils;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class GeneratorListener implements Listener {
    private GCore core;

    public GeneratorListener(GCore core) {
        this.core = core;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!(core.getCandy().isGenerator(e.getBlock().getLocation()))) return;
        GeneratorBlock block = core.getCandy().getGenerator(e.getBlock().getLocation());
        e.setCancelled(true);
        NBTItem nbtItem = new NBTItem(new ItemStack(block.getTier().getBlock()));
        nbtItem.setString("genBlock", block.getTier().getKey());
        if (InventoryUtils.addItemToInventory(e.getPlayer().getInventory(), nbtItem.getItem())) {
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYour inventory is full, please empty before breaking this"));
        } else {
            e.getBlock().setType(Material.AIR);
            core.getCandy().delGenerator(e.getBlock().getLocation());
        }
    }

    @EventHandler
    public void onplace(BlockPlaceEvent e) {
        NBTItem nbtItem = new NBTItem(e.getItemInHand());
        if (!(nbtItem.hasKey("genBlock"))) return;
        String genType = nbtItem.getString("genBlock");
        if (!(core.getCandy().isTier(genType))) return;
        core.getCandy().addGenerator(e.getBlockPlaced().getLocation(), genType);
    }
}
