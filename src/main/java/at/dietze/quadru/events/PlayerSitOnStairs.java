package at.dietze.quadru.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerSitOnStairs implements Listener {

    @EventHandler
    public void playerSitOnStairs(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        Player p = e.getPlayer();

        Block block = e.getClickedBlock();
        BlockData blockData = block.getBlockData();

        if (!(blockData instanceof Stairs) || p.isSneaking()) return;


        Stairs stairs = (Stairs) blockData;
        if (stairs.getShape() != Stairs.Shape.STRAIGHT) return;

        Entity arrow = p.getWorld().spawnEntity(block.getLocation().subtract(0D, 0.4D, 0D), EntityType.ARROW);
        arrow.setGravity(false);
        arrow.addPassenger(p);
    }
}
