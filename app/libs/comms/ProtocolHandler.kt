import java.nio.ByteBuffer
import java.nio.ByteOrder

class ProtocolHandler {
    // Encoding method
    fun encode(message: Any): ByteArray {
        return when (message) {
            is MoveJog -> encodeMoveJog(message)
            is RobotRequestStatus -> encodeRobotRequestStatus()
            else -> throw IllegalArgumentException("Unknown message type for encoding")
        }
    }

    // Decoding method
    fun decode(data: ByteArray): Any? {
        return when (data.getOrNull(0)?.toInt()) {
            0 -> decodeMotionId(data)
            3 -> decodeRobotStatus(data)
            else -> null
        }
    }

    private fun encodeMoveJog(moveJog: MoveJog): ByteArray {
        val buffer = ByteBuffer.allocate(65).order(ByteOrder.BIG_ENDIAN)
        buffer.put(1) // s_id for MoveJog
        buffer.put(moveJog.motionId.copyOf(16)) // Ensure motionId is exactly 16 bytes
        buffer.putDouble(moveJog.x)
        buffer.putDouble(moveJog.y)
        buffer.putDouble(moveJog.z)
        buffer.putDouble(moveJog.j1)
        buffer.putDouble(moveJog.j2)
        buffer.putDouble(moveJog.j3)
        buffer.putDouble(moveJog.j4)
        return buffer.array()
    }

    private fun encodeRobotRequestStatus(): ByteArray {
        return byteArrayOf(2) // s_id for RobotRequestStatus
    }

    private fun decodeMotionId(data: ByteArray): MotionId? {
        if (data.size != 18 || data[0] != 0.toByte()) return null

        val id = data.sliceArray(1..16)
        val status = data[17]

        return MotionId(id, status)
    }

    private fun decodeRobotStatus(data: ByteArray): RobotStatus? {
        if (data.size != 65 || data[0] != 3.toByte()) return null

        val motionId = data.sliceArray(1..16)
        val buffer = ByteBuffer.wrap(data, 17, 48).order(ByteOrder.BIG_ENDIAN)

        val x = buffer.double
        val y = buffer.double
        val z = buffer.double
        val j1 = buffer.double
        val j2 = buffer.double
        val j3 = buffer.double
        val j4 = buffer.double

        return RobotStatus(motionId, x, y, z, j1, j2, j3, j4)
    }
}
