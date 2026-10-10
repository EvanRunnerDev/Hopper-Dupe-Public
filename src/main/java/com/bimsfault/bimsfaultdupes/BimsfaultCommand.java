package com.bimsfault.bimsfaultdupes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class BimsfaultCommand implements CommandExecutor {

    private final BimsfaultDupesPlugin plugin;

    public BimsfaultCommand(BimsfaultDupesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(plugin.getConfigManager().getPrefix() + "Usage: /bimsfault <reload|dupeboard|mydupeboard>");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("bimsfault.reload")) {
                sender.sendMessage(plugin.getConfigManager().getNoPermissionMessage());
                return true;
            }
            plugin.reloadConfig();
            sender.sendMessage(plugin.getConfigManager().getReloadSuccessMessage());
            return true;
        }

        if (args[0].equalsIgnoreCase("dupeboard")) {
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

        if (args[0].equalsIgnoreCase("dupeboard:self") || args[0].equalsIgnoreCase("mydupeboard")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(plugin.getConfigManager().getPlayerOnlyMessage());
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("bimsfault.dupeboard.self")) {
                player.sendMessage(plugin.getConfigManager().getNoPermissionMessage());
                return true;
            }
            if (!plugin.getConfigManager().isLeaderboardEnabled()) {
                sender.sendMessage(plugin.getConfigManager().getLeaderboardDisabledMessage());
                return true;
            }

            int dupeCount = plugin.getDupeDataManager().getDupeCount(player);
            player.sendMessage(plugin.getConfigManager().getMyDupeboardMessage()
                    .replace("%count%", String.valueOf(dupeCount)));
            return true;
        }

        return false;
    }
}