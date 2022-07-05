package ltd.matrixstudios.compound

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import ltd.matrixstudios.compound.commands.bukkit.BukkitCommandFunctions
import ltd.matrixstudios.compound.staff.StaffSuiteManager
import ltd.matrixstudios.compound.staff.commands.BasicStaffCommands
import ltd.matrixstudios.compound.staff.listeners.FrozenPlayerListener
import ltd.matrixstudios.compound.staff.listeners.GenericStaffmodePreventionListener
import ltd.matrixstudios.compound.staff.listeners.StaffmodeFunctionalityListener
import ltd.matrixstudios.compound.menu.listener.MenuListener
import ltd.matrixstudios.compound.messages.commands.BasicMessageCommands
import ltd.matrixstudios.compound.messages.exclusions.commands.MessagingExclusionCommands
import ltd.matrixstudios.compound.packet.RedisManager
import ltd.matrixstudios.compound.staff.help.HelpCommands
import org.bukkit.command.defaults.HelpCommand
import org.bukkit.plugin.java.JavaPlugin

class CompoundPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: CompoundPlugin
    }

    lateinit var staffManager: StaffSuiteManager

    override fun onEnable() {
        saveDefaultConfig()
        instance = this

        BukkitCommandFunctions.useCommandMap()
        loadRedis()
        staffManager = StaffSuiteManager()

        registerCommands()
        registerListeners()


    }

    fun loadRedis() {
        RedisManager.load(config.getString("redis.uri"))
    }

    fun registerListeners() {
        server.pluginManager.registerEvents(GenericStaffmodePreventionListener(), this)
        server.pluginManager.registerEvents(StaffmodeFunctionalityListener(), this)
        server.pluginManager.registerEvents(FrozenPlayerListener(), this)

        server.pluginManager.registerEvents(MenuListener(), this)
    }

    fun registerCommands() {
        Command().create("test").execute().handle { args, sender, command -> sender.sendMessage(Chat.format("&cTest")) }.bindToPlugin()

        //companion objects because I dont want a ton of object classes
        BasicStaffCommands.registerAll()
        HelpCommands.registerAll()
        BasicMessageCommands.registerAll()
        MessagingExclusionCommands.registerAll()
    }


}