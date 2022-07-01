package ltd.matrixstudios.compound.staff.listeners

import ltd.matrixstudios.compound.CompoundPlugin
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class GenericStaffmodePreventionListener : Listener {

    @EventHandler
    fun breakBlock(e: BlockBreakEvent)
    {
        val player = e.player

        if (CompoundPlugin.instance.staffManager.isModMode(player))
        {
            if (!player.hasPermission("compound.staffmode.edit"))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun placeBlock(e: BlockPlaceEvent)
    {
        val player = e.player

        if (CompoundPlugin.instance.staffManager.isModMode(player))
        {
            if (!player.hasPermission("compound.staffmode.edit"))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun interact(e: PlayerInteractEvent)
    {
        val player = e.player

        if (CompoundPlugin.instance.staffManager.isModMode(player))
        {
            if (!player.hasPermission("compound.staffmode.edit"))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun damage(e: EntityDamageByEntityEvent) {
        val player = e.damager

        if (player is Player)
        {

            if (CompoundPlugin.instance.staffManager.isModMode(player))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun damage2(e: EntityDamageByEntityEvent) {
        val player = e.entity
        val damager = e.damager

        if (player is Player && damager is Player)
        {

            if (CompoundPlugin.instance.staffManager.isModMode(player))
            {
                e.isCancelled = true
            }
        }
    }
}