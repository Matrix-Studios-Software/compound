package ltd.matrixstudios.compound.kits

import ltd.matrixstudios.compound.utility.time.TimeUtil
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

data class Kit(
    val id: String,
    val displayName: String,
    val cooldown: String,
    val armor: Array<ItemStack?>,
    val contents: Inventory?,
    val displayItem: Material
) {

    fun getTimeInLong() : Long
    {
        return TimeUtil.parseTime(cooldown)
    }

}