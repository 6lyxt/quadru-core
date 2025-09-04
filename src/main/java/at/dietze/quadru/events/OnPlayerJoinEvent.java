package at.dietze.quadru.events;

import at.dietze.quadru.constants.IStrings;
import at.dietze.quadru.factories.NickRepository;
import at.dietze.quadru.factories.PlayerNameFormatter;
import at.dietze.quadru.factories.PlayerRepository;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class OnPlayerJoinEvent implements Listener {

    /**
     * @param e event
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        PlayerRepository playerRepository = new PlayerRepository();
        if(!playerRepository.playerExists(p)) {
            e.setJoinMessage(IStrings.prefix + "§a" + Objects.requireNonNull(p.getPlayer()).getName() + " wacht auf der Paravasa auf...");
            playerRepository.upsertPlayer(p);
        } else {
            e.setJoinMessage(IStrings.prefix + "§a" + Objects.requireNonNull(p.getPlayer()).getName() + " betritt Quadru.");
            NickRepository nickRepository = new NickRepository();
            String nick = nickRepository.fetchNick(p);
            if(nick == null) {
                p.sendMessage(IStrings.prefix + "§cDu hast noch keinen Nickname gesetzt. Nutze §e/nick <Nickname>§c um einen zu setzen.");
            } else {
                String islandName = playerRepository.fetchPlayerIsland(p);
                PlayerNameFormatter.format(nick, islandName);
                p.setDisplayName(nick);
            }
        }

        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(IStrings.prefix + "§aWillkommen auf Quadru!"));

    }
}
