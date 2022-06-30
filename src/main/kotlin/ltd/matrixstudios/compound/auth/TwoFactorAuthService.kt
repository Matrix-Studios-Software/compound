package ltd.matrixstudios.compound.auth

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.duplex.repository.DuplexMongoRepository
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue
import java.util.*
import java.util.concurrent.TimeUnit

object TwoFactorAuthService : DuplexMongoRepository<UUID, AuthProfile>("authprofiles", AuthProfile::class.java) {

    fun validate(uuid: UUID, authProfile: AuthProfile)
    {
        authProfile.validated = true
        authProfile.lastValidated = System.currentTimeMillis()

        save(uuid, authProfile)
    }

    fun goIntoLockMode(player: Player) {
        player.setMetadata("locked", FixedMetadataValue(CompoundPlugin.instance, true))
    }

    fun needs2fa(authProfile: AuthProfile) : Boolean
    {
        if (!authProfile.hasJoinedBefore) {
            return true
        }

        if (!authProfile.setup) {
            return true
        }

        if (!authProfile.bypassAllowed) {
            val difference = System.currentTimeMillis().minus(authProfile.lastValidated)

            if (difference > TimeUnit.HOURS.toMillis(2L))
            {
                return true
            }
        }

        return false
    }

}