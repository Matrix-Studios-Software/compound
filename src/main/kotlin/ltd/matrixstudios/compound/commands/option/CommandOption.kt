package ltd.matrixstudios.compound.commands.option

import ltd.matrixstudios.compound.commands.Command
import ltd.matrixstudios.compound.commands.executor.CommandExecutor


class CommandOption(
    var name: String,
    var owningCommand: Command,
) {

    var aliases: Array<String>? = null
    var description: String? = null
    var permission: String? = null
    var requirePlayer: Boolean = false
    var requireOperator: Boolean = false
    var finalExecutor: CommandExecutor? = null

    fun aliases(aliases: Array<String>): CommandOption {
        return this.apply {
            this.aliases = aliases
        }
    }

    fun description(description: String): CommandOption {
        return this.apply {
            this.description = description
        }
    }

    fun permission(permission: String): CommandOption {
        return this.apply {
            this.permission = permission
        }
    }

    fun requirePlayer(): CommandOption { return this.apply { this.requirePlayer = true } }

    fun requireOp() : CommandOption { return this.apply { this.requireOperator = true } }

    fun execute() : CommandExecutor { return CommandExecutor(this).apply { finalExecutor = this } }

}