package ltd.matrixstudios.compound.player

import ltd.matrixstudios.compound.inventory.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

class PlayerHeadUtility(val owner: String) {

    private val material = Material.SKULL_ITEM
    private val data = 3
    private val amount = 1

    var displayName: String? = null
    var lore: MutableList<String> = mutableListOf()

    fun useDisplayName(name: String) : PlayerHeadUtility
    {
        return this.apply { this.displayName = name }
    }

    fun withLore(lore: MutableList<String>) : PlayerHeadUtility
    {
        return this.apply { this.lore = lore }
    }

    fun toItemStack() : ItemStack {
        val finalItem = ItemStack(material)

        finalItem.durability = data.toShort()
        finalItem.amount = amount


        val itemMeta = finalItem.itemMeta as SkullMeta

        if (displayName != null)
        {
            itemMeta.displayName = displayName
        }

        itemMeta.lore = lore
        itemMeta.owner = owner

        finalItem.itemMeta = itemMeta

        return finalItem
    }
}