package ltd.matrixstudios.compound

import ltd.matrixstudios.compound.auth.AuthProfile
import ltd.matrixstudios.compound.auth.commands.AuthCommands
import ltd.matrixstudios.compound.auth.listeners.AuthProfileListener
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import ltd.matrixstudios.compound.commands.bukkit.BukkitCommandFunctions
import ltd.matrixstudios.duplex.DuplexMongoManager
import org.bukkit.plugin.java.JavaPlugin

class CompoundPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: CompoundPlugin
    }

    override fun onEnable() {
        saveDefaultConfig()
        instance = this

        loadDuplex()
        registerListeners()
        BukkitCommandFunctions.useCommandMap()

        Command().create("test").execute().handle { args, sender, command -> sender.sendMessage(Chat.format("&cTest")) }.bindToPlugin()
        AuthCommands.registerAll()
    }

    fun registerListeners() {
        server.pluginManager.registerEvents(AuthProfileListener(), this)
    }

    fun loadDuplex() {
        val config = this.config

        DuplexMongoManager.start(config.getString("mongo.uri"), config.getString("mongo.database"))
    }

}