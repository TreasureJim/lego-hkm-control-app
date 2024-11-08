package com.findingtreasure.comms

import com.findingtreasure.phonependant._currentPostion
import com.findingtreasure.phonependant.model.Position
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.UUID
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow


enum class SIG_ID_STRUCTS(val id: Int, val size: Int) {
    MotionId(0, 18),
    MoveJog(1, 73),
    RobotRequestStatus(2, 1),
    RobotStatus(3, 42),
    MoveLinear(4, 73)
}

object ProtocolHandler {
    private val _motionIdFlow = MutableSharedFlow<MotionId>(extraBufferCapacity = 1) // Emit MotionIds as they are decoded
    val motionIdFlow: SharedFlow<MotionId> get() = _motionIdFlow

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

        val motionId = MotionId(id, status)

        // Emit decoded motionId to flow
        _motionIdFlow.tryEmit(motionId)
        return motionId
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

        _currentPostion.value = Position(0,"Default Position", status.x, status.y, status.z, status.j1, status.j2, status.j3)

        return status
    }

    // ENCODE

    fun encodeMoveJog(moveJog: MoveJog): ByteArray {
        val buffer = ByteBuffer.allocate(73).order(ByteOrder.BIG_ENDIAN)
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
        val buffer = ByteBuffer.allocate(73).order(ByteOrder.BIG_ENDIAN)
        buffer.put(0x04) // s_id for MoveJog
        buffer.put(moveLinear.motionId.copyOf(16)) // Ensure motionId is exactly 16 bytes
        buffer.put(
            encodeRobTarget(moveLinear.target)
        )
        return buffer.array()
    }

    private fun encodeRobTarget(robTarget: RobTarget) : ByteArray {
        val buffer = ByteBuffer.allocate(73).order(ByteOrder.BIG_ENDIAN)
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
