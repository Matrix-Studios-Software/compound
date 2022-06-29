package ltd.matrixstudios.compound.commands.executor

import ltd.matrixstudios.compound.commands.bukkit.BukkitCommandFunctions
import ltd.matrixstudios.compound.commands.option.CommandOption
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin


class CommandExecutor(
    val commandOption: CommandOption
) {

    lateinit var unit: (Array<out String>, CommandSender, String) -> Unit

    fun handle(body: (Array<out String>, CommandSender, String) -> Unit) : CommandExecutor
    {
        return this.apply {
            unit = body
        }
    }

    fun bindToPlugin() : CommandExecutor {
        return this.apply {
            BukkitCommandFunctions.register(commandOption.owningCommand)
        }
    }

}