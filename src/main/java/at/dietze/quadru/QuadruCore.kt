package at.dietze.quadru

import at.dietze.quadru.commands.*
import at.dietze.quadru.constants.IStrings
import at.dietze.quadru.events.*
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class QuadruCore : JavaPlugin() {
    override fun onEnable() {
        plugin = this
        Bukkit.getConsoleSender().sendMessage(
            "§a[QuadruCore] Plugin enabled!"
        )

        registerCommands()
        registerEvents()
    }

    override fun onDisable() {}

    /**
     * helper method to require all events
     */
    private fun registerEvents() {
        Bukkit.getPluginManager().registerEvents(OnPlayerChatEvent(), this)
        Bukkit.getPluginManager().registerEvents(OnPlayerJoinEvent(), this)
        Bukkit.getPluginManager().registerEvents(OnPlayerSneakEvent(), this)
        Bukkit.getPluginManager().registerEvents(PlayerSitOnStairs(), this)
        Bukkit.getPluginManager().registerEvents(OnPlayerQuitEvent(), this)
        Bukkit.getPluginManager().registerEvents(OnPlayerDeathEvent(), this)

        Bukkit.getConsoleSender().sendMessage(IStrings.prefix + "§a Events wurden registriert.")
    }

    /**
     * helper method to require all commands
     */
    private fun registerCommands() {
        Objects.requireNonNull(this.getCommand("help")).setExecutor(HelpCommand())
        Objects.requireNonNull(this.getCommand("setnick")).setExecutor(SetNickCommand())
        Objects.requireNonNull(this.getCommand("sit")).setExecutor(SitCommand())
        Objects.requireNonNull(this.getCommand("invsee")).setExecutor(InvseeCommand())
        Objects.requireNonNull(this.getCommand("setisland")).setExecutor(SetIslandCommand())
        Bukkit.getConsoleSender().sendMessage(IStrings.prefix + "§a Befehle wurden registriert.")
    }


    companion object {
        var plugin: Plugin? = null
        var descriptions: ArrayList<String> = ArrayList()

        /**
         * @param action calling command, for example /help
         * @param desc   command description
         */
        fun addToDescriptions(action: String, desc: String) {
            descriptions.add("§a/$action§7 - $desc")
            Bukkit.getConsoleSender().sendMessage(IStrings.prefix + "§a" + action + " §7 wurde registriert.")
        }
    }
}
