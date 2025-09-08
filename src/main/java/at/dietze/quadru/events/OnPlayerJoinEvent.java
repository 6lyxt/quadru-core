package at.dietze.quadru.events;

import at.dietze.quadru.constants.IStrings;
import at.dietze.quadru.factories.NickRepository;
import at.dietze.quadru.factories.PlayerNameFormatter;
import at.dietze.quadru.factories.PlayerRepository;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class OnPlayerJoinEvent implements Listener {

    /**
     * @param e event
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        NickRepository nickRepository = new NickRepository();
        String nick = nickRepository.fetchNick(p);
        
        if (nick == null) {
            p.sendMessage(IStrings.prefix + "§cDu hast noch keinen Nickname gesetzt. Nutze §e/setnick <Nickname>§c um einen zu setzen.");
            e.setJoinMessage(IStrings.prefix + "§a" + Objects.requireNonNull(p.getPlayer()).getName() + " betritt Quadru.");
        } else {
            PlayerRepository playerRepository = new PlayerRepository();
            String islandName = playerRepository.fetchPlayerIsland(p);
            nick = PlayerNameFormatter.format(nick, islandName);

            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            String teamName = p.getUniqueId().toString().substring(0, 16);
            Team team = scoreboard.getTeam(teamName);
            if (team == null) {
                team = scoreboard.registerNewTeam(teamName);
            }
            team.setPrefix(nick + " [");
            team.setSuffix("]");
            team.addEntry(p.getName());

            p.setPlayerListName(nick);
            p.setDisplayName(nick);
            p.setCustomName(nick);
            e.setJoinMessage(IStrings.prefix + "§a" + Objects.requireNonNull(p.getPlayer()).getCustomName() + "§a betritt Quadru.");
        }

        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(IStrings.prefix + "§aWillkommen auf Quadru!"));
    }
}
