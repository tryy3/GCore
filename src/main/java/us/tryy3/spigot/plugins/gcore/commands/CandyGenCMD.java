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
import us.tryy3.spigot.plugins.gcore.utils.ChatUtils;
import us.tryy3.spigot.plugins.gcore.utils.InventoryUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by tryy3 on 2016-03-16.
 */
public class CandyGenCMD implements SubCommand {
    public GCore core;

    public CandyGenCMD(GCore core) {
        this.core = core;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] strings, Map<String, String> flags) {
        if (!(sender.hasPermission("gcore.admin")) || !(sender.hasPermission("gcore.cmd.candy"))) {
            ChatUtils.chat(sender, core.getMainConfig().getConfig().getString("Messages.No-Permission"));
            return;
        }

        if (strings.length < 2) {
            ChatUtils.chat(sender, core.getMainConfig().getConfig().getString("Messages.Incorrect-Syntax"));
            return;
        }

        if (!(sender instanceof Player) && strings.length != 3) {
            ChatUtils.chat(sender, core.getMainConfig().getConfig().getString("Messages.Supply-Player-Name"));
            return;
        }

        String player = (strings.length == 3) ? strings[2] : sender.getName();

        Player p = Bukkit.getPlayer(player);

        if (p == null || !(p.isOnline())) {
            ChatUtils.chat(sender, core.getMainConfig().getConfig().getString("Messages.Player-Not-Online").replace("%player%",player));
            return;
        }

        if (!(core.getCandy().isTier(strings[1]))) {
            ChatUtils.chat(sender, core.getMainConfig().getConfig().getString("Messages.Not-Valid-Tier").replace("%tier%",strings[1]));
            return;
        }

        CandyTier tier = core.getCandy().getTier(strings[1]);

        ItemStack stack = new ItemStack(tier.getBlock());
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatUtils.format(core.getMainConfig().getConfig().getString("Messages.Candy-Title")));
        meta.setLore(Arrays.asList("Tier: " + ChatUtils.format(tier.getName())));
        stack.setItemMeta(meta);
        NBTItem item = new NBTItem(stack);
        item.setString("genBlock", tier.getKey());
        InventoryUtils.addItemToInventory(p.getInventory(), item.getItem(), p.getLocation());
    }
}
