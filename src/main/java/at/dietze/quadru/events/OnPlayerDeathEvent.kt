package at.dietze.quadru.events

import at.dietze.quadru.constants.IStrings
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.data.type.Chest
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class OnPlayerDeathEvent : Listener {
    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        val p = e.player
        e.deathMessage = IStrings.prefix + "§c" + p.customName + " §cist gestorben."
        e.drops.clear()
        try {
            this.spawnDeathChest(p)
            p.sendMessage(IStrings.prefix + "§aDeine Todeskiste befindet sich hier: §7" + p.location.blockX + "§8/§7" + p.location.blockY + "§8/§7" + p.location.blockZ + " §ain Welt: §7" + p.world.name)
            p.inventory.clear()
        } catch (ex: Exception) {
            p.sendMessage(IStrings.prefix + "§cFehler beim Erstellen der Todestruhe, bitte kontaktiere einen Staff.")
            Bukkit.getConsoleSender()
                .sendMessage(IStrings.prefix + "§cFehler beim Erstellen der Todestruhe für " + p.customName + ": " + ex.message)
            return
        }
    }

    private fun spawnDeathChest(p: Player) {
        val deathLocation = p.location.toBlockLocation()
        val chestLocation1 = deathLocation.clone()

        while (!chestLocation1.block.isEmpty) {
            chestLocation1.add(0.0, 1.0, 0.0)
        }
        val chestLocation2 = chestLocation1.clone().add(1.0, 0.0, 0.0)

        // i wanted to originally check around the player but that resulted in in facing issues for some reason
        if (!chestLocation2.block.isEmpty) {
            while (!chestLocation2.block.isEmpty) {
                chestLocation1.add(0.0, 1.0, 0.0)
                chestLocation2.add(0.0, 1.0, 0.0)
            }
        }

        val chest1Block = chestLocation1.block
        val chest2Block = chestLocation2.block

        chest1Block.type = Material.CHEST
        chest2Block.type = Material.CHEST

        val data1 = chest1Block.blockData
        val data2 = chest2Block.blockData

        // since someone (who should be shot btw) randomly decided to name the type Chest and the Block Chest the same,
        // we need to fully import the types name, since java doesnt have named import (kotlin, scala etc have them tho) (i want to die)
        if (data1 is Chest && data2 is Chest) {
            data1.type = Chest.Type.RIGHT
            data2.type = Chest.Type.LEFT

            data1.facing = BlockFace.SOUTH
            data2.facing = BlockFace.SOUTH

            chest1Block.blockData = data1
            chest2Block.blockData = data2

            val chestState = chest1Block.state
            if (chestState is org.bukkit.block.Chest) {
                val chest = chestState
                val doubleChestInventory = chest.inventory

                chest.customName = "Grab von " + p.customName
                chest.update()

                val playerContents = p.inventory.contents

                for (item in playerContents) {
                    if (item != null) {
                        doubleChestInventory.addItem(item)
                    }
                }
            }
        }
    }
}
