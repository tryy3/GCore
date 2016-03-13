package us.tryy3.spigot.plugins.gcore.ship;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import us.tryy3.spigot.plugins.gcore.GCore;

import java.util.UUID;

import static us.tryy3.spigot.plugins.gcore.ship.Direction.*;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class ShipObject extends BukkitRunnable {
    private UUID uuid;
    private double count;
    private PlayerLastPosition lastPosition;
    private ArmorStand ship;
    private Warp warp;
    private GCore core;
    private Direction direction;

    public ShipObject(GCore core, UUID uuid, PlayerLastPosition lastPosition, ArmorStand ship) {
        this(core,uuid,lastPosition,ship,(Math.random()<0.5) ? SOUTH : NORTH)
    }

    public ShipObject(GCore core, UUID uuid, PlayerLastPosition lastPosition, ArmorStand ship, Direction direction) {
        this.uuid = uuid;
        this.core = core;
        this.lastPosition = lastPosition;
        this.ship = ship;
        this.direction = direction;
        this.count = Math.random()*10+21;
    }

    @Override
    public void run() {
        Player player = Bukkit.getServer().getPlayer(uuid);
        ship.setVelocity(player.getLocation().getDirection().multiply(3).setY(1));
        Location l = ship.getLocation();
        getDirection(l,3);
        player.playEffect(l, Effect.LARGE_SMOKE, 20);

        count--;
        if (count >= 0) return;

        ship.eject();
        ship.remove();

        player.teleport(warp.getLocation());
        player.sendMessage(core.mainConfig.arrivedAt.replace("%landing-pad%",warp.getName()));

        if (core.mainConfig.warpCommandOnArrival) {
            String toExecute = core.mainConfig.warpCommandToExecute.replace("%player%", player.getName());
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), toExecute);
        }

        core.shipHandler.finishShip(uuid);
        this.cancel();
    }

    private void getDirection(Location location, int speed) {
        switch (direction) {
            case UP:
                location.setY(location.getY()-speed);
                break;
            case DOWN:
                location.setY(location.getY()+speed);
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
    }

    public PlayerLastPosition getLastPosition() {
        return lastPosition;
    }
}
