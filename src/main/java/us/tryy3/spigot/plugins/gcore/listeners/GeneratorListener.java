package us.tryy3.spigot.plugins.gcore.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.candy.GeneratorBlock;
import us.tryy3.spigot.plugins.gcore.nbt.NBTItem;
import us.tryy3.spigot.plugins.gcore.utils.ChatUtils;
import us.tryy3.spigot.plugins.gcore.utils.InventoryUtils;

import java.util.Arrays;

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

        ItemStack stack = new ItemStack(block.getTier().getBlock());
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatUtils.format(core.getMainConfig().getConfig().getString("Messages.Candy-Title")));
        meta.setLore(Arrays.asList("Tier: " + ChatUtils.format(block.getTier().getName())));
        stack.setItemMeta(meta);
        NBTItem item = new NBTItem(stack);
        item.setString("genBlock", block.getTier().getKey());

        if (!(InventoryUtils.addItemToInventory(e.getPlayer().getInventory(), item.getItem()))) {
            ChatUtils.chat(e.getPlayer(), core.getMainConfig().getConfig().getString("Messages.Full-Inventory"));
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

    @EventHandler
    public void onInterract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        GeneratorBlock block = core.getCandy().getGenerator(event.getClickedBlock().getLocation());
        if (block == null) return;
        event.setCancelled(true);
        event.getPlayer().openInventory(block.getInventory());
    }
}
