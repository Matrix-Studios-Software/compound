package ltd.matrixstudios.compound

import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import ltd.matrixstudios.compound.commands.bukkit.BukkitCommandFunctions
import ltd.matrixstudios.compound.staff.StaffSuiteManager
import ltd.matrixstudios.compound.staff.commands.BasicStaffCommands
import ltd.matrixstudios.compound.staff.listeners.GenericStaffmodePreventionListener
import ltd.matrixstudios.compound.staff.listeners.StaffmodeFunctionalityListener
import ltd.matrixstudios.framework.menu.library.listener.MenuListener
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
        staffManager = StaffSuiteManager()

        registerCommands()
        registerListeners()


    }

    fun registerListeners() {
        server.pluginManager.registerEvents(GenericStaffmodePreventionListener(), this)
        server.pluginManager.registerEvents(StaffmodeFunctionalityListener(), this)

        server.pluginManager.registerEvents(MenuListener(), this)
    }

    fun registerCommands() {
        Command().create("test").execute().handle { args, sender, command -> sender.sendMessage(Chat.format("&cTest")) }.bindToPlugin()
        BasicStaffCommands.register()
    }


}