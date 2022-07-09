package ltd.matrixstudios.compound.kits

import ltd.matrixstudios.compound.utility.time.TimeUtil
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

data class Kit(
    val id: String,
    var displayName: String,
    var cooldown: String,
    var armor: Array<ItemStack?>,
    var contents: Inventory?,
    var displayItem: Material,
    var displayData: Short,
    var description: MutableList<String>,
    var permission: String,
    var slot: Int
) {

    fun getTimeInLong() : Long
    {
        return TimeUtil.parseTime(cooldown)
    }

}