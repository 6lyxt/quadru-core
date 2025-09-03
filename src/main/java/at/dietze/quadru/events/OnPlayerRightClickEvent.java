package at.dietze.quadru.events;

import at.dietze.quadru.QuadruCore;
import at.dietze.quadru.constants.ILocalization;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class OnPlayerRightClickEvent implements Listener {

    private static final NamespacedKey DEAD_BUSH_DATA_KEY = new NamespacedKey(QuadruCore.getPlugin(), "special_dead_bush");

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        // Only handle right-clicks
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        // Process only once per interaction (ignore off-hand duplicate calls)
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();

        ItemStack main = inv.getItemInMainHand();
        ItemStack off = inv.getItemInOffHand();

        boolean pairMainPaper = isPaper(main) && isTaggedDeadBush(off);
        boolean pairOffPaper = isPaper(off) && isTaggedDeadBush(main);

        if (!pairMainPaper && !pairOffPaper) return;

        // Consume items from corresponding hands
        if (pairMainPaper) {
            consumeOneFromMainHand(inv);
            consumeOneFromOffHand(inv);
        } else {
            // pairOffPaper
            consumeOneFromOffHand(inv);
            consumeOneFromMainHand(inv);
        }

        // Give stick (drop if inventory is full)

        ItemStack joint = new ItemStack(Material.DRIED_KELP, 1);
        ItemMeta meta = joint.getItemMeta();
        if (meta != null) {
            meta.displayName(ILocalization.joint);
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            pdc.set(new NamespacedKey(QuadruCore.getPlugin(), "weed_joint"), PersistentDataType.BYTE, (byte) 1);
            joint.setItemMeta(meta);
        }
        Map<Integer, ItemStack> leftovers = inv.addItem(joint);
        if (!leftovers.isEmpty()) {
            player.getWorld().dropItemNaturally(player.getLocation(), joint);
        }

        // Prevent default use of the items
        event.setCancelled(true);
    }

    private boolean isPaper(ItemStack stack) {
        return stack != null && stack.getType() == Material.PAPER && stack.getAmount() > 0;
    }

    private boolean isTaggedDeadBush(ItemStack stack) {
        if (stack == null || stack.getType() != Material.DEAD_BUSH || stack.getAmount() <= 0) return false;
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return false;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        // Adjust the key/type as needed to match your actual custom tag
        if (pdc.has(DEAD_BUSH_DATA_KEY, PersistentDataType.BYTE)) {
            Byte v = pdc.get(DEAD_BUSH_DATA_KEY, PersistentDataType.BYTE);
            return v != null && v == (byte) 1;
        }
        if (pdc.has(DEAD_BUSH_DATA_KEY, PersistentDataType.INTEGER)) {
            Integer v = pdc.get(DEAD_BUSH_DATA_KEY, PersistentDataType.INTEGER);
            return v != null && v != 0;
        }
        if (pdc.has(DEAD_BUSH_DATA_KEY, PersistentDataType.STRING)) {
            String v = pdc.get(DEAD_BUSH_DATA_KEY, PersistentDataType.STRING);
            return v != null && !v.isEmpty();
        }
        return false;
    }

    private void consumeOneFromMainHand(PlayerInventory inv) {
        ItemStack s = inv.getItemInMainHand();
        if (s == null || s.getType() == Material.AIR) return;
        int newAmt = s.getAmount() - 1;
        if (newAmt <= 0) {
            inv.setItemInMainHand(new ItemStack(Material.AIR));
        } else {
            s.setAmount(newAmt);
            inv.setItemInMainHand(s);
        }
    }

    private void consumeOneFromOffHand(PlayerInventory inv) {
        ItemStack s = inv.getItemInOffHand();
        if (s == null || s.getType() == Material.AIR) return;
        int newAmt = s.getAmount() - 1;
        if (newAmt <= 0) {
            inv.setItemInOffHand(new ItemStack(Material.AIR));
        } else {
            s.setAmount(newAmt);
            inv.setItemInOffHand(s);
        }
    }
}