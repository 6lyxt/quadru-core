package at.dietze.quadru.events

import at.dietze.quadru.constants.IStrings
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class OnPlayerQuitEvent : Listener, IStrings {
    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        val p = e.player
        e.quitMessage = IStrings.prefix + "§a" + p.customName + " §ahat das Spiel verlassen."
        val team = p.scoreboard.getTeam(p.uniqueId.toString().substring(0, 16))
        if (team != null) {
            team.removeEntry(p.name)
            if (team.entries.isEmpty()) {
                team.unregister()
            }
        }
    }
}
