package at.dietze.quadru.commands;

import at.dietze.quadru.QuadruCore;
import at.dietze.quadru.constants.ICommand;
import at.dietze.quadru.constants.IStrings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HelpCommand implements ICommand, IStrings, CommandExecutor {

    public HelpCommand() {
        QuadruCore.addToDescriptions(this.getAction(), this.getDescription());
    }

    @Override
    public String getAction() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Zeigt diesen Help-Command an.";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        Player p = (Player) commandSender;

        if (command.getName().equalsIgnoreCase(this.getAction())) {
            p.sendMessage(prefix + "§6§lHilfe Menü");
            QuadruCore.getDescriptions().forEach(p::sendMessage);
        }

        return true;
    }
}
