package com.bimsfault.bimsfaultdupes;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class HopperBreakListener implements Listener {

    private final BimsfaultDupesPlugin plugin;
    private final Random random;

    public HopperBreakListener(BimsfaultDupesPlugin plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        // Check if the broken block is a hopper
        if (block.getType() == Material.HOPPER) {
            // Get the hopper's state to access its inventory
            Hopper hopper = (Hopper) block.getState();
            Inventory inventory = hopper.getInventory();

            List<String> blacklistedItems = plugin.getConfigManager().getBlacklistedItems();
            int duplicationChance = plugin.getConfigManager().getDuplicationChance();
            boolean multipleItemsDupe = plugin.getConfigManager().getMultipleItemsDupe();
            boolean consoleLogging = plugin.getConfigManager().isConsoleLogging();
            boolean itemDuped = false;

            if (multipleItemsDupe) {
                // Iterate through the hopper's inventory
                for (ItemStack item : inventory.getContents()) {
                    if(processItemForDuplication(player, item, blacklistedItems, duplicationChance, block, consoleLogging)){
                        itemDuped = true;
                    }
                }
            } else {
                // Process only the first item in the hopper's inventory
                ItemStack firstItem = inventory.getItem(0);
                if(processItemForDuplication(player, firstItem, blacklistedItems, duplicationChance, block, consoleLogging)){
                    itemDuped = true;
                }
            }
            if(itemDuped && plugin.getConfigManager().isLeaderboardEnabled()){
                plugin.getDupeDataManager().incrementDupeCount(player, 1);
            }
        }
    }

    private boolean processItemForDuplication(Player player, ItemStack item, List<String> blacklistedItems, int duplicationChance, Block block, boolean consoleLogging) {
        if (item != null && item.getType() != Material.AIR) {
            // Check if the item is blacklisted
            if (blacklistedItems.contains(item.getType().name())) {
                if (consoleLogging) {
                    plugin.getLogger().info(plugin.getConfigManager().getItemBlacklistedMessage()
                            .replace("%item%", item.getType().name()));
                }
                return false;
            }

            // Check if the duplication chance succeeds
            if (random.nextInt(100) < duplicationChance) {
                ItemStack clonedItem = item.clone();
                // Duplicate the item by dropping it at the hopper's location
                block.getWorld().dropItemNaturally(block.getLocation(), clonedItem);
                
                // Log the dupe
                if (plugin.getConfigManager().isLoggingEnabled()) {
                    plugin.getDupeLogger().log(player, clonedItem);
                }

                if (consoleLogging) {
                    plugin.getLogger().info(plugin.getConfigManager().getItemDuplicatedMessage()
                            .replace("%amount%", String.valueOf(item.getAmount()))
                            .replace("%item%", item.getType().name())
                            .replace("%player%", player.getName()));
                }
                return true;
            } else {
                if (consoleLogging) {
                    plugin.getLogger().info(plugin.getConfigManager().getDuplicationChanceFailedMessage()
                            .replace("%item%", item.getType().name()));
                }
                return false;
            }
        }
        return false;
    }
}
