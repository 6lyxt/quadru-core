package at.dietze.quadru.factories;

import at.dietze.quadru.QuadruCore;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class NickRepository {

    private static final NamespacedKey NICK_KEY = new NamespacedKey(QuadruCore.getPlugin(), "player_nick");

    /**
     * @param p    Player
     * @param nick Nickname
     */
    public void upsertNick(Player p, String nick) {
        p.getPersistentDataContainer().set(NICK_KEY, PersistentDataType.STRING, nick);
    }

    /**
     * @param p Player
     * @return Nickname
     */
    public String fetchNick(Player p) {
        return p.getPersistentDataContainer().get(NICK_KEY, PersistentDataType.STRING);
    }
}
