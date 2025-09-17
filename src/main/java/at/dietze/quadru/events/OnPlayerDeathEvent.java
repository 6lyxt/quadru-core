package at.dietze.quadru.events;

import at.dietze.quadru.QuadruCore;
import at.dietze.quadru.constants.IStrings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OnPlayerDeathEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        e.setDeathMessage(IStrings.prefix + "§c" + p.getCustomName() + " §cist gestorben.");
        e.getDrops().clear();

        this.spawnDeathChest(p);

        p.getInventory().clear();
    }

    private void spawnDeathChest(Player p) {
        Location deathLocation = p.getLocation().toBlockLocation();
        Location chestLocation1 = deathLocation.clone();

        while (!chestLocation1.getBlock().isEmpty()) {
            chestLocation1.add(0, 1, 0);
        }
        Location chestLocation2 = chestLocation1.clone().add(1, 0, 0);

        // i wanted to originally check around the player but that resulted in in facing issues for some reason
        if (!chestLocation2.getBlock().isEmpty()) {
            while (!chestLocation2.getBlock().isEmpty()) {
                chestLocation1.add(0, 1, 0);
                chestLocation2.add(0, 1, 0);
            }
        }

        Block chest1Block = chestLocation1.getBlock();
        Block chest2Block = chestLocation2.getBlock();

        chest1Block.setType(Material.CHEST);
        chest2Block.setType(Material.CHEST);

        BlockData data1 = chest1Block.getBlockData();
        BlockData data2 = chest2Block.getBlockData();

        // since someone (who should be shot btw) randomly decided to name the type Chest and the Block Chest the same,
        // we need to fully import the types name, since java doesnt have named import (kotlin, scala etc have them tho) (i want to die)

        if (data1 instanceof org.bukkit.block.data.type.Chest chestData1 && data2 instanceof org.bukkit.block.data.type.Chest chestData2) {
            chestData1.setType(org.bukkit.block.data.type.Chest.Type.RIGHT);
            chestData2.setType(org.bukkit.block.data.type.Chest.Type.LEFT);

            chestData1.setFacing(BlockFace.SOUTH);
            chestData2.setFacing(BlockFace.SOUTH);

            chest1Block.setBlockData(chestData1);
            chest2Block.setBlockData(chestData2);

            BlockState chestState = chest1Block.getState();
            if (chestState instanceof org.bukkit.block.Chest) {
                org.bukkit.block.Chest chest = (org.bukkit.block.Chest) chestState;
                Inventory doubleChestInventory = chest.getInventory();

                chest.setCustomName("Grab von " + p.getCustomName());
                chest.update();

                ItemStack[] playerContents = p.getInventory().getContents();
                ItemStack[] armorContents = p.getInventory().getArmorContents();

                for (ItemStack item : playerContents) {
                    if (item != null) {
                        doubleChestInventory.addItem(item);
                    }
                }
                for (ItemStack item : armorContents) {
                    if (item != null) {
                        doubleChestInventory.addItem(item);
                    }
                }
            }
        }
    }
}
