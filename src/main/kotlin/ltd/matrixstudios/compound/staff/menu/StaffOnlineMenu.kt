package ltd.matrixstudios.compound.staff.menu

import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.menu.Button
import ltd.matrixstudios.compound.menu.pagination.PaginatedMenu
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class StaffOnlineMenu(val player: Player) : PaginatedMenu(18, player) {
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()
        var index = 0

        for (player in Bukkit.getOnlinePlayers().filter { it.hasPermission("compound.staff") })
        {
            buttons[index++] = StaffOnlineButton(player)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "All Online Staff"
    }


    class StaffOnlineButton(val target: Player) : Button() {
        override fun getMaterial(player: Player): Material {
            return Material.SKULL_ITEM
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = arrayListOf<String>()
            desc.add(Chat.format("&7&m---------------"))
            desc.add(Chat.format("&eModMode: &r" + if (target.hasMetadata("modmode")) "&aTrue" else "&cFalse"))
            desc.add(Chat.format("&eVanished: &r" + if (target.hasMetadata("vanish")) "&aTrue" else "&cFalse"))
            desc.add(Chat.format("&7&m---------------"))

            return desc
        }

        override fun getDisplayName(player: Player): String? {
           return target.displayName
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {}

    }
}