package ltd.matrixstudios.compound.chat.management

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object ChatManagmentHandler {

    var chatMuted: Boolean = false

    fun muteChat(player: Player)
    {
        if (!chatMuted)
        {
            chatMuted = true
            Bukkit.broadcastMessage(Chat.format(CompoundPlugin.instance.config.getString("mutechat.muted").replace("%displayname%", player.displayName)))
        } else if (chatMuted)
        {
            chatMuted = false
            Bukkit.broadcastMessage(Chat.format(CompoundPlugin.instance.config.getString("mutechat.unmuted").replace("%displayname%", player.displayName)))
        }
    }

    fun registerCommands() {
        Command().create("mutechat")
            .aliases(arrayOf("mc"))
            .requirePlayer()
            .permission("compound.mutechat.admin")
            .execute().handle { args, sender, command ->
                muteChat(sender as Player)
                sender.sendMessage(Chat.format("&eChanged chat mute status"))
            }.bindToPlugin()

    }
}