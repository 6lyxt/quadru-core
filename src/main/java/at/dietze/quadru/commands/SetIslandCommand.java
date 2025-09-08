package at.dietze.quadru.commands;

import at.dietze.quadru.constants.ICommand;
import at.dietze.quadru.constants.IStrings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetIslandCommand implements ICommand, CommandExecutor, IStrings {
    @Override
    public String getAction() {
        return "setIsland";
    }

    @Override
    public String getDescription() {
        return "Informiert über die Verwendung von Permissions für Inseln.";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        Player p = (Player) commandSender;

        if(command.getName().equalsIgnoreCase(this.getAction())) {
            if(p.hasPermission("core.admin")) {
                if(strings.length > 1) {
                    String targetPlayerName = strings[0];
                    String islandName = strings[1];
                    if(islandName.equalsIgnoreCase("Pyroka") || islandName.equalsIgnoreCase("Aloria")) {
                        p.sendMessage(prefix + "§eUm die Insel von §a" + targetPlayerName + " §eauf §a" + islandName + " §ezu setzen:");
                        p.sendMessage(prefix + "§7Verwende dein Permissions-Plugin und gib die Berechtigung:");
                        p.sendMessage(prefix + "§aquadru.island." + islandName.toLowerCase());
                        p.sendMessage(prefix + "§7Entferne ggf. andere Insel-Berechtigungen (pyroka/aloria).");
                    } else {
                        p.sendMessage(prefix + "§cDie Insel muss entweder 'Pyroka' oder 'Aloria' sein.");
                        return false;
                    }
                } else {
                    p.sendMessage(prefix + "§cCommandstruktur: /setIsland <Spielername> <Pyroka|Aloria>");
                    p.sendMessage(prefix + "§7Hinweis: Dieser Befehl zeigt jetzt nur die benötigten Permissions an.");
                }
            } else {
                p.sendMessage(prefix + "§cDu hast keine Rechte, um diesen Befehl auszuführen.");
            }
        }

        return true;
    }
}
