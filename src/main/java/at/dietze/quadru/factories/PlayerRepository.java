package at.dietze.quadru.factories;

import at.dietze.quadru.constants.IStrings;
import org.bukkit.entity.Player;

public class PlayerRepository {

    /**
     * @param p player
     */
    public void upsertPlayer(Player p) {
        // No longer needed with PDC storage
        // Players are automatically "registered" when they first join
    }

    /**
     * @param p player
     * @return true (all players exist now)
     */
    public boolean playerExists(Player p) {
        // With PDC storage, all players "exist" 
        return true;
    }

    /**
     * Sets player island by informing admin to use permission plugin
     * @param p player (admin executing command)
     * @param islandName island name
     */
    public void setPlayerIsland(Player p, String islandName) {
        // Since we now use permission groups, inform admin to use permission plugin
        p.sendMessage(IStrings.prefix + "§eHinweis: Verwende dein Permissions-Plugin, um die Berechtigung §aquadru.island." + islandName.toLowerCase() + " §ezu setzen.");
    }

    /**
     * Gets player island from permission groups
     * @param p player
     * @return island name
     */
    public String fetchPlayerIsland(Player p) {
        if (p.hasPermission("quadru.island.pyroka")) {
            return "pyroka";
        } else if (p.hasPermission("quadru.island.aloria")) {
            return "aloria";
        }
        
        return "OBDACHLOS";
    }
}
