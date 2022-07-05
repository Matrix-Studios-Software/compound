package ltd.matrixstudios.compound.misc.polls

import java.util.*
import kotlin.collections.HashMap

data class Poll(
    val id: UUID,
    val whatFor: String,
    val optionOne: String,
    val optionTwo: String,
    val totalVotes: HashMap<UUID, String>
)
