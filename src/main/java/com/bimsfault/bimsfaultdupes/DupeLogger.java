package com.bimsfault.bimsfaultdupes;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DupeLogger {

    private final BimsfaultDupesPlugin plugin;
    private File logFile;

    public DupeLogger(BimsfaultDupesPlugin plugin) {
        this.plugin = plugin;
        setup();
    }

    private void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        logFile = new File(plugin.getDataFolder(), "dupelog.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe(plugin.getConfigManager().getDupeLogCreateErrorMessage());
            }
        }
    }

    public void log(Player player, ItemStack item) {
        if (!plugin.getConfigManager().isLoggingEnabled()) return; // Check if logging is enabled

        try (FileWriter fw = new FileWriter(logFile, true);
             PrintWriter pw = new PrintWriter(fw)) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            pw.println(String.format("[%s] Player: %s, Duped Item: %s, Amount: %d",
                    timeStamp, player.getName(), item.getType().name(), item.getAmount()));
        } catch (IOException e) {
            plugin.getLogger().severe(plugin.getConfigManager().getDupeLogWriteErrorMessage());
        }
    }
}