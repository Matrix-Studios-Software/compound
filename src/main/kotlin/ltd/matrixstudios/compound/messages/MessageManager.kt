package ltd.matrixstudios.compound.messages

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.messages.exclusions.MessageExclusionManager
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

object MessageManager {

    val lastMessaged = hashMapOf<UUID, UUID>()

    fun getPlayerLastRepliedTo(player: Player): Boolean {
        return lastMessaged.containsKey(player.uniqueId)
    }

    fun message(sender: CommandSender, target: Player, message: String) {
        val toMessage = CompoundPlugin.instance.config.getString("messages.to")
        val fromMessage = CompoundPlugin.instance.config.getString("messages.from")
        if (sender !is Player) {
            target.sendMessage(
                Chat.format(
                    fromMessage.replace("%displayname%", "&4&lConsole").replace("%message%", message)
                )
            )
            sender.sendMessage(
                Chat.format(
                    toMessage.replace("%displayname%", "&4&lConsole").replace("%message%", message)
                )
            )

        }

        if (sender is Player) {

            if (MessageExclusionManager.isExcluded(target, sender)) {
                sender.sendMessage(Chat.format("&cThis player has you ignored!"))
                return
            }

            target.sendMessage(
                Chat.format(
                    fromMessage.replace("%displayname%", sender.displayName).replace("%message%", message)
                )
            )
            sender.sendMessage(
                Chat.format(
                    toMessage.replace("%displayname%", target.displayName).replace("%message%", message)
                )
            )
            lastMessaged[target.uniqueId] = sender.uniqueId
        }
    }

}