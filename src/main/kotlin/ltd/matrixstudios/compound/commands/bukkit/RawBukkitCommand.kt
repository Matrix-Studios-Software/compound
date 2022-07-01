package ltd.matrixstudios.compound.commands.bukkit

import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RawBukkitCommand(val command: Command) : org.bukkit.command.Command(command.commandOption.name) {

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        val compoundCommand = command

        if (compoundCommand.commandOption.requirePlayer)
        {
            if (sender !is Player)
            {
                sender.sendMessage(Chat.format("&cThis command must be from a player!"))
                return false
            }
        }

        if (compoundCommand.commandOption.permission != null)
        {
            if (!sender.hasPermission(compoundCommand.commandOption.permission))
            {
                sender.sendMessage(Chat.format("&cNo permission."))

                return false
            }
        }

        if (compoundCommand.commandOption.requireOperator)
        {
            if (!sender.isOp)
            {
                sender.sendMessage(Chat.format("&cThis is an operator only command!"))

                return false
            }
        }


        val executor = compoundCommand.commandOption.finalExecutor!!

        val unit = executor.unit

        unit.invoke(
            args,
            sender,
            commandLabel
        )

        return true
    }

    init {
        val commandAliases = command.commandOption.aliases

        if (commandAliases != null)
        {
            aliases = commandAliases.toCollection(mutableListOf())
        }
    }
}