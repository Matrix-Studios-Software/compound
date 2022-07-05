package ltd.matrixstudios.compound.misc.polls.menu

import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.menu.Button
import ltd.matrixstudios.compound.menu.Menu
import ltd.matrixstudios.compound.menu.buttons.SimpleActionButton
import ltd.matrixstudios.compound.misc.polls.PollManager
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class PollMenu(val player: Player) : Menu(27, player) {

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        buttons[12] = BooleanButton(true)
        buttons[14] = BooleanButton(false)

        val activePoll = PollManager.activePoll

        if (activePoll != null) {

            buttons[4] =
                SimpleActionButton(Material.BEACON,
                    mutableListOf(Chat.format("&7&m------------------"),
                        Chat.format("&e${activePoll.optionOne}: &c${PollManager.getVotesForPoll(activePoll.optionOne)}"),
                        Chat.format("&e${activePoll.optionTwo}: &c${PollManager.getVotesForPoll(activePoll.optionTwo)}"),
                        Chat.format("&7&m------------------")),
                Chat.format("&eQuestion: &c${activePoll.whatFor}"),
                0,
                null)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Poll Viewing Menu"
    }

    class BooleanButton(val boolean: Boolean) : Button() {

        val poll = PollManager.activePoll

        override fun getMaterial(player: Player): Material {
            if (poll == null)
            {
                return Material.BEDROCK
            } else if (boolean || !boolean)
            {
                return Material.WOOL
            }

            return Material.BEDROCK
        }

        override fun getDescription(player: Player): MutableList<String>? {
            return mutableListOf()
        }

        override fun getDisplayName(player: Player): String? {
            if (poll != null)
            {
                if (boolean) {
                    return Chat.format("&a${poll!!.optionOne}")
                } else if (!boolean) {
                    return Chat.format("&c${poll!!.optionTwo}")
                }
            }

            return Chat.format("&cNo Active Poll")
        }

        override fun getData(player: Player): Short {
            if (poll != null)
            {
                if (boolean)
                {
                    return 5
                } else if (!boolean) return 14
            }
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            if (poll != null)
            {

                if (!PollManager.hasVoted(player)) {

                    if (boolean) {
                        poll.totalVotes[player.uniqueId] = poll.optionOne
                        player.sendMessage(Chat.format("&aYou voted for &f${poll.optionOne}"))
                    } else if (!boolean) {
                        poll.totalVotes[player.uniqueId] = poll.optionTwo
                        player.sendMessage(Chat.format("&aYou voted for &f${poll.optionTwo}"))
                    }
                } else {
                    player.sendMessage(Chat.format("&cYou have already voted for an option!"))
                }
            } else {
                player.sendMessage(Chat.format("&cThere is no current poll going on!"))
            }
        }

    }
}