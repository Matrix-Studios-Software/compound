package ltd.matrixstudios.compound.staff

import ltd.matrixstudios.compound.items.ItemBuilder
import org.bukkit.Material

object StaffItems {

    val COMPASS = ItemBuilder.of(Material.COMPASS).name("&bCompass").build()
    val INVENTORY_INSPECT = ItemBuilder.of(Material.BOOK).name("&bInspect Inventory").build()
    val RANDOMTP = ItemBuilder.of(Material.BEACON).name("&bRandom TP").build()
    val BETTER_VIEW = ItemBuilder.of(Material.CARPET).data(7).name("&bBetter view").build()
    val ONLINE_STAFF = ItemBuilder.of(Material.SKULL_ITEM).name("&bOnline Staff").build()
    val VANISH = ItemBuilder.of(Material.INK_SACK).data(8).name("&bUnvanish").build()
    val UNVANISH = ItemBuilder.of(Material.INK_SACK).data(10).name("&bVanish").build()
    val REPORTS = ItemBuilder.of(Material.IRON_FENCE).name("&bReports").build()
    val FREEZE = ItemBuilder.of(Material.ICE).name("&bFreeze Player").build()
    val MINER_TP = ItemBuilder.of(Material.DIAMOND).name("&bMiner TP").build()

}