package ltd.matrixstudios.compound.staff.listeners

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.staff.StaffItems
import ltd.matrixstudios.compound.staff.menu.StaffOnlineMenu
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityInteractEvent
import org.bukkit.event.inventory.InventoryClickEvent
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
                    player.sendMessage(Chat.format("&6Teleporting..."))
                }

                if (itemInHand.isSimilar(StaffItems.VANISH))
                {
                    player.inventory.itemInHand = StaffItems.UNVANISH

                    player.performCommand("vanish")
                }

                if (itemInHand.isSimilar(StaffItems.UNVANISH))
                {
                    player.inventory.itemInHand = StaffItems.VANISH

                    player.performCommand("vanish")
                }

                if (itemInHand.isSimilar(StaffItems.ONLINE_STAFF))
                {
                    e.isCancelled = true
                    StaffOnlineMenu(player).updateMenu()
                }

                if (itemInHand.isSimilar(StaffItems.INVENTORY_INSPECT))
                {
                    e.isCancelled = true
                }

                if (itemInHand.isSimilar(StaffItems.FREEZE))
                {
                    e.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun interactWithEntity(e: PlayerInteractEntityEvent)
    {
        val player = e.player

        if (CompoundPlugin.instance.staffManager.isModMode(player))
        {
            val itemInHand = player.itemInHand

            if (e.rightClicked is Player)
            {
                if (itemInHand.isSimilar(StaffItems.INVENTORY_INSPECT))
                {
                    player.performCommand("invsee ${e.rightClicked.name}")
                    e.isCancelled = true
                }

                if (itemInHand.isSimilar(StaffItems.FREEZE))
                {
                    player.performCommand("freeze ${e.rightClicked.name}")
                    e.isCancelled = true
                }
            }
        }
    }
}