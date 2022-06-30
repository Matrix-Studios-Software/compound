package ltd.matrixstudios.compound.auth.commands

import ltd.matrixstudios.compound.auth.TwoFactorAuthService
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import org.bukkit.entity.Player

object AuthCommands {

    fun registerAll() {
        Command().create("auth")
            .requirePlayer()
            .permission("compound.staff")
            .execute()
            .handle { args, sender, command ->
                if (args.isEmpty()) {
                    sender.sendMessage(Chat.format("&cUsage: /auth <code>"))
                    return@handle
                }

                val player = sender as Player

                val authProfile = TwoFactorAuthService.byKey(player.uniqueId)

                if (authProfile == null) {
                    player.sendMessage(Chat.format("&cYou do not have a auth profile!"))
                    return@handle
                }

                val code = Integer.parseInt(args[0])

                if (code == null || code.toDouble().isNaN()) {
                    player.sendMessage(Chat.format("&cNot a number!"))
                    return@handle
                }

                if (code != authProfile.code) {
                    player.sendMessage(Chat.format("&cThis is not your code!"))
                    return@handle
                }

                if (code == authProfile.code) {
                    player.sendMessage(Chat.format("&aYou have been validated!"))

                    TwoFactorAuthService.validate(player.uniqueId, authProfile)
                }


            }.bindToPlugin()
    }

}