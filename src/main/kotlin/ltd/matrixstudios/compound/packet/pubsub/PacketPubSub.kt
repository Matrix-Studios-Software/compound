package ltd.matrixstudios.compound.packet.pubsub

import ltd.matrixstudios.compound.packet.RedisManager
import ltd.matrixstudios.compound.packet.RedisPacket
import ltd.matrixstudios.compound.tasks.Tasks
import redis.clients.jedis.JedisPubSub

class PacketPubSub : JedisPubSub() {

    override fun onMessage(channel: String?, message: String) {
        val packetClass: Class<*>
        val packetMessageSplit = message.indexOf("|")
        val packetClassStr = message.substring(0, packetMessageSplit)
        val messageJson = message.substring(packetMessageSplit + "|".length)
        packetClass = try {
            Class.forName(packetClassStr)
        } catch (ignored: ClassNotFoundException) {
            return
        }
        val packet = RedisManager.gson.fromJson(messageJson, packetClass) as RedisPacket
        Tasks.sync { packet.action() }
    }
}