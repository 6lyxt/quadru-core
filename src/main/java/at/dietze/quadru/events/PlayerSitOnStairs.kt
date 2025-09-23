package at.dietze.quadru.events

import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.data.type.Stairs
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class PlayerSitOnStairs : Listener {
    @EventHandler
    fun playerSitOnStairs(e: PlayerInteractEvent) {
        if (e.clickedBlock == null) return
        if (e.action != Action.RIGHT_CLICK_BLOCK) return
        val p = e.player

        if (p.isSneaking) return
        if (p.inventory.itemInMainHand.type != Material.AIR) return

        val block = e.clickedBlock

        val aboveBlock = block!!.getRelative(BlockFace.UP)
        if (!aboveBlock.isEmpty) return

        val blockData = block.blockData as? Stairs ?: return

        val stairs = blockData
        if (stairs.shape != Stairs.Shape.STRAIGHT) return

        val facing = stairs.facing

        val spawnLocation = block.location.add(0.5, 0.25, 0.5)

        when (facing) {
            BlockFace.NORTH -> spawnLocation.add(0.0, 0.0, 0.25)
            BlockFace.SOUTH -> spawnLocation.add(0.0, 0.0, -0.25)
            BlockFace.EAST -> spawnLocation.add(-0.25, 0.0, 0.0)
            BlockFace.WEST -> spawnLocation.add(0.25, 0.0, 0.0)
            BlockFace.UP -> TODO()
            BlockFace.DOWN -> TODO()
            BlockFace.NORTH_EAST -> TODO()
            BlockFace.NORTH_WEST -> TODO()
            BlockFace.SOUTH_EAST -> TODO()
            BlockFace.SOUTH_WEST -> TODO()
            BlockFace.WEST_NORTH_WEST -> TODO()
            BlockFace.NORTH_NORTH_WEST -> TODO()
            BlockFace.NORTH_NORTH_EAST -> TODO()
            BlockFace.EAST_NORTH_EAST -> TODO()
            BlockFace.EAST_SOUTH_EAST -> TODO()
            BlockFace.SOUTH_SOUTH_EAST -> TODO()
            BlockFace.SOUTH_SOUTH_WEST -> TODO()
            BlockFace.WEST_SOUTH_WEST -> TODO()
            BlockFace.SELF -> TODO()
        }

        val seat = p.world.spawnEntity(spawnLocation, EntityType.ARROW)

        seat.setGravity(false)
        seat.isInvulnerable = true
        seat.isSilent = true
        seat.isPersistent = false

        seat.addPassenger(p)
    }
}
