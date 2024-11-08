package com.findingtreasure.comms

import com.findingtreasure.phonependant._currentPostion
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.UUID

enum class SIG_ID_STRUCTS(val id: Int, val size: Int) {
    MotionId(0, 18),
    RobotStatus(3, 42)
}

object ProtocolHandler {
    // DECODE

    fun numBytesToDecode(sigId: Int): Int {
        return when (sigId) {
            SIG_ID_STRUCTS.MotionId.id -> SIG_ID_STRUCTS.MotionId.size
            SIG_ID_STRUCTS.RobotStatus.id -> SIG_ID_STRUCTS.RobotStatus.size
            else -> {
                println("[ERROR] Decoded invalid sig_id: $sigId")
                0
            }
        }
    }

    fun decode(data: ByteArray): Any? {
        return when (data.getOrNull(0)?.toInt()) {
            0 -> decodeMotionId(data)
            3 -> decodeRobotStatus(data)
            else -> null
        }
    }

    private fun decodeMotionId(data: ByteArray): MotionId? {
        if (data.size != 18 || data[0] != 0.toByte()) return null

        val id = data.sliceArray(1..16)
        val status = data[17]

        return MotionId(id, status)
    }

    private fun decodeRobotStatus(data: ByteArray): RobotStatus? {
        if (data.size != 42) return null

        val buffer = ByteBuffer.wrap(data, 0, 42).order(ByteOrder.BIG_ENDIAN)

        val x = buffer.double
        val y = buffer.double
        val z = buffer.double
        val j1 = buffer.double
        val j2 = buffer.double
        val j3 = buffer.double
        val j4 = buffer.double

        val status = RobotStatus(x, y, z, j1, j2, j3, j4)

        _currentPostion.value = status

        return status
    }

    // ENCODE

    fun encodeMoveJog(moveJog: MoveJog): ByteArray {
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

    fun encodeRobotRequestStatus(): ByteArray {
        return byteArrayOf(0x02) // s_id for RobotRequestStatus
    }

    // HELPER

    fun generateMotionId(): ByteArray {
        return UUID.randomUUID().toString().toByteArray()
    }
}
