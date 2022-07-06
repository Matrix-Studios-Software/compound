package ltd.matrixstudios.compound.inventory

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object InventoryUtils {

    fun getOpenSlots(player: Player) : Int
    {
        var index = 0

        for (item in player.inventory.contents)
        {
            if (item != null && item.type != Material.AIR)
            {
                index++
            }
        }

        return index
    }

    fun inventoryHasFreeSpace(player: Player) : Boolean
    {
        var found = false

        for (item in player.inventory.contents)
        {
            if (item != null)
            {
                found = true
            }
        }

        return found
    }

    fun convertItemsToHashMap(player: Player) : Map<Int, ItemStack>
    {
        val hashMap = hashMapOf<Int, ItemStack>()
        val items = player.inventory.contents

        for (item in items)
        {
            if (item != null)
            {
                hashMap[items.indexOf(item)] = item
            }
        }

        return hashMap
    }
}