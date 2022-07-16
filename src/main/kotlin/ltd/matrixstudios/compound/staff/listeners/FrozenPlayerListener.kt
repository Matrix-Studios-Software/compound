package ltd.matrixstudios.compound.staff.listeners

import ltd.matrixstudios.compound.chat.Chat
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerMoveEvent

class FrozenPlayerListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun move(e: PlayerMoveEvent) {
        val player = e.player

        if (player.hasMetadata("frozen"))
        {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun damage(e: EntityDamageByEntityEvent)
    {
        val entity = e.entity

        if (entity is Player)
        {
            if (entity.hasMetadata("frozen"))
            {
                val damager = e.damager

                damager.sendMessage(Chat.format("&cPlayer is currently frozen and cannot be damaged"))

                e.isCancelled = true
            }
        }
    }
}