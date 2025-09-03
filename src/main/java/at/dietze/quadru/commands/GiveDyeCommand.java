package at.dietze.quadru.commands;

import at.dietze.quadru.QuadruCore;
import at.dietze.quadru.constants.ILocalization;
import at.dietze.quadru.constants.IStrings;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public final class GiveDyeCommand {

    private GiveDyeCommand() {}

    public static void register(ReloadableRegistrarEvent<@NotNull Commands> commands) {
        commands.registrar().register(
                Commands.literal("givedye")
                        .requires(src -> src.getSender() instanceof Player && src.getSender().hasPermission("quadru.givedye"))
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                .executes(ctx -> {
                                    Player player = (Player) ctx.getSource().getSender();
                                    int amount = IntegerArgumentType.getInteger(ctx, "amount");
                                    giveTaggedGreenDye(player, amount);
                                    player.sendMessage(Component.text("Gave you ")
                                                    .color(TextColor.color(0x00FF00))
                                                    .append(Component.text(" x"))
                                                    .append(Component.text(amount).color(TextColor.color(0xFFFFFF)))
                                        .append(ILocalization.freshMarijuana));
                                    return 1;
                                })
                        ).build()
        );
    }

    private static void giveTaggedGreenDye(Player player, int amount) {
        if (amount <= 0) return;

        NamespacedKey key = new NamespacedKey(QuadruCore.getPlugin(), "special_dye");
        PlayerInventory inv = player.getInventory();

        int remaining = amount;
        while (remaining > 0) {
            int stackSize = Math.min(remaining, Material.GREEN_DYE.getMaxStackSize());
            ItemStack stack = new ItemStack(Material.GREEN_DYE, stackSize);

            ItemMeta meta = stack.getItemMeta();
            if (meta != null) {
                meta.displayName(
                        ILocalization.freshMarijuana
                );
                meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
                stack.setItemMeta(meta);
            }

            var leftovers = inv.addItem(stack);
            if (!leftovers.isEmpty()) {
                leftovers.values().forEach(it -> player.getWorld().dropItemNaturally(player.getLocation(), it));
            }
            remaining -= stackSize;
        }
    }
}