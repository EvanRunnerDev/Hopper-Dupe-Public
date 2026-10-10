package com.bimsfault.bimsfaultdupes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MyDupeboardCommand implements CommandExecutor {

    private final BimsfaultDupesPlugin plugin;

    public MyDupeboardCommand(BimsfaultDupesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getPlayerOnlyMessage());
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("bimsfault.dupeboard.self")) {
            player.sendMessage(plugin.getConfigManager().getNoPermissionMessage());
            return true;
        }
        int dupeCount = plugin.getDupeDataManager().getDupeCount(player);
        player.sendMessage(plugin.getConfigManager().getMyDupeboardMessage()
                .replace("%count%", String.valueOf(dupeCount)));
        return true;
    }
}