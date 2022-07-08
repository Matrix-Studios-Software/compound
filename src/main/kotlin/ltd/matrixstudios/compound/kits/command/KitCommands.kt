package ltd.matrixstudios.compound.kits.command

import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import ltd.matrixstudios.compound.kits.Kit
import ltd.matrixstudios.compound.kits.KitManager
import ltd.matrixstudios.compound.utility.time.TimeUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

class KitCommands {

    companion object {
        fun registerCommand()
        {
            Command().create("kit")
                .requirePlayer()
                .execute().handle { args, sender, command ->
                    if (!sender.hasPermission("compound.kits.details"))
                    {

                        return@handle
                    }

                    if (sender.hasPermission("compound.kits.details") && args.isEmpty())
                    {
                        sender.sendMessage(Chat.format("&b=== &fDetailed Kit Help &b==="))
                        sender.sendMessage(Chat.format("&f/kit create <kit> - &bCreates a kit"))
                        sender.sendMessage(Chat.format("&f/kit delete <kit> - &bDeletes a kit"))
                        sender.sendMessage(Chat.format("&f/kit edit <kit> - &bOpens the kit edit menu"))
                        sender.sendMessage(Chat.format("&f/kit menu - &bOpens the default menu"))
                        sender.sendMessage(Chat.format("&b=== &fShowing &b4 &fresults. &b==="))

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

                            val kit = Kit(name, name, "1d", player.inventory.armorContents, player.inventory, Material.DIRT)

                            KitManager.save(kit)
                            sender.sendMessage(Chat.format("&aCreated a kit with the name &f$name"))
                        }
                    }
                }.bindToPlugin()
        }
    }
}