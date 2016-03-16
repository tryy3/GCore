package us.tryy3.spigot.plugins.gcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;
import us.tryy3.spigot.plugins.gcore.GCore;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class ShipListener implements Listener {
    private GCore core;

    public ShipListener(GCore core) {
        this.core = core;
    }

    @EventHandler
    public void onDismount(final EntityDismountEvent e) {
        if (!(e.getDismounted() instanceof ArmorStand)) return;
        if (!(e.getEntity() instanceof Player)) return;
        if (!(core.getCache().isPlayer(e.getEntity().getUniqueId()))) return;

        Bukkit.getScheduler().runTaskLater(core, new Runnable() {
            @Override
            public void run() {
                e.getDismounted().setPassenger(e.getEntity());
            }
        }, 2L);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (!core.getCache().isPlayer(e.getPlayer().getUniqueId())) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (!(core.getCache().isPlayer(e.getPlayer().getUniqueId()))) return;
        if (!(e.getPlayer().isInsideVehicle())) return;
        if (!(e.getPlayer().getVehicle() instanceof ArmorStand)) return;
        core.getShipHandler().deactivateShip(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!(core.getCache().isPlayer(e.getPlayer().getUniqueId()))) return;
        core.getCache().teleportPlayer(e.getPlayer().getUniqueId());
        e.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
    }
}
