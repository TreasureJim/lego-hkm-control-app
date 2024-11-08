package com.findingtreasure.phonependant.ui.helper

import com.findingtreasure.comms.MoveLinear
import com.findingtreasure.comms.NetworkManager
import com.findingtreasure.comms.ProtocolHandler
import com.findingtreasure.comms.RobTarget
import com.findingtreasure.phonependant.model.Position
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

suspend fun TrackPath(positionList: List<Position>) {
    CoroutineScope(Dispatchers.IO).launch {
        for (position in positionList) {
            // Create the MoveLinear command
            val moveLinear = MoveLinear(
                motionId = ProtocolHandler.generateMotionId(),
                target = RobTarget(
                    x = position.x,
                    y = position.y,
                    z = position.z,
                    j4 = 0.0,
                    a = 0.0,
                    b = 0.0,
                    c = 0.0
                )
            )

            // Send the MoveLinear command
            NetworkManager.sendData(ProtocolHandler.encodeMoveLinear(moveLinear))

            // Wait for MotionId confirmation
            val motionIdConfirmed = waitForMotionId(moveLinear.motionId)

            // If MotionId not confirmed, stop the path-tracking sequence
            if (!motionIdConfirmed) {
                println("Failed to receive confirmation for motionId ${moveLinear.motionId}")
                break
            }
        }
    }
}

suspend fun waitForMotionId(expectedMotionId: ByteArray, timeout: Long = 5000L): Boolean {
    // Timeout after specified time if MotionId is not received
    return withTimeoutOrNull(timeout) {
        ProtocolHandler.motionIdFlow.first { it.id.contentEquals(expectedMotionId) } != null
    } ?: false // Return false if timeout is reached
}
