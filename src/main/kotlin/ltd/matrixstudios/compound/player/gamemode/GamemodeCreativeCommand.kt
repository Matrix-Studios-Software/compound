package ltd.matrixstudios.compound.player.gamemode

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player

class GamemodeCreativeCommand {

    companion object {
        fun registerCommand() {
            Command().create("gmc")
                .permission("compound.gamemode")
                .execute().handle { args, sender, command ->
                    if (sender !is Player)
                    {
                        sender.sendMessage(Chat.format("&cYou must be a player!"))
                        return@handle
                    }

                    sender.gameMode = GameMode.CREATIVE
                    sender.sendMessage(Chat.format(CompoundPlugin.instance.config.getString("gamemode.creative")))

                }.bindToPlugin()
        }
    }

}