package at.dietze.quadru;

import at.dietze.quadru.commands.InvseeCommand;
import at.dietze.quadru.commands.SetNickCommand;
import at.dietze.quadru.commands.SitCommand;
import at.dietze.quadru.constants.IStrings;
import at.dietze.quadru.events.OnPlayerChatEvent;
import at.dietze.quadru.events.OnPlayerJoinEvent;
import at.dietze.quadru.events.OnPlayerSneakEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;

public final class QuadruCore extends JavaPlugin {

    public static Plugin plugin;
    public static ArrayList<String> descriptions = new ArrayList<>();

    public static ArrayList<String> getDescriptions() {
        return descriptions;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getConsoleSender().sendMessage(
                "§a[QuadruCore] Plugin enabled!"
        );

        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {

    }

    /**
     * @param action calling command, for example /help
     * @param desc   command description
     */
    public static void addToDescriptions(String action, String desc) {
        QuadruCore.descriptions.add("§a/" + action + "§7 - " + desc);
        Bukkit.getConsoleSender().sendMessage(IStrings.prefix + "§a" + action + " §7 wurde registriert.");
    }

    /**
     * helper method to require all events
     */
    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new OnPlayerChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerJoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerSneakEvent(), this);

        Bukkit.getConsoleSender().sendMessage(IStrings.prefix + "§a Events wurden registriert.");
    }

    /**
     * helper method to require all commands
     */
    private void registerCommands() {
        Objects.requireNonNull(this.getCommand("help")).setExecutor(new HelpCommand());
        Objects.requireNonNull(this.getCommand("setnick")).setExecutor(new SetNickCommand());
        Objects.requireNonNull(this.getCommand("sit")).setExecutor(new SitCommand());
        Objects.requireNonNull(this.getCommand("invsee")).setExecutor(new InvseeCommand());
        Bukkit.getConsoleSender().sendMessage(IStrings.prefix + "§a Befehle wurden registriert.");
    }


}
