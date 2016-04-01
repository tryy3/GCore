package us.tryy3.spigot.plugins.gcore.ship;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.utils.ChatUtils;

import java.util.UUID;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class RunningShip extends BukkitRunnable {
    private UUID uuid;
    private int count;
    private ArmorStand ship;
    private Warp warp;
    private GCore core;
    private Direction direction;

    public RunningShip(GCore core, UUID uuid, Warp warp, ArmorStand ship, Direction direction) {
        this.uuid = uuid;
        this.core = core;
        this.ship = ship;
        this.direction = direction;
        this.count = (int) (Math.random()*140+61);
        this.warp = warp;
    }

    @Override
    public void run() {
        Player player = Bukkit.getServer().getPlayer(uuid);
        ship.setVelocity(player.getLocation().getDirection().multiply(3).setY(1));
        Location l = ship.getLocation();
        getDirection(l,1);
        System.out.println(count);
        player.playEffect(l, Effect.LARGE_SMOKE, 20);

        count--;
        if (count >= 0) return;

        core.getShipHandler().deactivateShip(player.getUniqueId());
        core.getCache().teleportPlayer(player.getUniqueId());
        ChatUtils.chat(player, core.getMainConfig().getConfig().getString("Messages.Arrived-At").replace("%warp%", warp.getTo().getName()));

        if (warp.getTo().hasCommand()) {
            String toExecute = warp.getTo().getCommand().replace("%player%", player.getName());
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), toExecute);
        }
        this.cancel();
    }

    private void getDirection(Location location, int speed) {
        System.out.println(speed);
        System.out.println(location);
        switch (direction) {
            case UP:
                location.setY(location.getY()+speed);
                break;
            case DOWN:
                location.setY(location.getY()-speed);
                break;
            case SOUTH:
                location.setZ(location.getZ()+speed);
                break;
            case NORTH:
                location.setZ(location.getZ()-speed);
                break;
            case EAST:
                location.setX(location.getX()+speed);
                break;
            case WEST:
                location.setX(location.getX()-speed);
                break;
        }
        System.out.println(location);
    }

    public ArmorStand getShip() {
        return ship;
    }
}
