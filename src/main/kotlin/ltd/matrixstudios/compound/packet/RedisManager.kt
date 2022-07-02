package ltd.matrixstudios.compound.packet

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import ltd.matrixstudios.compound.packet.pubsub.PacketPubSub
import ltd.matrixstudios.compound.tasks.Tasks
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import java.net.URI
import kotlin.concurrent.thread

object RedisManager {

    lateinit var jedis: JedisPool
    lateinit var resource: Jedis

    val gson: Gson =
        GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .create()

    fun load(uri: String)
    {
        jedis = JedisPool(URI(uri))

        resource = jedis.resource

        thread {
            resource.subscribe(PacketPubSub(), "Compound||Packets||")
        }
    }

    fun send(packet: RedisPacket)
    {
        Tasks.async {
            resource.use { jedis ->
                val encodedPacket = packet.javaClass.name + "|" + gson.toJson(packet)
                jedis.publish("Compound||Packets||", encodedPacket)
            }
        }

    }
}