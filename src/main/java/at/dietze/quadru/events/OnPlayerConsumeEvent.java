package at.dietze.quadru.events;

import at.dietze.quadru.QuadruCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class OnPlayerConsumeEvent implements Listener {

    private static final NamespacedKey JOINT_KEY = new NamespacedKey(QuadruCore.getPlugin(), "weed_joint");

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.DRIED_KELP) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        Byte tag = pdc.get(JOINT_KEY, PersistentDataType.BYTE);
        if (tag == null || tag != (byte) 1) return; // only our custom kelp joint

        Player player = event.getPlayer();

        // Spawn smoke particles around the player's head
        Location loc = player.getLocation().add(0, 1.6, 0);
        player.getWorld().spawnParticle(Particle.SMOKE, loc, 30, 0.35, 0.4, 0.35, 0.01);

        // Apply effects: Luck, Hunger, Nausea
        player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 20 * 60, 0, true, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20 * 30, 0, true, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20 * 20, 0, true, true, true));
    }
}
