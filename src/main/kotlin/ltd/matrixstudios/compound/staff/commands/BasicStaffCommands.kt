package ltd.matrixstudios.compound.staff.commands

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue

class BasicStaffCommands {

    companion object {
        fun registerAll() {
            Command().create("staff")
                .aliases(arrayOf("h", "hacker", "staffmode", "mod", "modmode"))
                .permission("compound.staffmode")
                .execute().handle { args, sender, command ->
                    if (sender !is Player)
                    {
                        sender.sendMessage(Chat.format("&cYou must be a player"))
                        return@handle
                    }

                    val player = sender

                    if (!CompoundPlugin.instance.staffManager.isModMode(player))
                    {
                        CompoundPlugin.instance.staffManager.setStaffMode(player)
                        player.sendMessage(Chat.format("&aYou have toggled StaffMode!"))
                    } else {
                        CompoundPlugin.instance.staffManager.removeStaffMode(player)
                        player.sendMessage(Chat.format("&cYou have untoggled your StaffMode!"))
                    }
                }.bindToPlugin()


            Command().create("freeze")
                .permission("compound.freeze")
                .execute().handle { args, sender, command ->
                    if (args.isEmpty())
                    {
                        sender.sendMessage(Chat.format("&cUsage: /freeze <target>"))
                        return@handle
                    }

                    val target = Bukkit.getPlayer(args[0])

                    if (target == null)
                    {
                        sender.sendMessage(Chat.format("&cThis target does not exist"))
                        return@handle
                    }

                    val player = sender

                    if (!target.hasMetadata("frozen"))
                    {
                        target.setMetadata("frozen", FixedMetadataValue(CompoundPlugin.instance, true))
                        target.sendMessage(Chat.format("&c&lYou have been frozen!"))

                        player.sendMessage(Chat.format("&6You froze &f${target.displayName}"))
                    } else {
                        target.removeMetadata("frozen", CompoundPlugin.instance)
                        target.sendMessage(Chat.format("&a&lYou have been unfrozen!"))

                        player.sendMessage(Chat.format("&6You unfroze &f${target.displayName}"))
                    }

                }.bindToPlugin()
        }
    }

}