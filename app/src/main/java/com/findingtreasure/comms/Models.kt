package com.findingtreasure.comms

data class MotionId(
    val id: ByteArray, // 16 bytes
    val status: Byte   // 1 byte
)

data class MoveJog(
    val motionId: ByteArray, // 16 bytes
    val x: Double,           // 8 bytes each
    val y: Double,
    val z: Double,
    val j1: Double,
    val j2: Double,
    val j3: Double,
    val j4: Double
)

data class RobotStatus(
    val x: Double,           // 8 bytes each
    val y: Double,
    val z: Double,
    val j1: Double,
    val j2: Double,
    val j3: Double,
    val j4: Double
)

object RobotRequestStatus