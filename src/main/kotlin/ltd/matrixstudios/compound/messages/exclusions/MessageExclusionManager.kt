package ltd.matrixstudios.compound.messages.exclusions

import ltd.matrixstudios.compound.packet.RedisManager
import org.bukkit.entity.Player
import java.util.*

object MessageExclusionManager {

    fun getExcludedPlayers(uuid: UUID) : MutableList<UUID>
    {
        val ignoredList = mutableListOf<UUID>()

        val players = RedisManager.jedis.resource.use {
            it.hgetAll("Compound::Messages::Ignorelist::${uuid.toString()}")
        }

        for (uuid in players.keys)
        {
            ignoredList.add(UUID.fromString(uuid))
        }

        return ignoredList
    }

    fun isExcluded(player: Player, target: Player) : Boolean
    {
        return getExcludedPlayers(player.uniqueId).contains(target.uniqueId)
    }

    fun ignorePlayer(player: Player, target: Player)
    {
        RedisManager.jedis.resource.use {
            it.hset(
                "Compound::Messages::Ignorelist::${player.uniqueId.toString()}",
                target.uniqueId.toString(),
                "true"
            )
        }
    }

    fun unignore(player: Player, target: Player)
    {
        RedisManager.jedis.resource.use {
            it.hdel(
                "Compound::Messages::Ignorelist::${player.uniqueId.toString()}",
                target.uniqueId.toString()
            )
        }
    }
}