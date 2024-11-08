package com.findingtreasure.comms

import com.findingtreasure.phonependant._currentPostion
import com.findingtreasure.phonependant.model.Position
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.UUID

enum class SIG_ID_STRUCTS(val id: Int, val size: Int) {
    MotionId(0, 17),
    RobotStatus(3, 56),
    MoveJog(1, 73),
    RobotRequestStatus(2, 1),
    MoveLinear(4, 73),
    MovePos(5, 73)
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

    fun decode(sidId: Byte, data: ByteArray): Any? {
        return when (sidId) {
            0x00.toByte() -> decodeMotionId(data)
            0x03.toByte() -> decodeRobotStatus(data)
            else -> null
        }
    }

    private fun decodeMotionId(data: ByteArray): MotionId? {
        if (data.size != SIG_ID_STRUCTS.MotionId.size) return null

        val id = data.sliceArray(0..15)
        val status = data[16]

        return MotionId(id, status)
    }

    private fun decodeRobotStatus(data: ByteArray): RobotStatus? {
        if (data.size != SIG_ID_STRUCTS.RobotStatus.size) return null

        val buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN)

        val x = buffer.double
        val y = buffer.double
        val z = buffer.double
        val j1 = buffer.double
        val j2 = buffer.double
        val j3 = buffer.double
        val j4 = buffer.double

        val status = RobotStatus(x, y, z, j1, j2, j3, j4)

        val newPosition = _currentPostion.value.copy(x = status.x, y = status.y, z = status.z, j1 = status.j1, j2 = status.j2, j3 = status.j3)
        _currentPostion.value = newPosition

        return status
    }

    // ENCODE

    fun encodeMoveJog(moveJog: MoveJog): ByteArray {
        val buffer = ByteBuffer.allocate(73).order(ByteOrder.LITTLE_ENDIAN)
        buffer.put(0x01) // s_id for MoveJog
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

    fun encodeMoveLinear(moveLinear: MoveLinear) : ByteArray {
        val buffer = ByteBuffer.allocate(73).order(ByteOrder.LITTLE_ENDIAN)
        buffer.put(0x04) // s_id for MoveJog
        buffer.put(moveLinear.motionId.copyOf(16)) // Ensure motionId is exactly 16 bytes
        buffer.put(
            encodeRobTarget(moveLinear.target)
        )
        return buffer.array()
    }

    fun encodeMovePos(movePos: MovePos) : ByteArray {
        val buffer = ByteBuffer.allocate(73).order(ByteOrder.LITTLE_ENDIAN)
        buffer.put(0x04) // s_id for MoveJog
        buffer.put(movePos.motionId.copyOf(16)) // Ensure motionId is exactly 16 bytes
        buffer.put(
            encodeRobTarget(movePos.target)
        )
        return buffer.array()
    }

    private fun encodeRobTarget(robTarget: RobTarget) : ByteArray {
        val buffer = ByteBuffer.allocate(73).order(ByteOrder.LITTLE_ENDIAN)
        buffer.putDouble(robTarget.x)
        buffer.putDouble(robTarget.y)
        buffer.putDouble(robTarget.z)
        buffer.putDouble(robTarget.j4)
        buffer.putDouble(robTarget.a)
        buffer.putDouble(robTarget.b)
        buffer.putDouble(robTarget.c)
        return buffer.array()
    }

    // HELPER

    fun generateMotionId(): ByteArray {
        return UUID.randomUUID().toString().toByteArray()
    }
}
