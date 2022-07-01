package ltd.matrixstudios.compound.tasks

import ltd.matrixstudios.compound.CompoundPlugin
import org.bukkit.Bukkit

object Tasks {

    private val plugin = CompoundPlugin.instance

    fun sync(task: () -> Unit)
    {
        Bukkit.getScheduler().runTask(plugin, task)
    }

    fun async(task: () -> Unit)
    {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task)
    }

    fun syncDelayed(task: () -> Unit, delay: Long)
    {
        Bukkit.getScheduler().runTaskLater(plugin, task, delay)
    }

    fun asyncDelayed(task: () -> Unit, delay: Long)
    {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay)
    }

    fun syncRepeating(task: () -> Unit, delay: Long, period: Long)
    {
        Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period)
    }

    fun asyncRepeating(task: () -> Unit, delay: Long, period: Long)
    {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, delay, period)
    }
}