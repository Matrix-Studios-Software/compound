package ltd.matrixstudios.compound.tasks

import ltd.matrixstudios.compound.CompoundPlugin
import org.bukkit.Bukkit

object Tasks {

    val plugin = CompoundPlugin.instance

    fun sync(task: () -> Unit)
    {
        Bukkit.getScheduler().runTask(plugin, task)
    }

    fun async(task: () -> Unit)
    {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task)
    }
}