package at.dietze.quadru.events

import at.dietze.quadru.constants.IStrings
import at.dietze.quadru.constants.IStrings.Companion.prefix
import at.dietze.quadru.factories.NickRepository
import at.dietze.quadru.factories.PlayerNameFormatter.format
import at.dietze.quadru.factories.PlayerRepository
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.util.*

class OnPlayerJoinEvent : Listener, IStrings {
    /**
     * @param e event
     */
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val p = e.player

        val playerRepository = PlayerRepository()
        if (!playerRepository.playerExists(p)) {
            e.joinMessage =
                prefix + "§a" + Objects.requireNonNull(p.player).name + " wacht auf der Paravasa auf..."
            playerRepository.upsertPlayer(p)
        } else {
            val nickRepository = NickRepository()
            var nick = nickRepository.fetchNick(p)
            if (nick == null) {
                p.sendMessage(prefix + "§cDu hast noch keinen Nickname gesetzt. Nutze §e/nick <Nickname>§c um einen zu setzen.")
                e.joinMessage =
                    prefix + "§a" + Objects.requireNonNull(p.player).name + " betritt Quadru."
            } else {
                val islandName = playerRepository.fetchPlayerIsland(p)
                nick = format(nick, islandName)

                val scoreboard = Bukkit.getScoreboardManager().mainScoreboard
                val teamName = p.uniqueId.toString().substring(0, 16)
                var team = scoreboard.getTeam(teamName)
                if (team == null) {
                    team = scoreboard.registerNewTeam(teamName)
                }
                team.setPrefix("$nick [")
                team.setSuffix("]")
                team.addEntry(p.name)

                p.playerListName = nick
                p.displayName = nick
                p.customName = nick
                e.joinMessage =
                    prefix + "§a" + Objects.requireNonNull(p.player).customName + "§a betritt Quadru."
            }
        }

        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(prefix + "§aWillkommen auf Quadru!"))
    }
}
