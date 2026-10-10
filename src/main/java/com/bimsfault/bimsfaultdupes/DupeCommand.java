package com.bimsfault.bimsfaultdupes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DupeCommand implements CommandExecutor {

    private final BimsfaultDupesPlugin plugin;

    public DupeCommand(BimsfaultDupesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getPlayerOnlyMessage());
            return true;
        }

        if (!plugin.getConfigManager().isDupeCommandEnabled()) {
            sender.sendMessage(plugin.getConfigManager().getCommandDisabledMessage());
            return true;
        }

        if (!sender.hasPermission("bimsfault.dupe")) {
            sender.sendMessage(plugin.getConfigManager().getNoPermissionMessage());
            return true;
        }

        sender.sendMessage(plugin.getConfigManager().getDupeCommandMessage());
        return true;
    }
}
