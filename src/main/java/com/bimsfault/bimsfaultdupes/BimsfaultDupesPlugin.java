package com.bimsfault.bimsfaultdupes;

import org.bukkit.plugin.java.JavaPlugin;

public final class BimsfaultDupesPlugin extends JavaPlugin {

    private ConfigManager configManager;
    private DupeDataManager dupeDataManager;
    private DupeLogger dupeLogger;

    @Override
    public void onEnable() {
        // Save the default config.yml if it doesn't exist
        saveDefaultConfig();

        // Initialize managers
        this.configManager = new ConfigManager(this);
        this.dupeDataManager = new DupeDataManager(this);
        this.dupeLogger = new DupeLogger(this);

        // Register the event listener
        getServer().getPluginManager().registerEvents(new HopperBreakListener(this), this);

        // Register commands
        this.getCommand("bimsfault").setExecutor(new BimsfaultCommand(this));
        this.getCommand("dupe").setExecutor(new DupeCommand(this));
        this.getCommand("dupeboard").setExecutor(new DupeboardCommand(this));
        this.getCommand("mydupeboard").setExecutor(new MyDupeboardCommand(this));

        getLogger().info(configManager.getPluginEnabledMessage());
    }

    @Override
    public void onDisable() {
        getLogger().info(configManager.getPluginDisabledMessage());
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public DupeDataManager getDupeDataManager() {
        return dupeDataManager;
    }

    public DupeLogger getDupeLogger() {
        return dupeLogger;
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        // custom reload logic here if needed
    }
}