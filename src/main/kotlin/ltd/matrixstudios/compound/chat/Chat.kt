package ltd.matrixstudios.compound.chat

import org.bukkit.ChatColor

object Chat {

    fun format(message: String) : String {
        return ChatColor.translateAlternateColorCodes('&', message)
    }
}