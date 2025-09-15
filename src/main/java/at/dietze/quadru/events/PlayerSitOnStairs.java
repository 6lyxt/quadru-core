package at.dietze.quadru.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerSitOnStairs implements Listener {

    @EventHandler
    public void playerSitOnStairs(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player p = e.getPlayer();

        if (p.isSneaking()) return;
        if (p.getInventory().getItemInMainHand().getType() != Material.AIR) return;

        Block block = e.getClickedBlock();

        Block aboveBlock = block.getRelative(BlockFace.UP);
        if (!aboveBlock.isEmpty()) return;

        BlockData blockData = block.getBlockData();

        if (!(blockData instanceof Stairs)) return;

        Stairs stairs = (Stairs) blockData;
        if (stairs.getShape() != Stairs.Shape.STRAIGHT) return;

        BlockFace facing = stairs.getFacing();

        Location spawnLocation = block.getLocation().add(0.5, 0.25, 0.5);

        switch (facing) {
            case NORTH:
                spawnLocation.add(0, 0, 0.25);
                break;
            case SOUTH:
                spawnLocation.add(0, 0, -0.25);
                break;
            case EAST:
                spawnLocation.add(-0.25, 0, 0);
                break;
            case WEST:
                spawnLocation.add(0.25, 0, 0);
                break;
        }

        Entity seat = p.getWorld().spawnEntity(spawnLocation, EntityType.ARROW);

        seat.setGravity(false);
        seat.setInvulnerable(true);
        seat.setSilent(true);
        seat.setPersistent(false);

        seat.addPassenger(p);
    }
}
