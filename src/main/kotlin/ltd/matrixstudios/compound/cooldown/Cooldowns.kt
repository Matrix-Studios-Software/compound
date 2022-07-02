package ltd.matrixstudios.compound.cooldown

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import com.google.common.collect.Tables
import java.util.*

object Cooldowns {

    var cooldownTable = HashBasedTable.create<String, UUID, Long>()

    fun addCooldown(cooldown: String, target: UUID, time: Long)
    {
        cooldownTable.put(cooldown, target, System.currentTimeMillis() + time)
    }

    fun isOnCooldown(uuid: UUID, cooldown: String) : Boolean
    {
        return cooldownTable.containsColumn(uuid) && cooldownTable.get(cooldown, uuid) >= System.currentTimeMillis()
    }

}