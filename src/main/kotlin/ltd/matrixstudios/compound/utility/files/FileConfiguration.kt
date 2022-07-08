package ltd.matrixstudios.compound.utility.files

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException


class FileConfiguration(plugin: JavaPlugin, fileName: String) {
    val file: File
    val config: FileConfiguration

    init {
        file = File(plugin.dataFolder, fileName)
        if (!file.exists()) {
            file.parentFile.mkdirs()
            if (plugin.getResource(fileName) == null) {
                try {
                    file.createNewFile()
                } catch (e: IOException) {
                    plugin.logger.severe("Failed to create new file $fileName")
                }
            } else {
                plugin.saveResource(fileName, false)
            }
        }
        config = YamlConfiguration.loadConfiguration(file) as FileConfiguration
    }

    fun save() {
        try {
            config.save(file)
        } catch (e: IOException) {
            Bukkit.getLogger().severe("Could not save config file " + file.toString())
            e.printStackTrace()
        }
    }
}