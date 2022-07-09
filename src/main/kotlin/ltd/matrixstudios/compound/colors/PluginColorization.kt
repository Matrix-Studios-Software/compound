package ltd.matrixstudios.compound.colors

import ltd.matrixstudios.compound.CompoundPlugin

class PluginColorization {

    companion object
    {
        val PRIMARY_COLOR: String = CompoundPlugin.instance.config.getString("colors.primary")
        val SECONDARY_COLOR: String = CompoundPlugin.instance.config.getString("colors.secondary")
    }
}