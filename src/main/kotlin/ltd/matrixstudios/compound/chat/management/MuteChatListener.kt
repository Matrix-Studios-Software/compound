package ltd.matrixstudios.compound.chat.management

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class MuteChatListener : Listener {

    @EventHandler
    fun muteChat(e: AsyncPlayerChatEvent)
    {
        val player = e.player

        if (ChatManagmentHandler.chatMuted)
        {
            if (!player.hasPermission(CompoundPlugin.instance.config.getString("mutechat.bypass")))
            {
                e.isCancelled = true
                player.sendMessage(Chat.format("&cChat is currently muted!"))
            }
        }

    }
}