package ltd.matrixstudios.compound.player.gamemode

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player

class GamemodeSurvivalCommand {

    companion object {
        fun registerCommand() {
            Command().create("gms")
                .permission("compound.gamemode")
                .execute().handle { args, sender, command ->
                    if (sender !is Player)
                    {
                        sender.sendMessage(Chat.format("&cYou must be a player!"))
                        return@handle
                    }


                    sender.gameMode = GameMode.SURVIVAL
                    sender.sendMessage(Chat.format(CompoundPlugin.instance.config.getString("gamemode.survival")))


                }.bindToPlugin()
        }
    }
}