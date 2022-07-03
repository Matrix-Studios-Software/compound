package ltd.matrixstudios.compound.staff.help.packets

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.packet.RedisPacket
import org.bukkit.Bukkit

class ReportPacket(
    val targetDisplayName: String,
    val reason: String,
    val reporterDisplayname: String
) : RedisPacket("compound-report") {

    override fun action()
    {
        val showingPlayers = Bukkit.getOnlinePlayers().filter { it.hasPermission("compound.staff") }

        showingPlayers.forEach {
            val message = CompoundPlugin.instance.config.getString("report.message")
                .replace("%target%", Chat.format(targetDisplayName))
                .replace("%reason%", reason)
                .replace("%reporter%", Chat.format(reporterDisplayname))

            it.sendMessage(Chat.format(message))
        }
    }
}