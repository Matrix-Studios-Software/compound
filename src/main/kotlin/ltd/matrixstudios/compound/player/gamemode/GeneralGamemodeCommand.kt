package ltd.matrixstudios.compound.player.gamemode

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player

class GeneralGamemodeCommand {

    companion object {
        fun registerCommand() {
            Command().create("gamemode")
                .permission("compound.gamemode")
                .execute().handle { args, sender, command ->
                    if (sender !is Player)
                    {
                        sender.sendMessage(Chat.format("&cYou must be a player!"))
                        return@handle
                    }

                    if (args.isEmpty())
                    {
                        sender.sendMessage(Chat.format("&cUsage: /gamemode <c/s>"))
                        return@handle
                    }

                    if (args[0] == "c" || args[0] == "creative")
                    {
                        sender.gameMode = GameMode.CREATIVE
                        sender.sendMessage(Chat.format(CompoundPlugin.instance.config.getString("gamemode.creative")))
                    } else if (args[0] == "s" || args[0] == "survival")
                    {
                        sender.gameMode = GameMode.SURVIVAL
                        sender.sendMessage(Chat.format(CompoundPlugin.instance.config.getString("gamemode.survival")))

                    } else if (args[0] != "s" && args[0] != "survival" && args[0] != "c" && args[0] != "creative")
                    {
                        sender.sendMessage(Chat.format("&cInvalid gamemode!"))
                        return@handle
                    }


                }.bindToPlugin()
        }
    }
}