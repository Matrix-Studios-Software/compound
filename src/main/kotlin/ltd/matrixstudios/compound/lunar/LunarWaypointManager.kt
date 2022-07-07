package ltd.matrixstudios.compound.lunar

import com.lunarclient.bukkitapi.LunarClientAPI
import com.lunarclient.bukkitapi.`object`.LCWaypoint
import ltd.matrixstudios.compound.CompoundPlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.awt.Color

object LunarWaypointManager {

    val waypoints = hashMapOf<String, LCWaypoint>()

    fun sendTo(
        player: Player,
        waypoint: LCWaypoint
    )
    {
        LunarClientAPI.getInstance().sendWaypoint(player, waypoint)
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

    fun loadWaypoints()
    {
        for (key in CompoundPlugin.instance.config.getConfigurationSection("waypoints").getKeys(false))
        {
            val config = CompoundPlugin.instance.config

            val name = key
            val world = config.getString("waypoints.$name.world")
            val color = config.getString("waypoints.$name.color")
            val x = config.getInt("waypoints.$name.x")
            val y = config.getInt("waypoints.$name.y")
            val z = config.getInt("waypoints.$name.z")

            waypoints[name] = LCWaypoint(name, x, y, z, world, Color.getColor(color).rgb, true, true)
        }
    }
}