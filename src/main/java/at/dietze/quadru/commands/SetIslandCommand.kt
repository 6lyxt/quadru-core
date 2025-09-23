package at.dietze.quadru.commands;

import at.dietze.quadru.constants.ICommand;
import at.dietze.quadru.constants.IStrings;
import at.dietze.quadru.factories.PlayerRepository;
import org.bukkit.Bukkit;
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
        return "Setzt die Insel eines Spielers.";
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
                        Player toBeNicked = Bukkit.getPlayer(targetPlayerName);

                        if(toBeNicked == null) {
                            p.sendMessage(prefix + "§cDer Spieler §e" + targetPlayerName + " §cwurde nicht gefunden.");
                            return false;
                        }

                        PlayerRepository playerRepository = new PlayerRepository();
                        playerRepository.setPlayerIsland(toBeNicked, islandName);

                        p.sendMessage(prefix + "§aDie Insel von §e" + targetPlayerName + " §awurde zu §e" + islandName + " §ageändert.");
                    } else {
                        p.sendMessage(prefix + "§cDie Insel muss entweder 'Pyroka' oder 'Aloria' sein.");
                        return false;
                    }
                } else {
                    p.sendMessage(prefix + "§cCommandstruktur: /setIsland <Spielername> <Pyroka|Aloria>");
                }
            } else {
                p.sendMessage(prefix + "§cDu hast keine Rechte, um diesen Befehl auszuführen.");
            }
        }

        return true;
    }
}
