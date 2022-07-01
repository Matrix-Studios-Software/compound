package ltd.matrixstudios.compound.staff.commands

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import org.bukkit.entity.Player

object BasicStaffCommands {

    fun register() {
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


    }
}