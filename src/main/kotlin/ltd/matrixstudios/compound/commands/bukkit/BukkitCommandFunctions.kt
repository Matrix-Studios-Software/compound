package ltd.matrixstudios.compound.commands.bukkit

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.commands.Command
import org.bukkit.command.SimpleCommandMap
import org.bukkit.plugin.SimplePluginManager

object BukkitCommandFunctions {

    lateinit var commandMap: SimpleCommandMap

    var commands = hashMapOf<String, Command>()

    fun useCommandMap()
    {
        val map = SimplePluginManager::class.java.getDeclaredField("commandMap").apply { this.isAccessible = true }

        commandMap = map.get(CompoundPlugin.instance.server.pluginManager) as SimpleCommandMap
    }

    fun registerWithCommandMap(command: Command)
    {
        commandMap.register("", RawBukkitCommand(command))
    }

    fun register(command: Command)
    {
        commands[command.commandOption.name] = command

        registerWithCommandMap(command)
    }

    fun constructStringBuilder(args: Array<out String>, index: Int) : java.lang.StringBuilder
    {
        val builder = StringBuilder()

        for (i in index until args.size)
        {
            builder.append(args[i]).append(" ")
        }

        return builder
    }


}