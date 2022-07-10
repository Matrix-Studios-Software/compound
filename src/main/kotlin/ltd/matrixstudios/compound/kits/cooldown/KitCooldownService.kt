package ltd.matrixstudios.compound.kits.cooldown

import ltd.matrixstudios.compound.kits.Kit
import ltd.matrixstudios.compound.packet.RedisManager
import java.util.UUID

object KitCooldownService {

    fun addCooldown(kitName: String, uuid: UUID, time: Long)
    {
        RedisManager.jedis.resource.use {
            it.hset(
                "Compound::Kits::$kitName::Cooldowns::",
                uuid.toString(),
                time.toString()
            )
        }
    }

    fun getCooldown(kit: Kit, player: UUID) : Long?
    {
        val long = RedisManager.jedis.resource.use { it.hget("Compound::Kits::${kit.id}::Cooldowns::", player.toString()) } ?: return null

        return long.toLong()
    }

    fun isOnCoodown(kit: Kit, player: UUID) : Boolean
    {
        val long = RedisManager.jedis.resource.use { it.hget("Compound::Kits::${kit.id}::Cooldowns::", player.toString()) } ?: return false

        return long.toLong() >= System.currentTimeMillis()
    }
}