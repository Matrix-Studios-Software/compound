package ltd.matrixstudios.compound.staff

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.function.Consumer
import java.util.stream.Collectors


class StaffUpdateVisibilityTask : BukkitRunnable() {
    override fun run() {
        val vanished = Bukkit.getOnlinePlayers().filter { player: Player ->
            player.hasMetadata(
                "vanish"
            )
        }
        for (online in Bukkit.getOnlinePlayers()) {
            if (online.hasPermission("compound.staff")) {
                continue
            }
            vanished.forEach(Consumer { player: Player? -> online.hidePlayer(player) })
        }
    }
}