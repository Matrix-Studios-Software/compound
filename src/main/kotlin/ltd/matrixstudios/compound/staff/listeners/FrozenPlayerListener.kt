package ltd.matrixstudios.compound.staff.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class FrozenPlayerListener : Listener {

    @EventHandler
    fun move(e: PlayerMoveEvent) {
        val player = e.player

        if (player.hasMetadata("frozen"))
        {
            e.isCancelled = true
        }
    }
}