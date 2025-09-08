package at.dietze.quadru.events;

import at.dietze.quadru.constants.IStrings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeathEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setDeathMessage(IStrings.prefix + "§c" + e.getEntity().getPlayer().getCustomName() + " §cist gestorben.");
    }
}
