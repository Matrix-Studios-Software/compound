package ltd.matrixstudios.compound.lunar

import com.lunarclient.bukkitapi.LunarClientAPI
import com.lunarclient.bukkitapi.`object`.LCWaypoint
import org.bukkit.entity.Player

object LunarWaypointManager {

    val waypoints = hashMapOf<String, LCWaypoint>()

    fun sendTo(
        player: Player,
        name: String,
        x: Int, y: Int, z: Int,
        world: String,
        color: Int
    )
    {
        val lcWaypoint: LCWaypoint = LCWaypoint(name, x, y, z, world, color, true, true)
        LunarClientAPI.getInstance().sendWaypoint(player, lcWaypoint)

        waypoints[name] = lcWaypoint
    }

    fun removeFrom(player: Player, name: String)
    {
        val waypoint = waypoints[name]

        if (waypoint == null)
        {
            throw Exception("Waypoint was not found!")
        } else
        {
            LunarClientAPI.getInstance().removeWaypoint(player, waypoint)
        }

    }
}