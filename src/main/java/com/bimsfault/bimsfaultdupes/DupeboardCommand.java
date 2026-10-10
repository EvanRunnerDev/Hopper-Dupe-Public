package com.bimsfault.bimsfaultdupes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class DupeboardCommand implements CommandExecutor {

    private final BimsfaultDupesPlugin plugin;

    public DupeboardCommand(BimsfaultDupesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("bimsfault.dupeboard")) {
            sender.sendMessage(plugin.getConfigManager().getNoPermissionMessage());
            return true;
        }
        if (!plugin.getConfigManager().isLeaderboardEnabled()) {
            sender.sendMessage(plugin.getConfigManager().getLeaderboardDisabledMessage());
            return true;
        }

        Map<String, Integer> leaderboard = plugin.getDupeDataManager().getLeaderboard();
        if (leaderboard.isEmpty()) {
            sender.sendMessage(plugin.getConfigManager().getLeaderboardEmpty());
            return true;
        }

        sender.sendMessage(plugin.getConfigManager().getLeaderboardHeader());
        int rank = 1;
        for (Map.Entry<String, Integer> entry : leaderboard.entrySet()) {
            sender.sendMessage(plugin.getConfigManager().getLeaderboardEntry()
                    .replace("%rank%", String.valueOf(rank))
                    .replace("%player%", entry.getKey())
                    .replace("%count%", String.valueOf(entry.getValue())));
            rank++;
        }
        return true;
    }
}
