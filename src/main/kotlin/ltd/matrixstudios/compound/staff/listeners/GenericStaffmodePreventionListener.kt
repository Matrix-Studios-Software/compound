package ltd.matrixstudios.compound.staff.listeners

import ltd.matrixstudios.compound.CompoundPlugin
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerPickupItemEvent
import org.bukkit.event.player.PlayerQuitEvent

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
    fun damage(e: EntityDamageEvent) {
        val player = e.entity

        if (player is Player)
        {

            if (CompoundPlugin.instance.staffManager.isModMode(player))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun pickup(e: PlayerPickupItemEvent) {
        if (CompoundPlugin.instance.staffManager.isModMode(e.player))
        {
            if (!e.player.hasPermission("compound.staffmode.edit"))
                {

                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun drop(e: PlayerDropItemEvent)
    {
        if (CompoundPlugin.instance.staffManager.isModMode(e.player))
        {
            if (!e.player.hasPermission("compound.staffmode.edit"))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun playerQuit(e: PlayerQuitEvent)
    {
        if (CompoundPlugin.instance.staffManager.isModMode(e.player))
        {
            CompoundPlugin.instance.staffManager.removeStaffMode(e.player)
        }
    }


    @EventHandler
    fun damagedBy(e: EntityDamageByEntityEvent) {
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

    @EventHandler
    fun damagedFrom(e: EntityDamageByEntityEvent) {
        val player = e.entity
        val damager = e.damager

        if (player is Player && damager is Player)
        {

            if (CompoundPlugin.instance.staffManager.isModMode(damager))
            {
                e.isCancelled = true
            }
        }
    }
}