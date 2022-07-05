package ltd.matrixstudios.compound.packet

abstract class RedisPacket(
    var packetId: String
) {
    abstract fun action()
}