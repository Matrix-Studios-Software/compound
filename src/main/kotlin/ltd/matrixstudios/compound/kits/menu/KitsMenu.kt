package ltd.matrixstudios.compound.kits.menu

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.kits.Kit
import ltd.matrixstudios.compound.kits.KitManager
import ltd.matrixstudios.compound.kits.cooldown.KitCooldownService
import ltd.matrixstudios.compound.menu.Button
import ltd.matrixstudios.compound.menu.Menu
import ltd.matrixstudios.compound.menu.buttons.SimpleActionButton
import ltd.matrixstudios.compound.utility.time.TimeUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class KitsMenu(val player: Player): Menu(CompoundPlugin.instance.config.getInt("kits.menuSize"), player) {

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        for (kit in KitManager.kits.values)
        {
            buttons[kit.slot] = KitButton(kit)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Viewing All Kits"
    }

    class KitButton(val kit: Kit) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return kit.displayItem
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = kit.description.toCollection(mutableListOf())
            desc.add(" ")
            if (KitCooldownService.isOnCoodown(kit, player.uniqueId))
            {
                desc.add(Chat.format("&cYou are currently on colodown!"))
                desc.add(Chat.format("&cRemaining: &f" + TimeUtil.formatDuration(KitCooldownService.getCooldown(kit, player.uniqueId)!!.minus(System.currentTimeMillis()))))
            }

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(kit.displayName)
        }

        override fun getData(player: Player): Short {
            return kit.displayData
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            if (kit.permission != "" && !player.hasPermission(kit.permission))
            {
                player.sendMessage(Chat.format("&cYou do not have access to this kit!"))
                return
            }

            if (kit.permission == "" || player.hasPermission(kit.permission))
            {
                if (KitCooldownService.isOnCoodown(kit, player.uniqueId))
                {
                    player.sendMessage(Chat.format("&cYou are currently on kit cooldown!"))
                    return
                }

                KitManager.giveToPlayer(kit, player)

                player.sendMessage(Chat.format("&aApplied the &f${kit.displayName} &akit!"))
            }

            player.closeInventory()
        }

    }
}