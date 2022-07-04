package ltd.matrixstudios.compound.messages.commands

import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import ltd.matrixstudios.compound.commands.bukkit.BukkitCommandFunctions
import ltd.matrixstudios.compound.messages.MessageManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class BasicMessageCommands {

    companion object {
        fun registerAll() {
            Command().create("message")
                .aliases(arrayOf("msg", "m", "tell", "whisper"))
                .execute().handle { args, sender, command ->
                    if (args.isEmpty())
                    {
                        sender.sendMessage(Chat.format("&cUsage: /message <target> <message>"))
                        return@handle
                    }

                    val player = Bukkit.getPlayer(args[0])

                    if (player == null)
                    {
                        sender.sendMessage(Chat.format("&cThis target does not exist!"))
                        return@handle
                    }

                    val message = BukkitCommandFunctions.constructStringBuilder(args, 1)

                    if (message.isEmpty())
                    {
                        sender.sendMessage(Chat.format("&cYou must supply a message!"))
                        return@handle
                    }

                    MessageManager.message(sender, player, message.toString())
                }.bindToPlugin()

            Command().create("reply")
                .aliases(arrayOf("r"))
                .execute().handle { args, sender, command ->
                    if (args.isEmpty())
                    {
                        sender.sendMessage(Chat.format("&cUsage: /reply <message>"))
                        return@handle
                    }

                    if (sender !is Player)
                    {
                        sender.sendMessage(Chat.format("&cYou must be a player!"))
                        return@handle
                    }

                    val message = BukkitCommandFunctions.constructStringBuilder(args, 0)

                    if (message.isEmpty())
                    {
                        sender.sendMessage(Chat.format("&cYou must supply a message!"))
                        return@handle
                    }

                    if (!MessageManager.getPlayerLastRepliedTo(sender))
                    {
                        sender.sendMessage(Chat.format("&cYou have nobody to reply to!"))
                        return@handle
                    }

                    val player = Bukkit.getPlayer(MessageManager.lastMessaged[sender.uniqueId])

                    MessageManager.message(sender, player, message.toString())
                }.bindToPlugin()
        }
    }
}