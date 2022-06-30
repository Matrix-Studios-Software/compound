package ltd.matrixstudios.compound.auth

import java.util.*

data class AuthProfile(
    var uuid: UUID,
    var hasJoinedBefore: Boolean,
    var bypassAllowed: Boolean,
    var code: Int,
    var lastValidated: Long,
    var validated: Boolean,
    var setup: Boolean
)
