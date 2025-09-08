package at.dietze.quadru.events;

import at.dietze.quadru.constants.IStrings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

public class OnPlayerQuitEvent implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(IStrings.prefix + "§a" + p.getCustomName() + " §ahat das Spiel verlassen.");
        Team team = p.getScoreboard().getTeam(p.getUniqueId().toString().substring(0, 16));
        if (team != null) {
            team.removeEntry(p.getName());
            if (team.getEntries().isEmpty()) {
                team.unregister();
            }
        }

    }
}
