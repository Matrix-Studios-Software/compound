package ltd.matrixstudios.compound.player.items

import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import org.bukkit.entity.Player

class ItemEditCommands {

    companion object
    {
        fun register()
        {
            Command().create("rename")
                .requirePlayer()
                .permission("compound.rename")
                .execute().handle { args, sender, command ->

                    val player = sender as Player

                    if (args.isEmpty()) {
                        player.sendMessage(Chat.format("&cUsage: /rename <name>"))
                    }

                    if (player.itemInHand == null) {
                        player.sendMessage(Chat.format("&cYou are not holding an item!"))
                        return@handle
                    }

                    val name = args[0]

                    if (!player.hasPermission("compound.rename.colored"))
                    {
                        player.itemInHand.itemMeta.displayName = name
                    } else if (player.hasPermission("compound.rename.colored"))
                    {
                        player.itemInHand.itemMeta.displayName = Chat.format(name)
                    }

                    player.sendMessage(Chat.format("&aYou have renamed your item!"))
                }
        }
    }
}