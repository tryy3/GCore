package us.tryy3.spigot.plugins.gcore.ship;

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
    private Map<UUID,ShipObject> activeShips = new HashMap<>();
    private Map<String,ItemStack> ships = new HashMap<>();
    private GCore core;

    public ShipHandler() {
        this.ships.put("cobra", new ItemStack(Material.CARPET,1,Byte.valueOf("15")));
        this.ships.put("", new ItemStack(Material.CARPET,1,Byte.valueOf("14")));
        this.ships.put("", new ItemStack(Material.CARPET,1,Byte.valueOf("13")));
        this.ships.put("", new ItemStack(Material.CARPET,1,Byte.valueOf("1")));
    }

    public ShipObject getShip(UUID uuid) {
        return this.activeShips.get(uuid);
    }

    public boolean containShip(UUID uuid) {
        return this.activeShips.containsKey(uuid);
    }

    public void deactivateShip(UUID uuid) {
        this.warpPlayers.put(uuid, this.activeShips.get(uuid).getLastPosition());
        this.activeShips.remove(uuid);
    }

    public PlayerLastPosition getPlayer(UUID uuid) {
        return this.warpPlayers.get(uuid);
    }

    public boolean containsPlayer(UUID uuid) {
        return this.warpPlayers.containsKey(uuid);
    }

    public boolean isShip(String name) {
        return ships.containsKey(name);
    }

    public ArmorStand getShip(String name, Location location) {
        ArmorStand stand = location.getWorld().spawn(location, ArmorStand.class);
        stand.setHelmet(ships.get(name));
        stand.setVisible(false);
        stand.setGravity(false);

        return stand;
    }
}
