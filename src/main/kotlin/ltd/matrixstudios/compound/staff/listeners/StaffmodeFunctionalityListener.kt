package ltd.matrixstudios.compound.staff.listeners

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.staff.StaffItems
import ltd.matrixstudios.compound.staff.StaffSuiteVisibilityHandler
import ltd.matrixstudios.compound.staff.menu.StaffOnlineMenu
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityInteractEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.metadata.FixedMetadataValue
import java.util.concurrent.ThreadLocalRandom

class StaffmodeFunctionalityListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun interact(e: PlayerInteractEvent) {
        val player = e.player

        if (CompoundPlugin.instance.staffManager.isModMode(player))
        {
            if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK)
            {

                val itemInHand = player.itemInHand


                if (itemInHand.isSimilar(StaffItems.RANDOMTP))
                {
                    e.isCancelled = true
                    val actualPlayer = Bukkit.getOnlinePlayers().shuffled().first()

                    if (actualPlayer == null)
                    {
                        player.sendMessage(Chat.format("&cThis mf wasn't even found LOL"))
                        return
                    }

                    if (actualPlayer == player)
                    {
                        player.sendMessage(Chat.format("&cYou cannot teleport to yourself"))
                        return
                    }


                    player.teleport(actualPlayer)
                    player.sendMessage(Chat.format("&6Teleporting&f..."))
                }

                if (itemInHand.isSimilar(StaffItems.VANISH))
                {
                    player.inventory.itemInHand = StaffItems.UNVANISH

                    StaffSuiteVisibilityHandler.onDisableVisbility(player)

                    player.removeMetadata("vanish", CompoundPlugin.instance)
                }

                if (itemInHand.isSimilar(StaffItems.UNVANISH))
                {
                    player.inventory.itemInHand = StaffItems.VANISH

                    StaffSuiteVisibilityHandler.onEnableVisibility(player)

                    player.setMetadata("vanish", FixedMetadataValue(CompoundPlugin.instance, true))
                }

                if (itemInHand.isSimilar(StaffItems.ONLINE_STAFF))
                {
                    e.isCancelled = true
                    StaffOnlineMenu(player).updateMenu()
                }
            }
        }
    }
}