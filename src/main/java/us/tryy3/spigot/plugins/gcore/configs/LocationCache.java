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

    public void saveWarps() {
        Map<String, Map<String, Object>> warps = new HashMap<>();

        for (Map.Entry<String, Warp> entry : this.warps.entrySet()) {
            Warp location = entry.getValue();
            Map<String, Object> warp = new HashMap<>();
            warp.put("From", location.getFrom().getName());
            warp.put("To", location.getTo().getName());
            warp.put("Name", location.getName());
            warp.put("Name", location.getName());
            warps.put(entry.getKey(), warp);
        }

        config.createSection("Warps",warps);
        save();
    }

    public void saveLandzones() {
        Map<String, Map<String, Object>> landzones = new HashMap<>();

        for (Landzone landzone : this.landzones.values()) {
            Map<String, Object> zone = new HashMap<>();
            zone.put("Location", LocationUtils.LocationToString(landzone.getLocation()));
            if (landzone.getShip() != null) zone.put("Ships", landzone.getShip());
            if (landzone.getCommand() != null) zone.put("Command", landzone.getCommand());
            if (landzone.getDirections() != null) {
                Direction[] directions = landzone.getDirections();
                String str = "";
                for (Direction dir : directions) {
                    str = dir.getDir()+",";
                }
                zone.put("Direction", str.substring(0, str.length()-1));
            }
            landzones.put(landzone.getName(), zone);
        }

        config.createSection("Landzones",landzones);
        save();
    }

    public void savePlayers() {
        Map<String, String> players = new HashMap<>();

        for (PlayerPosition lastPosition : this.warpPlayers.values()) {
            players.put(lastPosition.getUuid().toString(),LocationUtils.LocationToString(lastPosition.getLocation()));
        }

        config.createSection("Players",players);
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
        if (!(file.exists())) {
            File parent = file.getParentFile();
            if (!(parent.exists())) {
                parent.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        this.warps = new HashMap<>();
        this.landzones = new HashMap<>();
        this.warpPlayers = new HashMap<>();

        if (config.contains("Landzones")) {
            Map<String, Object> landzones = config.getConfigurationSection("Landzones").getValues(false);

            for (Map.Entry<String, Object> landzone : landzones.entrySet()) {
                String[] dirs = null;
                Direction[] directions;
                if (!(config.contains("Landzones."+landzone.getKey()+".Direction"))) {
                    directions = null;
                } else {
                    dirs = config.getString("Landzones."+landzone.getKey()+".Direction").split(",");
                    directions = new Direction[dirs.length];
                    for (int i = 0; i < dirs.length; i++) {
                        directions[i] = Direction.getDir(Integer.parseInt(dirs[i]));
                    }
                }

                Landzone zone = new Landzone(landzone.getKey(),
                        LocationUtils.LocationFromString(config.getString("Landzones." + landzone.getKey() + ".Location")),
                        (config.contains("Landzones." + landzone.getKey() + ".Ships")) ?
                                config.getStringList("Landzones." + landzone.getKey() + ".Ships") :
                                null,
                        directions,
                        (config.contains("Landzones." + landzone.getKey() + ".Command")) ?
                                config.getString("Landzones." + landzone.getKey() + ".Command") :
                                null);
                this.landzones.put(landzone.getKey(), zone);
            }
        }

        if (config.contains("Warps")) {
            Map<String, Object> warps = config.getConfigurationSection("Warps").getValues(false);

            for (Map.Entry<String, Object> warp : warps.entrySet()) {
                if (!isLandzone(config.getString("Warps." + warp.getKey() + ".From"))) {
                    core.getLogger().warning("The warp " + warp.getKey() + " has a invalid from landzone.");
                    return;
                }
                if (!isLandzone(config.getString("Warps." + warp.getKey() + ".To"))) {
                    core.getLogger().warning("The warp " + warp.getKey() + " has a invalid to landzone.");
                    return;
                }

                this.warps.put(warp.getKey(), new Warp(config.getString("Warps." + warp.getKey() + ".Name"),
                        this.landzones.get(config.getString("Warps." + warp.getKey() + ".From")),
                        this.landzones.get(config.getString("Warps." + warp.getKey() + ".To"))));
            }
        }

        if (config.contains("Players")) {
            Map<String, Object> players = config.getConfigurationSection("Players").getValues(false);

            for (Map.Entry<String, Object> player : players.entrySet()) {
                UUID uuid = UUID.fromString(player.getKey());

                this.warpPlayers.put(uuid, new PlayerPosition(uuid, LocationUtils.LocationFromString((String) player.getValue())));
            }
        }
    }

    public void addLandzone(String name, Landzone zone) {
        this.landzones.put(name,zone);
        saveLandzones();
    }

    public void delLandzone(String name) {
        this.landzones.remove(name);
        saveLandzones();
    }

    public Landzone getLandzone(String name) {
        return this.landzones.get(name);
    }

    public boolean isLandzone(String name) {
        return landzones.containsKey(name);
    }

    public void addWarp(Warp warp) {
        this.warps.put(String.valueOf(this.warps.size()), warp);
        saveWarps();
    }

    public void delWarp(String name) {
        String id = getWarpID(name);
        if (id == null) return;
        this.warps.remove(id);
        saveWarps();
    }

    public boolean isWarp(String name) {
        return (getWarpID(name) != null);
    }

    public Warp getWarp(String name) {
        String id = getWarpID(name);
        if (id == null) return null;
        return this.warps.get(id);
    }

    public Map<String, Warp> getWarps() {
        return warps;
    }

    public Map<String, Warp> getWarps(String landzone) {
        Map<String, Warp> retWarp = new HashMap<>();
        for (Map.Entry<String, Warp> warp: warps.entrySet()) {
            if (!(warp.getValue().getFrom().getName().equalsIgnoreCase(landzone)) ||
                    !(warp.getValue().getTo().getName().equalsIgnoreCase(landzone))) continue;

            retWarp.put(warp.getKey(), warp.getValue());
        }

        return retWarp;
    }

    private String getWarpID(String name) {
        if (this.warps.containsKey(name)) return name;

        for (Map.Entry<String, Warp> warp : this.warps.entrySet()) {
            if (warp.getValue().getName().equalsIgnoreCase(name)) return warp.getKey();
        }

        return null;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
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
        System.out.println(warpPlayers.get(uuid).getLocation());
        delPlayer(uuid);
    }
}
