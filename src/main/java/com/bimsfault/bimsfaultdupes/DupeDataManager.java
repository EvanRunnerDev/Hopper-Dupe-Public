package com.bimsfault.bimsfaultdupes;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class DupeDataManager {

    private final BimsfaultDupesPlugin plugin;
    private FileConfiguration dataConfig;
    private File dataFile;

    public DupeDataManager(BimsfaultDupesPlugin plugin) {
        this.plugin = plugin;
        setup();
    }

    public void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        dataFile = new File(plugin.getDataFolder(), "dupecounts.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe(plugin.getConfigManager().getDupecountsCreateErrorMessage());
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void saveData() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe(plugin.getConfigManager().getDupecountsSaveErrorMessage());
        }
    }

    public void reloadData() {
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void incrementDupeCount(Player player, int amount) {
        String uuid = player.getUniqueId().toString();
        int currentCount = dataConfig.getInt("counts." + uuid + ".count", 0);
        dataConfig.set("counts." + uuid + ".name", player.getName());
        dataConfig.set("counts." + uuid + ".count", currentCount + amount);
        saveData();
    }

    public int getDupeCount(Player player) {
        String uuid = player.getUniqueId().toString();
        return dataConfig.getInt("counts." + uuid + ".count", 0);
    }

    public Map<String, Integer> getLeaderboard() {
        Map<String, Integer> leaderboard = new LinkedHashMap<>();
        if (dataConfig.getConfigurationSection("counts") == null) {
            return leaderboard;
        }
        //Sort the map
        Map<String, Integer> unsortedMap = new LinkedHashMap<>();
        for (String uuid : dataConfig.getConfigurationSection("counts").getKeys(false)) {
            unsortedMap.put(dataConfig.getString("counts." + uuid + ".name"), dataConfig.getInt("counts." + uuid + ".count"));
        }

        return unsortedMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
