package ltd.matrixstudios.compound.commands

import ltd.matrixstudios.compound.commands.option.CommandOption

class Command {

    lateinit var commandOption: CommandOption

    fun create(name: String) : CommandOption
    {
        return CommandOption(name,
            this
        ).apply {
            commandOption = this
        }
    }
}