package ltd.matrixstudios.compound.auth.listeners

import ltd.matrixstudios.compound.auth.AuthProfile
import ltd.matrixstudios.compound.auth.TwoFactorAuthService
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent

class AuthProfileListener : Listener {

    @EventHandler
    fun join(event: PlayerJoinEvent) {
        val player = event.player.uniqueId

        if (TwoFactorAuthService.byKey(player) == null)
        {
            val newProfile = AuthProfile(player, true, false, 0, 0L, false, false)

            TwoFactorAuthService.save(player, newProfile)
        }

        val profile = TwoFactorAuthService.byKey(player)

        if (TwoFactorAuthService.needs2fa(profile!!))
        {
            TwoFactorAuthService.goIntoLockMode(event.player)
        }

    }
}