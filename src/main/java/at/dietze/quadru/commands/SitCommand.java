package at.dietze.quadru.commands;

import at.dietze.quadru.QuadruCore;
import at.dietze.quadru.constants.ICommand;
import at.dietze.quadru.constants.IStrings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SitCommand implements ICommand, CommandExecutor, IStrings {

    public SitCommand() {
        QuadruCore.addToDescriptions(this.getAction(), this.getDescription());
    }

    @Override
    public String getAction() {
        return "sit";
    }

    @Override
    public String getDescription() {
        return "sitz wie hund wuff wuff";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        Player p = (Player) commandSender;

        if(command.getName().equalsIgnoreCase(this.getAction())) {
            this.spawnHelperEntity(p);
        }

        return false;
    }

    /**
     * @param p Player
     */
    private void spawnHelperEntity(Player p) {
        if(p.isInsideVehicle()) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(prefix + "§cDu sitzt bereits!"));
            return;
        }

        if(!p.isOnGround()) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(prefix + "§cDu musst stehen, um zu sitzen!"));
            return;
        }

        Entity arrow = p.getWorld().spawnEntity(p.getLocation().subtract(0D, 0.4D, 0D), EntityType.ARROW);
        arrow.setGravity(false);
        arrow.addPassenger(p);
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(prefix + "§aUm aufzustehen, drücke SHIFT."));

    }
}
