package ltd.matrixstudios.compound

import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.chat.management.ChatManagmentHandler
import ltd.matrixstudios.compound.chat.management.MuteChatListener
import ltd.matrixstudios.compound.chat.management.SlowChatListener
import ltd.matrixstudios.compound.commands.Command
import ltd.matrixstudios.compound.commands.bukkit.BukkitCommandFunctions
import ltd.matrixstudios.compound.kits.KitManager
import ltd.matrixstudios.compound.kits.command.KitCommands
import ltd.matrixstudios.compound.lunar.LunarWaypointManager
import ltd.matrixstudios.compound.staff.StaffSuiteManager
import ltd.matrixstudios.compound.staff.commands.BasicStaffCommands
import ltd.matrixstudios.compound.staff.listeners.FrozenPlayerListener
import ltd.matrixstudios.compound.staff.listeners.GenericStaffmodePreventionListener
import ltd.matrixstudios.compound.staff.listeners.StaffmodeFunctionalityListener
import ltd.matrixstudios.compound.menu.listener.MenuListener
import ltd.matrixstudios.compound.messages.commands.BasicMessageCommands
import ltd.matrixstudios.compound.messages.exclusions.commands.MessagingExclusionCommands
import ltd.matrixstudios.compound.misc.polls.PollManager
import ltd.matrixstudios.compound.packet.RedisManager
import ltd.matrixstudios.compound.player.gamemode.GamemodeCreativeCommand
import ltd.matrixstudios.compound.player.gamemode.GamemodeSurvivalCommand
import ltd.matrixstudios.compound.player.gamemode.GeneralGamemodeCommand
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
        LunarWaypointManager.loadWaypoints()
        KitManager.loadKits()

        registerCommands()
        registerListeners()
    }

    fun loadRedis()
    {
        RedisManager.load(config.getString("redis.uri"))
    }

    fun registerListeners()
    {
        server.pluginManager.registerEvents(GenericStaffmodePreventionListener(), this)
        server.pluginManager.registerEvents(StaffmodeFunctionalityListener(), this)
        server.pluginManager.registerEvents(FrozenPlayerListener(), this)

        server.pluginManager.registerEvents(MenuListener(), this)

        server.pluginManager.registerEvents(SlowChatListener(), this)
        server.pluginManager.registerEvents(MuteChatListener(), this)

    }

    fun registerCommands()
    {
        val config = this.config
        if (config.getBoolean("modules.staffSuite"))
        {
            BasicStaffCommands.registerAll()
        }

        if (config.getBoolean("modules.reportsAndRequests"))
        {
            HelpCommands.registerAll()
        }

        if (config.getBoolean("modules.messages"))
        {
            BasicMessageCommands.registerAll()
            MessagingExclusionCommands.registerAll()
        }

        if (config.getBoolean("modules.polls"))
        {
            PollManager.registerCommands()
        }

        if (config.getBoolean("modules.chatManagement"))
        {
            ChatManagmentHandler.registerCommands()
        }

        if (config.getBoolean("modules.gamemode"))
        {
            GamemodeCreativeCommand.registerCommand()
            GamemodeSurvivalCommand.registerCommand()
            GeneralGamemodeCommand.registerCommand()
        }

        if (config.getBoolean("modules.kits"))
        {
            KitCommands.registerCommands()
        }

    }


}