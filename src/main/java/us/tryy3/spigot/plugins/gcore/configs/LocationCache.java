package us.tryy3.spigot.plugins.gcore.configs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.ship.Direction;
import us.tryy3.spigot.plugins.gcore.ship.Landzone;
import us.tryy3.spigot.plugins.gcore.ship.PlayerPosition;
import us.tryy3.spigot.plugins.gcore.ship.Warp;
import us.tryy3.spigot.plugins.gcore.utils.LocationUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class LocationCache {
    private Map<String, Warp> warps;
    private Map<String, Landzone> landzones;
    private Map<UUID, PlayerPosition> warpPlayers;

    private File file;
    private YamlConfiguration config;
    private GCore core;

    public LocationCache(File file, GCore core) {
        this.core = core;
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        load();
    }

    public boolean isLandzone(String name) {
        return landzones.containsKey(name);
    }

    public void saveWarps() {
        Map<String, Map<String, Object>> warps = new HashMap<>();

        for (Map.Entry<String, Warp> entry : this.warps.entrySet()) {
            Warp location = entry.getValue();
            Map<String, Object> warp = new HashMap<>();
            warp.put("From", location.getFrom().getName());
            warp.put("To", location.getTo().getName());
            warp.put("Direction", location.getDirection().getDir());
            warp.put("Ships", location.getShips());
            warps.put(entry.getKey(), warp);
        }

        config.createSection("warps",warps);
        save();
    }

    public void saveLandzones() {
        Map<String, String> landzones = new HashMap<>();

        for (Landzone landzone : this.landzones.values()) {
            landzones.put(landzone.getName(), LocationUtils.LocationToString(landzone.getLocation()));
        }

        config.createSection("landzones",landzones);
        save();
    }

    public void savePlayers() {
        Map<String, String> players = new HashMap<>();

        for (PlayerPosition lastPosition : this.warpPlayers.values()) {
            players.put(lastPosition.getUuid().toString(),LocationUtils.LocationToString(lastPosition.getLocation()));
        }

        config.createSection("players",players);
        save();
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        this.warps = new HashMap<>();
        this.landzones = new HashMap<>();
        this.warpPlayers = new HashMap<>();

        Map<String, Object> landzones = config.getConfigurationSection("warps").getValues(false);

        for (Map.Entry<String, Object> landzone : landzones.entrySet()) {
            Landzone zone = new Landzone(landzone.getKey(), LocationUtils.LocationFromString((String) landzone.getValue()));
            this.landzones.put(landzone.getKey(),zone);
        }

        Map<String, Object> warps = config.getConfigurationSection("warps").getValues(false);

        for (Map.Entry<String, Object> warp : warps.entrySet()) {
            if (!isLandzone(config.getString("warps."+warp.getKey()+".From"))) {
                core.getLogger().warning("The warp "+warp.getKey()+" has a invalid from landzone.");
                return;
            }
            if (!isLandzone(config.getString("warps."+warp.getKey()+".To"))) {
                core.getLogger().warning("The warp "+warp.getKey()+" has a invalid to landzone.");
                return;
            }

            this.warps.put(warp.getKey(), new Warp(
                    this.landzones.get(config.getString("warps."+warp.getKey()+".From")),
                    this.landzones.get(config.getString("warps."+warp.getKey()+".To")),
                    Direction.getDir(config.getInt("warps."+warp.getKey()+".Direction")),
                    config.getStringList("warps."+warp.getKey()+".Ships")));
        }

        Map<String, Object> players = config.getConfigurationSection("players").getValues(false);

        for (Map.Entry<String, Object> player : warps.entrySet()) {
            UUID uuid = UUID.fromString(player.getKey());

            this.warpPlayers.put(uuid, new PlayerPosition(uuid, LocationUtils.LocationFromString((String) player.getValue())));
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public boolean isPlayer(UUID uuid) {
        return warpPlayers.containsKey(uuid);
    }

    public void addPlayer(UUID uuid, PlayerPosition position) {
        warpPlayers.put(uuid, position);
        savePlayers();
    }

    public void delPlayer(UUID uuid) {
        warpPlayers.remove(uuid);
        savePlayers();
    }

    public void teleportPlayer(UUID uuid) {
        Bukkit.getPlayer(uuid).teleport(warpPlayers.get(uuid).getLocation());
        delPlayer(uuid);
    }
}
