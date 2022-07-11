package ltd.matrixstudios.compound.misc.polls

import ltd.matrixstudios.compound.CompoundPlugin
import ltd.matrixstudios.compound.chat.Chat
import ltd.matrixstudios.compound.commands.Command
import ltd.matrixstudios.compound.commands.bukkit.BukkitCommandFunctions
import ltd.matrixstudios.compound.misc.polls.menu.PollMenu
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.omg.PortableInterceptor.ACTIVE
import java.nio.channels.InterruptedByTimeoutException
import java.util.*

object PollManager {

    var activePoll: Poll? = null

    fun startPoll(poll: Poll)
    {
        this.activePoll = poll

        Bukkit.broadcastMessage(Chat.format(CompoundPlugin.instance.config.getString("polls.startMessage")))

        object : BukkitRunnable() {
            var seconds = 60
            override fun run() {
                when (seconds) {
                    60 -> {
                        Bukkit.broadcastMessage(Chat.format(CompoundPlugin.instance.config.getString("polls.intervalMessage")))
                    }

                    0 -> {
                        closePoll()

                        cancel()
                    }
                }

                seconds--
            }

        }.runTaskTimer(CompoundPlugin.instance, 0L, 18L)
    }

    fun closePoll()
    {

        if (activePoll != null)
        {
            val poll = activePoll!!



            val optOneVotes = poll.totalVotes.values.filter { it == poll.optionOne }.size
            val optTwoVotes = poll.totalVotes.values.filter { it == poll.optionTwo }.size

            val message = Chat.format(CompoundPlugin.instance.config.getString("polls.endMessage"))

            if (optOneVotes > optTwoVotes)
            {
                Bukkit.broadcastMessage(message.replace("%entry%", poll.optionOne))

                activePoll = null
            } else if (optTwoVotes > optOneVotes)
            {
                Bukkit.broadcastMessage(message.replace("%entry%", poll.optionTwo))

                activePoll = null
            } else if (optOneVotes == optTwoVotes)
            {
                val random = listOf(poll.optionOne, poll.optionTwo).shuffled()[0]

                Bukkit.broadcastMessage(message.replace("%entry%", random))

                activePoll = null
            }
        }

        activePoll == null
    }

    fun getVotesForPoll(option: String) : Int
    {
       return activePoll!!.totalVotes.values.filter { it == option }.size
    }

    fun hasVoted(player: Player) : Boolean
    {
        return activePoll!!.totalVotes.containsKey(player.uniqueId)
    }


    fun registerCommands()
    {
        Command().create("poll").requirePlayer().execute().handle { args, sender, command -> PollMenu(sender as Player).openMenu()}.bindToPlugin()

        Command().create("createpoll").requirePlayer().permission("compound.polls.admin").execute().handle { args, sender, command ->
            if (args.isEmpty())
            {
                sender.sendMessage(Chat.format("&cUsage: /createpoll <opt1> <opt1> <for>"))
                return@handle
            }

            val option1 = args[0]

            if (option1 == null)
            {
                sender.sendMessage(Chat.format("&cUsage: /createpoll <opt1> <opt1> <for>"))
                return@handle
            }

            val option2 = args[1]

            if (option2 == null)
            {
                sender.sendMessage(Chat.format("&cUsage: /createpoll <opt1> <opt1> <for>"))
                return@handle
            }

            val whatFor = BukkitCommandFunctions.constructStringBuilder(args, 2)

            if (whatFor.isEmpty())
            {
                sender.sendMessage(Chat.format("&cYou must supply a reason!"))
                return@handle
            }

            val poll = Poll(UUID.randomUUID(), whatFor.toString(), option1, option2, hashMapOf())
            startPoll(poll)
            sender.sendMessage(Chat.format("&aStarted a poll!"))
        }.bindToPlugin()

        Command().create("forcestoppoll").permission("compound.polls.admin").execute().handle { args, sender, command ->
            activePoll = null
            sender.sendMessage(Chat.format("&cEnded the current poll!"))
        }.bindToPlugin()
    }
}             