package at.dietze.quadru.commands;

import at.dietze.quadru.QuadruCore;
import at.dietze.quadru.constants.ICommand;
import at.dietze.quadru.constants.IStrings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class InvseeCommand implements ICommand, CommandExecutor, IStrings {

    public InvseeCommand() {
        QuadruCore.addToDescriptions(this.getAction(), this.getDescription());
    }

    @Override
    public String getAction() {
        return "invsee";
    }

    @Override
    public String getDescription() {
        return "Öffnet das Inventar eines anderen Spielers.";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        Player p = (Player) commandSender;

        if (command.getName().equalsIgnoreCase(this.getAction())) {
            if (p.hasPermission("core.admin")) {
                if (strings.length > 0) {
                    Player invPlayer = Bukkit.getPlayer(strings[0]);
                    if (invPlayer != null) {
                        Inventory inv = invPlayer.getInventory();
                        p.openInventory(inv);
                    } else {
                        p.sendMessage(prefix + "§cDieser Spieler wurde leider nicht gefunden.");
                    }
                }
            } else {
                p.sendMessage(prefix + "§cDu hast keine Rechte, um diesen Befehl auszuführen.");
            }
        }

        return true;
    }
}
