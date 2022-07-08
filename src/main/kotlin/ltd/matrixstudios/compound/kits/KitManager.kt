package ltd.matrixstudios.compound.kits

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.utility.files.FileConfiguration
import ltd.matrixstudios.compound.inventory.InventoryUtils
import ltd.matrixstudios.compound.utility.time.TimeUtil
import org.bukkit.Material
import java.util.concurrent.TimeUnit

object KitManager {

    private lateinit var config: FileConfiguration

    val kits = hashMapOf<String, Kit>()

    fun loadKits()
    {

        config = FileConfiguration(CompoundPlugin.instance, "kits.yml")

        if (!config.config.isConfigurationSection("kits"))
        {
            config.config.createSection("kits")
        }

        for (entry in config.config.getConfigurationSection("kits").getKeys(false))
        {
            val kitConfig = config.config

            //display
            val name = entry
            val displayName = kitConfig.getString("kits.$name.displayName")
            val time = kitConfig.getString("kits.$name.cooldown")
            val material = Material.getMaterial(kitConfig.getString("kits.$name.material"))

            //contents
            val armor = InventoryUtils.toItemArray(kitConfig, "kits.$name.armor")
            val inventory = InventoryUtils.toInventory(kitConfig, "kits.$name.inventory")!!

            val finalKit = Kit(name, displayName, time, armor, inventory, material)

            kits[name] = finalKit
        }

        config.save()
    }

    fun save(kit: Kit)
    {
        val kitconfig = config.config

        val name = kit.id

        kitconfig.set("kits.$name.displayName", kit.displayName)
        kitconfig.set("kits.$name.cooldown", kit.cooldown)
        kitconfig.set("kits.$name.material", kit.displayItem.name)
        InventoryUtils.saveItemArray(kit.armor, kitconfig, "kits.$name.armor")
        InventoryUtils.saveInventory(kit.contents, kitconfig, "kits.$name.inventory")

        config.save()
        kits[name] = kit
    }

    fun getKit(name: String) : Kit?
    {
        return kits[name]
    }
}