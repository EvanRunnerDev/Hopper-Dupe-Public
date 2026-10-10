package com.bimsfault.bimsfaultdupes;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {

    private final BimsfaultDupesPlugin plugin;

    public ConfigManager(BimsfaultDupesPlugin plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public List<String> getBlacklistedItems() {
        return getConfig().getStringList("blacklisted-items");
    }

    public int getDuplicationChance() {
        return getConfig().getInt("duplication-chance", 50);
    }

    public boolean getMultipleItemsDupe() {
        return getConfig().getBoolean("multiple-items-dupe", true);
    }

    public boolean isConsoleLogging() {
        return getConfig().getBoolean("console-logging", true);
    }

    public String getPrefix() {
        return getColoredString("messages.prefix", "&7[&bBimsFault-Dupes&7] &r");
    }

    public boolean isDupeCommandEnabled() {
        return getConfig().getBoolean("dupe-command.enabled", true);
    }

    public boolean isLeaderboardEnabled() {
        return getConfig().getBoolean("leaderboard.enabled", true);
    }

    public boolean isLoggingEnabled() {
        return getConfig().getBoolean("logging.enabled", true);
    }

    public String getDupeCommandMessage() {
        return getPrefix() + getColoredString("dupe-command.message", "Break hoppers to dupe!");
    }

    public String getReloadSuccessMessage() {
        return getPrefix() + getColoredString("messages.reload-success", "&aConfig reloaded.");
    }

    public String getNoPermissionMessage() {
        return getPrefix() + getColoredString("messages.no-permission", "&cYou do not have permission to use this command.");
    }

    public String getPlayerOnlyMessage() {
        return getPrefix() + getColoredString("messages.player-only", "&cThis command can only be run by a player.");
    }

    public String getCommandDisabledMessage() {
        return getPrefix() + getColoredString("messages.command-disabled", "&cThis command is disabled.");
    }

    public String getLeaderboardDisabledMessage() {
        return getPrefix() + getColoredString("messages.leaderboard-disabled", "&cThe dupe leaderboard is currently disabled.");
    }

    public String getLeaderboardHeader() {
        return getPrefix() + getColoredString("messages.leaderboard-header", "&6--- Dupe Leaderboard (Times Duped) ---");
    }

    public String getLeaderboardEmpty() {
        return getPrefix() + getColoredString("messages.leaderboard-empty", "&cThe dupe leaderboard is empty.");
    }

    public String getLeaderboardEntry() {
        return getColoredString("messages.leaderboard-entry", "&e%rank%. %player%: %count% times");
    }

    public String getMyDupeboardMessage() {
        return getPrefix() + getColoredString("messages.mydupeboard-message", "&aYou have successfully duped %count% times.");
    }

    // HopperBreakListener messages
    public String getItemBlacklistedMessage() {
        return getColoredString("messages.item-blacklisted", "Item %item% is blacklisted and will not be duplicated.");
    }

    public String getItemDuplicatedMessage() {
        return getColoredString("messages.item-duplicated", "Duplicated %amount%x %item% for %player%.");
    }

    public String getDuplicationChanceFailedMessage() {
        return getColoredString("messages.duplication-chance-failed", "Duplication chance failed for %item%.");
    }

    // DupeDataManager messages
    public String getDupecountsCreateErrorMessage() {
        return "Could not create dupecounts.yml!";
    }

    public String getDupecountsSaveErrorMessage() {
        return "Could not save dupecounts.yml!";
    }

    // DupeLogger messages
    public String getDupeLogCreateErrorMessage() {
        return "Could not create dupelog.txt!";
    }

    public String getDupeLogWriteErrorMessage() {
        return "Could not write to dupelog.txt!";
    }

    // Plugin enable/disable messages
    public String getPluginEnabledMessage() {
        return "BimsFault-Dupes has been enabled!";
    }

    public String getPluginDisabledMessage() {
        return "BimsFault-Dupes has been disabled!";
    }

    private String getColoredString(String path, String defaultVal) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path, defaultVal));
    }
}
