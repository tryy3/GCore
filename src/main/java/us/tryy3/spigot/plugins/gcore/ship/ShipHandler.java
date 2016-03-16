package us.tryy3.spigot.plugins.gcore.ship;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import us.tryy3.spigot.plugins.gcore.GCore;

import java.util.*;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class ShipHandler {
    private Map<UUID, WaitingShip> waitingShip = new HashMap<>();
    private Map<UUID,RunningShip> runningShips = new HashMap<>();
    private Map<String,ItemStack> ships = new HashMap<>();
    private GCore core;

    public ShipHandler(GCore core) {
        this.core = core;
        this.ships.put("cobra", new ItemStack(Material.CARPET,1,Byte.valueOf("15")));
        this.ships.put("viper", new ItemStack(Material.CARPET,1,Byte.valueOf("14")));
        this.ships.put("mamba", new ItemStack(Material.CARPET,1,Byte.valueOf("13")));
        this.ships.put("landzone", new ItemStack(Material.CARPET,1,Byte.valueOf("1")));
    }

    public void startShip(UUID uuid, Landzone landzone, String ship, Direction direction) {
        Location location = landzone.getLocation();
        ArmorStand stand = location.getWorld().spawn(location, ArmorStand.class);
        stand.setHelmet(ships.get(ship));
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setPassenger(Bukkit.getPlayer(uuid));

        WaitingShip wait = new WaitingShip(landzone, uuid, stand, direction);
        waitingShip.put(uuid, wait);

        PlayerPosition position = new PlayerPosition(uuid, location);
        core.getCache().addPlayer(uuid, position);
    }

    public void activateShip(UUID uuid, Warp warp) {
        WaitingShip wait = waitingShip.get(uuid);
        RunningShip running = new RunningShip(core, uuid, warp, wait.getStand(), wait.getDirection());
        runningShips.put(uuid, running);

        waitingShip.remove(uuid);
        core.getCache().delPlayer(uuid);

        PlayerPosition position = new PlayerPosition(uuid, warp.getTo().getLocation());
        core.getCache().addPlayer(uuid, position);
    }

    public void deactivateShip(UUID uuid) {
        ArmorStand stand;
        if (runningShips.containsKey(uuid)) {
            RunningShip ship = runningShips.get(uuid);
            stand = ship.getShip();
            runningShips.remove(uuid);
        } else {
            WaitingShip ship = waitingShip.get(uuid);
            stand = ship.getStand();
            waitingShip.remove(uuid);
        }
        stand.eject();
        stand.remove();
    }

    public boolean isShip(String ship) {
        return this.ships.containsKey(ship);
    }

    public boolean isWaiting(UUID uuid) {
        return this.waitingShip.containsKey(uuid);
    }

    public boolean isRunning(UUID uuid) {
        return this.runningShips.containsKey(uuid);
    }
}
