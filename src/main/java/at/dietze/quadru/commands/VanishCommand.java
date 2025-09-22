package at.dietze.quadru.commands;

import at.dietze.quadru.constants.IStrings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class VanishCommand implements CommandExecutor, IStrings {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        Player p = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("vanish")) {
            if (p.hasPermission("core.admin")) {
                PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false);
                if (p.hasPotionEffect(invisibility.getType())) {
                    p.removePotionEffect(invisibility.getType());
                    p.sendMessage(prefix + "§aDu bist nun nicht mehr im Vanish-Modus.");
                } else {
                    p.addPotionEffect(invisibility);
                    p.sendMessage(prefix + "§aDu bist nun im Vanish-Modus.");
                }
                return true;
            } else {
                p.sendMessage(prefix + "§cDu hast keine Rechte, um diesen Befehl auszuführen.");
            }
        }

        return false;
    }
}
