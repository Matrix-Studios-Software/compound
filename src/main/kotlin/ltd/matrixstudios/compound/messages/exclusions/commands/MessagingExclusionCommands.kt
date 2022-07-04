package ltd.matrixstudios.compound.messages.exclusions.commands

import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import ltd.matrixstudios.compound.messages.exclusions.MessageExclusionManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class MessagingExclusionCommands {

    companion object {
        fun registerAll() {
            Command().create("ignore")
                .execute().handle { args, sender, command ->
                    if (args.isEmpty())
                    {
                        sender.sendMessage(Chat.format("&cUsage: /ignore <target>"))
                        return@handle
                    }

                    if (sender !is Player)
                    {
                        sender.sendMessage(Chat.format("&cYou must be a player!"))
                        return@handle
                    }

                    val target = Bukkit.getPlayer(args[0])

                    if (target == null)
                    {
                        sender.sendMessage(Chat.format("&cThis target does not exist!"))
                        return@handle
                    }

                    if (MessageExclusionManager.isExcluded(sender, target))
                    {
                        sender.sendMessage(Chat.format("&cThis player is already ignored"))
                        return@handle
                    }

                    MessageExclusionManager.ignorePlayer(sender, target)
                    sender.sendMessage(Chat.format("&aIgnored ${target.displayName}"))
                }.bindToPlugin()

            Command().create("unignore")
                .execute().handle { args, sender, command ->
                    if (args.isEmpty())
                    {
                        sender.sendMessage(Chat.format("&cUsage: /unignore <target>"))
                        return@handle
                    }

                    if (sender !is Player)
                    {
                        sender.sendMessage(Chat.format("&cYou must be a player!"))
                        return@handle
                    }

                    val target = Bukkit.getPlayer(args[0])

                    if (target == null)
                    {
                        sender.sendMessage(Chat.format("&cThis target does not exist!"))
                        return@handle
                    }

                    if (!MessageExclusionManager.isExcluded(sender, target))
                    {
                        sender.sendMessage(Chat.format("&cThis player is not ignored"))
                        return@handle
                    }

                    MessageExclusionManager.unignore(sender, target)
                    sender.sendMessage(Chat.format("&aUnignored ${target.displayName}"))
                }.bindToPlugin()
        }
    }
}