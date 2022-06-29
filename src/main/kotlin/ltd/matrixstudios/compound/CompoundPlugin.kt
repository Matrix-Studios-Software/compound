package ltd.matrixstudios.compound

import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import ltd.matrixstudios.compound.commands.bukkit.BukkitCommandFunctions
import org.bukkit.plugin.java.JavaPlugin

class CompoundPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: CompoundPlugin
    }

    override fun onEnable() {
        instance = this

        BukkitCommandFunctions.useCommandMap()

        Command().create("test").execute().handle { args, sender, command -> sender.sendMessage(Chat.format("&cTest")) }.bindToPlugin()
    }
}