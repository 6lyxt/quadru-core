package at.dietze.quadru.commands;

import at.dietze.quadru.constants.ICommand;
import at.dietze.quadru.constants.IStrings;
import at.dietze.quadru.factories.NickRepository;
import at.dietze.quadru.factories.PlayerNameFormatter;
import at.dietze.quadru.factories.PlayerRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetNickCommand implements ICommand, IStrings, CommandExecutor {
    @Override
    public String getAction() {
        return "setNick";
    }

    @Override
    public String getDescription() {
        return "Setzt deinen Nicknamen.";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        Player p = (Player) commandSender;

        if(command.getName().equalsIgnoreCase(this.getAction())) {
            if(strings.length > 0) {
                String newNick = strings[0];
                PlayerRepository playerRepository = new PlayerRepository();
                String islandName = playerRepository.fetchPlayerIsland(p).toUpperCase();
                NickRepository nickRepository = new NickRepository();
                nickRepository.upsertNick(p, newNick);

                newNick = PlayerNameFormatter.format(newNick, islandName);
                p.setDisplayName(newNick);
                p.setCustomName(newNick);
                p.setPlayerListName(newNick);

                p.sendMessage(prefix + "§aDein Nickname wurde zu §e" + newNick + " §ageändert. Wir empfehlen, einmal neuzujoinen, damit der Nickname überall übernommen wird.");
            } else {
                p.sendMessage(prefix + "§cBitte gib einen Nicknamen an.");
            }
        }

        return true;
    }
}
