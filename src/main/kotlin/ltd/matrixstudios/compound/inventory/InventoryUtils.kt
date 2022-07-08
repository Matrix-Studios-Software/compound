package ltd.matrixstudios.compound.inventory

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
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

    fun saveInventory(inv: Inventory?, config: FileConfiguration, path: String)
    {
        if (inv == null)
        {
            config.set(path, null)
            return
        }

        for (i in 0 until inv.size)
        {
            if (inv.getItem(i) != null)
            {
                config.set("$path.$i", inv.getItem(i))
            } else
            {
                if (config.isItemStack("$path.$i"))
                {
                    config.set("$path.$i", null)
                }
            }
        }
    }

    fun saveItemArray(itemStacks: Array<ItemStack?>, config: FileConfiguration, path: String)
    {
        for (i in itemStacks.indices)
        {
            if (itemStacks[i] != null)
            {
                config["$path.$i"] = itemStacks[i]
            } else
            {
                if (config.isItemStack("$path.$i"))
                {
                    config["$path.$i"] = null
                }
            }
        }
    }

    fun toInventory(config: FileConfiguration, path: String): Inventory?
    {
        val inv = Bukkit.createInventory(null, 36)

        for (i in 0..35)
        {
            if (config.isItemStack("$path.$i"))
            {
                inv.setItem(i, config.getItemStack("$path.$i"))
            }
        }
        return inv
    }

    fun toItemArray(config: FileConfiguration, path: String): Array<ItemStack?>
    {
        val itemStacks = arrayOfNulls<ItemStack>(4)

        for (i in 0..26)
        {
            if (config.isItemStack("$path.$i"))
            {
                itemStacks[i] = config.getItemStack("$path.$i")
            }
        }
        return itemStacks
    }

}