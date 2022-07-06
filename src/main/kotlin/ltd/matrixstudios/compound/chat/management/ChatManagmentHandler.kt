package ltd.matrixstudios.compound.chat.management

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import org.bukkit.Bukkit

object ChatManagmentHandler {

    var chatMuted: Boolean = false

    fun muteChat()
    {
        if (!chatMuted)
        {
            chatMuted = true
            Bukkit.broadcastMessage(Chat.format(CompoundPlugin.instance.config.getString("mutechat.muted")))
        } else if (chatMuted)
        {
            chatMuted = false
            Bukkit.broadcastMessage(Chat.format(CompoundPlugin.instance.config.getString("mutechat.unmuted")))
        }
    }

    fun registerCommands() {
        Command().create("mutechat")
            .aliases(arrayOf("mc"))
            .permission("compound.mutechat.admin")
            .execute().handle { args, sender, command ->
                muteChat()
                sender.sendMessage(Chat.format("&eChanged chat mute status"))
            }.bindToPlugin()

    }
}