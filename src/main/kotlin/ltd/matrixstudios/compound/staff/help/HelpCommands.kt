package ltd.matrixstudios.compound.staff.help

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import ltd.matrixstudios.compound.commands.bukkit.BukkitCommandFunctions
import ltd.matrixstudios.compound.cooldown.Cooldowns
import ltd.matrixstudios.compound.packet.RedisManager
import ltd.matrixstudios.compound.staff.help.packets.ReportPacket
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

class HelpCommands {

    companion object {
        val reports = arrayListOf<ReportModel>()

        fun registerAll() {
            Command().create("report")
                .requirePlayer()
                .execute().handle { args, sender, command ->

                    if (sender !is Player) {
                        sender.sendMessage(Chat.format("&cThis command is player only"))
                        return@handle
                    }

                    if (args.isEmpty()) {
                        sender.sendMessage(Chat.format("&cUsage: /report <target> <reason>"))
                        return@handle
                    }

                    val target = Bukkit.getPlayer(args[0])

                    if (target == null) {
                        sender.sendMessage(Chat.format("&cThis target does not exist"))
                        return@handle
                    }

                    if (Cooldowns.isOnCooldown(sender.uniqueId, "report")) {
                        sender.sendMessage(Chat.format("&cYou are currently on cooldown!"))
                        return@handle
                    }

                    val finalArgs = BukkitCommandFunctions.constructStringBuilder(args, 1)

                    if (finalArgs.isEmpty()) {
                        sender.sendMessage(Chat.format("&cYou must supply a reason!"))
                        return@handle
                    }

                    if (CompoundPlugin.instance.config.getBoolean("modules.useRedis"))
                    {
                        RedisManager.send(ReportPacket(target.displayName, finalArgs.toString(), sender.displayName))
                    } else {
                        Bukkit.getOnlinePlayers().filter { it.hasPermission("compound.staff") }.forEach {
                            val message = CompoundPlugin.instance.config.getString("report.message")
                                .replace("%target%", Chat.format(target.displayName))
                                .replace("%reason%", finalArgs.toString())
                                .replace("%reporter%", Chat.format(sender.displayName))

                            it.sendMessage(Chat.format(message))
                        }
                    }

                    Cooldowns.addCooldown("report", sender.uniqueId, TimeUnit.MINUTES.toMillis(1))

                    sender.sendMessage(Chat.format("&aWe have received your report!"))

                    reports.add(
                        ReportModel(
                            sender.uniqueId,
                            target.uniqueId,
                            System.currentTimeMillis(),
                            finalArgs.toString()
                        )
                    )
                }.bindToPlugin()
        }
    }
}