package ltd.matrixstudios.compound.staff

import ltd.matrixstudios.compound.visibility.VisibilityHandler
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object StaffSuiteVisibilityHandler : VisibilityHandler() {

    override fun onDisableVisbility(player: Player) {
        Bukkit.getOnlinePlayers().forEach {
            it.showPlayer(player)
        }

    }

    override fun onEnableVisibility(player: Player) {
        Bukkit.getOnlinePlayers().filter { !it.hasPermission("compound.staff") }.forEach { it.hidePlayer(player) }

        Bukkit.getOnlinePlayers().filter {
            it.hasPermission("compound.staff")
        }.forEach {
            player.showPlayer(it)
        }
    }
}