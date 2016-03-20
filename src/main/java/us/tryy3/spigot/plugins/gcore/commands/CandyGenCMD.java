package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.candy.CandyTier;
import us.tryy3.spigot.plugins.gcore.nbt.NBTItem;
import us.tryy3.spigot.plugins.gcore.utils.InventoryUtils;

import java.util.Arrays;

/**
 * Created by tryy3 on 2016-03-16.
 */
public class CandyGenCMD implements SubCommand {
    public GCore core;

    public CandyGenCMD(GCore core) {
        this.core = core;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender.hasPermission("gcore.admin")) || !(sender.hasPermission("gcore.cmd.candy"))) {
            sender.sendMessage(core.getMainConfig().getConfig().getString("No-Permission"));
            return;
        }

        if (strings.length < 2) {
            sender.sendMessage(core.getMainConfig().getConfig().getString("Incorrect-Syntax"));
            return;
        }

        if (!(sender instanceof Player) && strings.length != 3) {
            sender.sendMessage("Please supply a player name.");
            return;
        }

        String player = (strings.length == 3) ? strings[2] : sender.getName();

        Player p = Bukkit.getPlayer(player);

        if (p == null || !(p.isOnline())) {
            sender.sendMessage("The player "+player+" is not online");
            return;
        }

        if (!(core.getCandy().isTier(strings[1]))) {
            sender.sendMessage(strings[1] + " is not a valid candy tier.");
            return;
        }

        CandyTier tier = core.getCandy().getTier(strings[1]);

        ItemStack stack = new ItemStack(tier.getBlock());
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Candy Generator");
        meta.setLore(Arrays.asList("Tier: " + tier.getName()));
        stack.setItemMeta(meta);
        NBTItem item = new NBTItem(stack);
        item.setString("genBlock", tier.getKey());
        InventoryUtils.addItemToInventory(p.getInventory(), item.getItem(), p.getLocation());
    }
}
