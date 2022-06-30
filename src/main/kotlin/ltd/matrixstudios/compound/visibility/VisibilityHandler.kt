package ltd.matrixstudios.compound.visibility

import org.bukkit.entity.Player

class VisibilityHandler {

    lateinit var enableVisibility: (Player) -> Unit
    lateinit var changeVisibility: (Player) -> Unit


    fun onDisableVisibility(predicate: (Player) -> Unit): VisibilityHandler
    {
        return this.apply {
            changeVisibility = predicate
        }
    }

    fun onEnableVisibility(predicate: (Player) -> Unit): VisibilityHandler
    {
        return this.apply {
            enableVisibility = predicate
        }
    }


    fun processDisableVisibility(player: Player)
    {
        changeVisibility.invoke(player)
    }

    fun processEnableVisibility(player: Player)
    {
        enableVisibility.invoke(player)
    }

}
