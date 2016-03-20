package us.tryy3.spigot.plugins.gcore.ship;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
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

    private ArmorStand createShip(UUID uuid, Landzone landzone, Location location) {
        Player player = Bukkit.getPlayer(uuid);
        ArmorStand stand = location.getWorld().spawn(location, ArmorStand.class);
        stand.setHelmet(ships.get(landzone.getShip(player)));
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setPassenger(player);
        return stand;
    }

    private void addPosition(UUID uuid, Location location) {
        PlayerPosition position = new PlayerPosition(uuid, location);
        core.getCache().addPlayer(uuid, position);
    }

    public void startShip(UUID uuid, Landzone landzone) {
        Location location = landzone.getLocation();
        ArmorStand stand = createShip(uuid, landzone, location);

        WaitingShip wait = new WaitingShip(landzone, uuid, stand, landzone.getDirection());
        waitingShip.put(uuid, wait);

        addPosition(uuid, location);
    }

    public void activateShip(UUID uuid, Warp warp) {
        ArmorStand stand;

        if (waitingShip.containsKey(uuid)) {
            stand = waitingShip.get(uuid).getStand();
            waitingShip.remove(uuid);
        } else {
            stand = createShip(uuid, warp.getFrom(), warp.getFrom().getLocation());
        }

        RunningShip running = new RunningShip(core, uuid, warp, stand, warp.getFrom().getDirection());
        runningShips.put(uuid, running);

        addPosition(uuid, warp.getTo().getLocation());
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

    public List<String> getShips() {
        List<String> output = new ArrayList<>();
        output.addAll(this.ships.keySet());
        return output;
    }

    public boolean isWaiting(UUID uuid) {
        return this.waitingShip.containsKey(uuid);
    }

    public boolean isRunning(UUID uuid) {
        return this.runningShips.containsKey(uuid);
    }
}
