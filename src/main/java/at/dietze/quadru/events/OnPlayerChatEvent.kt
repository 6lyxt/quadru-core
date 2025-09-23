package at.dietze.quadru.events

import at.dietze.quadru.constants.IStrings
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.*

class OnPlayerChatEvent : Listener, IStrings {
    /**
     * @param e AsyncPlayerChatEvent
     */
    @EventHandler
    fun onPlayerChat(e: AsyncPlayerChatEvent) {
        val rawMsg = e.message
        val msg = IStrings.prefix + "§7[privat] <§a" + e.player.customName + "§7> " + rawMsg

        val dst = 100

        val playerLoc = e.player.location

        for (pl in (Bukkit.getServer().getWorld(e.player.world.uid))?.players!!) {
            if (pl.location.distanceSquared(playerLoc) <= dst && !rawMsg.lowercase(Locale.getDefault())
                    .startsWith("@all")
            ) {
                pl.sendMessage(msg)
                Bukkit.getConsoleSender().sendMessage(msg)
            } else if (rawMsg.lowercase(Locale.getDefault()).startsWith("@all")) {
                Bukkit.getConsoleSender().sendMessage(
                    IStrings.prefix + "§7[alle] <§a" + e.player.customName + "§7>" + rawMsg.replace(
                        "@all",
                        ""
                    )
                )
                Bukkit.broadcastMessage(
                    IStrings.prefix + "§7[alle] <§a" + e.player.customName + "§7>" + rawMsg.replace(
                        "@all",
                        ""
                    )
                )
                break
            }
        }

        e.isCancelled = true
    }
}
