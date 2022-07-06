package ltd.matrixstudios.compound.chat.management

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.cooldown.Cooldowns
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

class SlowChatListener : Listener {

    @EventHandler
    fun slowChat(e: AsyncPlayerChatEvent)
    {
        val player = e.player

        if (!player.hasPermission(CompoundPlugin.instance.config.getString("slowchat.bypass")))
        {
            if (Cooldowns.isOnCooldown(player.uniqueId, "chat"))
            {
                player.sendMessage(
                    Chat.format(CompoundPlugin.instance.config.getString("slowchat.message")
                        .replace("%seconds%", getSeconds(
                            Cooldowns.cooldownTable.get("chat", player.uniqueId) - System.currentTimeMillis()
                        ).toDouble().roundToInt().toString())))
                e.isCancelled = true
            } else if (!Cooldowns.isOnCooldown(player.uniqueId, "chat"))
            {
                Cooldowns.addCooldown("chat", player.uniqueId, TimeUnit.SECONDS.toMillis(CompoundPlugin.instance.config.getLong("slowchat.seconds")))
            }
        }
    }

    fun getSeconds(long: Long) : Long {
        return long / 1000
    }
}