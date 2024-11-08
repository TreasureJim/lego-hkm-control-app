package com.findingtreasure.phonependant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findingtreasure.comms.MoveJog
import com.findingtreasure.comms.MoveLinear
import com.findingtreasure.comms.NetworkManager
import com.findingtreasure.comms.ProtocolHandler
import com.findingtreasure.comms.RobotStatus
import com.findingtreasure.comms.RobTarget
import com.findingtreasure.phonependant.model.Position
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
    // Mutable state for UI to observe position updates
    private val _positionState = MutableStateFlow(initialPosition)
    val positionState: StateFlow<Position> get() = _positionState

    // Sliders for joint rotation
    val slider1Value = mutableFloatStateOf(0f)
    val slider2Value = mutableFloatStateOf(0f)
    val slider3Value = mutableFloatStateOf(0f)
    val sliderXValue = mutableFloatStateOf(0f)
    val sliderYValue = mutableFloatStateOf(0f)
    val sliderZValue = mutableFloatStateOf(0f)

    init {
        NetworkManager.sendData(
            ProtocolHandler.encodeMoveLinear(
                MoveLinear(
                    motionId = ProtocolHandler.generateMotionId(),
                    target = RobTarget(
                        x = initialPosition.x,
                        y = initialPosition.y,
                        z = initialPosition.z,
                        j4 = 0.0,
                        a = 0.0,
                        b = 0.0,
                        c = 0.0
                    )
                )
            )
        )
        startUpdatingPosition(settings.commandSendHertz.value, settings.sensitivity.value)
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

    // Function to set name
    fun setName(value: String) {
        _positionState.value = _positionState.value.copy(
            name = value
        )
    }

    // Coroutine to update position every 5 seconds
    private fun startUpdatingPosition(commandSendHertz: Int, jointSensitivity: Float, coordSensitivity: Float) {
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

//                    /* TO IMPLEMENT: fetch robot status from global variable */
//                    val updatedStatus = RobotStatus(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
//
//                    if (updatedStatus != null) {
//                        _positionState.value = _positionState.value.copy(
//                            x = updatedStatus.x,
//                            y = updatedStatus.y,
//                            z = updatedStatus.z,
//                            j1 = updatedStatus.j1,
//                            j2 = updatedStatus.j2,
//                            j3 = updatedStatus.j3
//                        )
//                    }
                }

                delay((1 / commandSendHertz * 1000).toLong())
            }
        }
    }
}
