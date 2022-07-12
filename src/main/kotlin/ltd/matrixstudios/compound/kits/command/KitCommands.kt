package ltd.matrixstudios.compound.kits.command

import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.colors.PluginColorization
import ltd.matrixstudios.compound.commands.Command
import ltd.matrixstudios.compound.commands.bukkit.BukkitCommandFunctions
import ltd.matrixstudios.compound.kits.Kit
import ltd.matrixstudios.compound.kits.KitManager
import ltd.matrixstudios.compound.kits.cooldown.KitCooldownService
import ltd.matrixstudios.compound.kits.menu.KitsMenu
import ltd.matrixstudios.compound.utility.time.TimeUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

class KitCommands {

    companion object {
        fun registerCommands()
        {
            Command().create("kit")
                .requirePlayer()
                .execute().handle { args, sender, command ->
                    if (args.isEmpty())
                    {
                        KitsMenu(sender as Player).openMenu()
                    } else if (args.size == 1)
                    {
                        val kit = args[0]

                        val actualKit = KitManager.getKit(kit)

                        if (actualKit == null)
                        {
                            sender.sendMessage(Chat.format("&cKit does not exist!"))
                            return@handle
                        }

                        if (actualKit.permission != "" && !sender.hasPermission(actualKit.permission))
                        {
                            sender.sendMessage(Chat.format("&cYou do not have access to this kit!"))
                            return@handle
                        }

                        if (actualKit.permission == "" || sender.hasPermission(actualKit.permission))
                        {
                            if (KitCooldownService.isOnCoodown(actualKit, (sender as Player).uniqueId))
                            {
                                sender.sendMessage(Chat.format("&cYou are currently on kit cooldown!"))
                                return@handle
                            }

                            KitManager.giveToPlayer(actualKit, sender)

                            sender.sendMessage(Chat.format("&aApplied the &f$kit &akit!"))
                        }
                    }

                }.bindToPlugin()

            Command().create("kitadmin")
                .requirePlayer()
                .permission("compound.kits.details")
                .execute().handle { args, sender, command ->
                    if (args.isEmpty())
                    {
                        sender.sendMessage(Chat.format("${PluginColorization.PRIMARY_COLOR}=== ${PluginColorization.SECONDARY_COLOR}Detailed Kit Help ${PluginColorization.PRIMARY_COLOR}==="))

                        sender.sendMessage(Chat.format("${PluginColorization.SECONDARY_COLOR}/kit create <kit> - ${PluginColorization.PRIMARY_COLOR}Creates a kit"))
                        sender.sendMessage(Chat.format("${PluginColorization.SECONDARY_COLOR}/kit delete <kit> - ${PluginColorization.PRIMARY_COLOR}Deletes a kit"))
                        sender.sendMessage(Chat.format("${PluginColorization.SECONDARY_COLOR}/kit setitem <kit> <material> - ${PluginColorization.PRIMARY_COLOR}Sets the display item of a kit"))
                        sender.sendMessage(Chat.format("${PluginColorization.SECONDARY_COLOR}/kit menu - ${PluginColorization.PRIMARY_COLOR}Opens the default menu"))
                        sender.sendMessage(Chat.format("${PluginColorization.SECONDARY_COLOR}/kit setdisplayname <kit> <name> - ${PluginColorization.PRIMARY_COLOR}Sets the display name of a kit"))
                        sender.sendMessage(Chat.format("${PluginColorization.PRIMARY_COLOR}=== ${PluginColorization.SECONDARY_COLOR}Showing ${PluginColorization.PRIMARY_COLOR}5 ${PluginColorization.SECONDARY_COLOR}results. ${PluginColorization.PRIMARY_COLOR}==="))

                        return@handle
                    }

                    val extraParam = args[0]

                    when (extraParam.toLowerCase()) {
                        "create" -> {
                            if (args.size != 2)
                            {
                                sender.sendMessage(Chat.format("&cUsage: /kit create <name>"))
                                return@handle
                            }

                            val name = args[1]

                            if (KitManager.getKit(name) != null)
                            {
                                sender.sendMessage(Chat.format("&cThis kit already exists"))
                                return@handle
                            }

                            val player = sender as Player

                            val kit = Kit(name.toLowerCase(), name, "1d", player.inventory.armorContents, player.inventory, Material.DIRT, 0, mutableListOf(), "", 0)

                            KitManager.save(kit)
                            sender.sendMessage(Chat.format("&aCreated a kit with the name &f$name"))
                        }

                        "setitem" -> {
                            if (args.size != 3)
                            {
                                sender.sendMessage(Chat.format("&cUsage: /kit setitem <item>"))
                                return@handle
                            }

                            if (KitManager.getKit(args[1]) == null)
                            {
                                sender.sendMessage(Chat.format("&cThis kit doesnt exists"))
                                return@handle
                            }

                            val item = args[2].toUpperCase()

                            val material = Material.getMaterial(item)

                            if (material == null)
                            {
                                sender.sendMessage(Chat.format("&cInvalid material"))
                                return@handle
                            }

                            val kit = KitManager.getKit(args[1])

                            kit!!.displayItem = material
                            KitManager.save(kit)
                            sender.sendMessage(Chat.format("&aSet the display item of the kit &f${args[1]}"))
                        }

                        "setdisplayname" -> {
                            if (args.size < 3)
                            {
                                sender.sendMessage(Chat.format("&cUsage: /kit setdisplayname <kit> <name>"))
                                return@handle
                            }

                            if (KitManager.getKit(args[1]) == null)
                            {
                                sender.sendMessage(Chat.format("&cThis kit doesnt exists"))
                                return@handle
                            }

                            val name = BukkitCommandFunctions.constructStringBuilder(args, 2)

                            val kit = KitManager.getKit(args[1])

                            kit!!.displayName = name.toString()
                            KitManager.save(kit)
                            sender.sendMessage(Chat.format("&aSet the display name of the kit &f${args[1]}"))
                        } else -> {
                            sender.sendMessage(Chat.format("&cNot a param option."))
                        }


                    }
                }.bindToPlugin()
        }
    }
}