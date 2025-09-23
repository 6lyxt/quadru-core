package at.dietze.quadru.events

import org.bukkit.Location
import org.bukkit.entity.Arrow
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSneakEvent

/**
 *
 */
class OnPlayerSneakEvent : Listener {
    /**
     * @param e Event
     */
    @EventHandler
    fun onSneak(e: PlayerToggleSneakEvent) {
        val p = e.player

        if (p.vehicle is Arrow) {
            (p.vehicle as Arrow).remove()
            val loc =
                Location(p.world, p.location.x, p.location.y + 0.4, p.location.z, p.location.yaw, p.location.pitch)
            p.teleport(loc)
        }
    }
}