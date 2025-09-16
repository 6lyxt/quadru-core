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

    private org.bukkit.block.data.type.Chest TypeChest;


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        e.setDeathMessage(IStrings.prefix + "§c" + p.getCustomName() + " §cist gestorben.");

        Location deathLocation = p.getLocation().toBlockLocation();
        Location chestLocation1 = deathLocation.clone();
        Location chestLocation2 = deathLocation.clone().add(0, 0, 1);

        while (!chestLocation1.getBlock().isEmpty() || !chestLocation2.getBlock().isEmpty()) {
            chestLocation1.add(0, 1, 0);
            chestLocation2.add(0, 1, 0);
        }

        Block chest1Block = chestLocation1.getBlock();
        Block chest2Block = chestLocation2.getBlock();

        chest1Block.setType(Material.CHEST);
        chest2Block.setType(Material.CHEST);

        BlockData data1 = chest1Block.getBlockData();
        BlockData data2 = chest2Block.getBlockData();

        if (data1 instanceof Chest && data2 instanceof Chest) {
            org.bukkit.block.data.type.Chest chestData1 = (org.bukkit.block.data.type.Chest) data1;
            org.bukkit.block.data.type.Chest chestData2 = (org.bukkit.block.data.type.Chest) data2;

            chestData1.setType(org.bukkit.block.data.type.Chest.Type.LEFT);
            chestData2.setType(org.bukkit.block.data.type.Chest.Type.RIGHT);
            chestData1.setFacing(BlockFace.SOUTH);
            chestData2.setFacing(BlockFace.SOUTH);

            chest1Block.setBlockData(chestData1);
            chest2Block.setBlockData(chestData2);

            BlockState chestState = chest1Block.getState();

            if (chestState instanceof org.bukkit.block.Chest) {
                org.bukkit.block.Chest chest = (Chest) chestState;
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

            p.getInventory().clear();
            e.getDrops().clear();
        }

    }
}
