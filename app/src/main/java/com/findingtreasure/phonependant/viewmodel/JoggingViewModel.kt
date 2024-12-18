package com.findingtreasure.phonependant.viewmodel

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findingtreasure.comms.MoveJog
import com.findingtreasure.comms.MoveLinear
import com.findingtreasure.comms.NetworkManager
import com.findingtreasure.comms.ProtocolHandler
import com.findingtreasure.comms.RobotStatus
import com.findingtreasure.comms.RobTarget
import com.findingtreasure.phonependant.model.Position
import com.findingtreasure.phonependant.model.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class JoggingViewModel(
    initialPosition: Position,
    settings: SettingsViewModel
) : ViewModel() {
    // Sliders for joint rotation
    val slider1Value = mutableFloatStateOf(0f)
    val slider2Value = mutableFloatStateOf(0f)
    val slider3Value = mutableFloatStateOf(0f)
    val sliderXValue = mutableFloatStateOf(0f)
    val sliderYValue = mutableFloatStateOf(0f)
    val sliderZValue = mutableFloatStateOf(0f)
    val positionState = mutableStateOf(initialPosition)

    init {
        // TODO
//        NetworkManager.sendData(
//            ProtocolHandler.encodeMoveLinear(
//                MoveLinear(
//                    motionId = ProtocolHandler.generateMotionId(),
//                    target = RobTarget(
//                        x = initialPosition.x,
//                        y = initialPosition.y,
//                        z = initialPosition.z,
//                        j4 = 0.0,
//                        a = 0.0,
//                        b = 0.0,
//                        c = 0.0
//                    )
//                )
//            )
//        )
        startUpdatingPosition(settings.commandSendHertz.value.toFloat(), settings.jointsensitivity.value, settings.coordsensitivity.value)
    }

    // Function to set name
    fun setName(newName: String) {
        positionState.value = positionState.value.copy(name = newName)
    }

    // Function to update position
    fun updatePosition(status: Status) {
        positionState.value = positionState.value.copy(status = status)
    }

    // Function to set slider values
    fun setSliderValue(sliderNumber: String, value: Float ) {
        when (sliderNumber) {
            "1" -> slider1Value.value = value
            "2" -> slider2Value.value = value
            "3" -> slider3Value.value = value
            "X" -> sliderXValue.value = value
            "Y" -> sliderYValue.value = value
            "Z" -> sliderZValue.value = value
        }
    }

    // Coroutine to update position
    private fun startUpdatingPosition(commandSendHertz: Float, jointSensitivity: Float, coordSensitivity: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                if (slider1Value.value != 0f || slider2Value.value != 0f || slider3Value.value != 0f ||
                    sliderXValue.value != 0f || sliderYValue.value != 0f || sliderZValue.value != 0f) {
                    // Send MoveJog command with current slider values
                    val jog = MoveJog(
                        motionId = ProtocolHandler.generateMotionId(),
                        x = sliderXValue.value.toDouble() * coordSensitivity,
                        y = sliderYValue.value.toDouble() * coordSensitivity,
                        z = sliderZValue.value.toDouble() * coordSensitivity,
                        j1 = slider1Value.value.toDouble() * jointSensitivity,
                        j2 = slider2Value.value.toDouble() * jointSensitivity,
                        j3 = slider3Value.value.toDouble() * jointSensitivity,
                        j4 = 0.0
                    )
                    NetworkManager.sendData(
                        ProtocolHandler.encodeMoveJog(jog)
                    )
                }

                delay((1 / commandSendHertz * 1000).toLong())
            }
        }
    }
}
