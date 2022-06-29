package ltd.matrixstudios.compound.visibility

import org.bukkit.entity.Player

class VisibilityHandler {

    lateinit var startvisibility: (Player) -> Unit
    lateinit var changevisibility: (Player) -> Unit


    fun onChangeVisibility(predicate: (Player) -> Unit): VisibilityHandler
    {
        return this.apply {
            changevisibility = predicate
        }
    }

    fun onStartVisibility(predicate: (Player) -> Unit): VisibilityHandler
    {
        return this.apply {
            startvisibility = predicate
        }
    }


    fun processChangeVisibility(player: Player)
    {
        changevisibility.invoke(player)
    }

    fun processStartVisibility(player: Player)
    {
        startvisibility.invoke(player)
    }

}
