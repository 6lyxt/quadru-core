package at.dietze.quadru.recipes;

import at.dietze.quadru.QuadruCore;
import at.dietze.quadru.constants.ILocalization;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public final class CustomRecipes {

    private CustomRecipes() {}

    public static void registerAll(Plugin plugin) {
        registerSpecialDyeSmelting(plugin);
    }

    private static void registerSpecialDyeSmelting(Plugin plugin) {
        // Input: the specially tagged green dye (matches GiveDyeCommand)
        ItemStack input = new ItemStack(Material.GREEN_DYE, 1);
        ItemMeta inMeta = input.getItemMeta();
        if (inMeta != null) {
            inMeta.displayName(ILocalization.freshMarijuana);
            inMeta.getPersistentDataContainer().set(new NamespacedKey(QuadruCore.getPlugin(), "special_dye"), PersistentDataType.BYTE, (byte) 1);
            input.setItemMeta(inMeta);
        }

        // Output: named and tagged dead bush
        ItemStack result = new ItemStack(Material.DEAD_BUSH, 1);
        ItemMeta outMeta = result.getItemMeta();
        if (outMeta != null) {
            outMeta.displayName(ILocalization.driedMarijuana);
            outMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "special_dead_bush"), PersistentDataType.BYTE, (byte) 1);
            result.setItemMeta(outMeta);
        }

        NamespacedKey key = new NamespacedKey(plugin, "special_dye_smelting");
        // Remove any old recipe with the same key to avoid duplicates on reloads
        try {
            Bukkit.removeRecipe(key);
        } catch (Throwable ignored) {}

        FurnaceRecipe recipe = new FurnaceRecipe(key, result, new RecipeChoice.ExactChoice(input), 0.1f, 200);
        Bukkit.addRecipe(recipe);
    }
}

