package ltd.matrixstudios.compound.staff

import ltd.matrixstudios.compound.CompoundPlugin
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import java.util.*

class StaffSuiteManager {

    var modInventories = hashMapOf<UUID, Array<ItemStack?>>()
    var modArmor = hashMapOf<UUID, Array<ItemStack?>>()

    var modModePlayers = arrayListOf<UUID>()

    fun isModMode(player: Player) : Boolean
    {
        return modModePlayers.contains(player.uniqueId)
    }

    fun removeStaffMode(player: Player)
    {
        player.gameMode = GameMode.SURVIVAL
        player.inventory.clear()

        if (modInventories.containsKey(player.uniqueId)) {
            val items = modInventories[player.uniqueId]!!

            player.inventory.contents = items
        }

        if (modArmor.containsKey(player.uniqueId))
        {

            val armor = modArmor[player.uniqueId]!!

            player.inventory.armorContents = armor
        }

        player.updateInventory()

        modModePlayers.remove(player.uniqueId)
        modInventories.remove(player.uniqueId)
        modArmor.remove(player.uniqueId)

        StaffSuiteVisibilityHandler.onDisableVisbility(player)

        player.removeMetadata("modmode", CompoundPlugin.instance)
        player.removeMetadata("vanish", CompoundPlugin.instance)

    }

    fun setStaffMode(player: Player)
    {
        player.gameMode = GameMode.CREATIVE
        player.health = 20.0

        modInventories[player.uniqueId] = player.inventory.contents
        modArmor[player.uniqueId] = player.inventory.armorContents
        modModePlayers.add(player.uniqueId)

        player.inventory.setItem(0, StaffItems.COMPASS)
        player.inventory.setItem(1, StaffItems.INVENTORY_INSPECT)
        player.inventory.setItem(2, StaffItems.RANDOMTP)
        player.inventory.setItem(3, StaffItems.BETTER_VIEW)
        player.inventory.setItem(5, StaffItems.ONLINE_STAFF)
        player.inventory.setItem(6, StaffItems.VANISH)
        player.inventory.setItem(7, StaffItems.REPORTS)
        player.inventory.setItem(8, StaffItems.MINER_TP)

        player.updateInventory()

        StaffSuiteVisibilityHandler.onEnableVisibility(player)

        player.setMetadata("modmode", FixedMetadataValue(CompoundPlugin.instance, true))
        player.setMetadata("vanish", FixedMetadataValue(CompoundPlugin.instance, true))
    }
}