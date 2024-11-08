package com.findingtreasure.phonependant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findingtreasure.comms.MoveJog
import com.findingtreasure.comms.MoveLinear
import com.findingtreasure.comms.NetworkManager
import com.findingtreasure.comms.ProtocolHandler
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
    private val _slider1Value = MutableStateFlow(0f)
    private val _slider2Value = MutableStateFlow(0f)
    private val _slider3Value = MutableStateFlow(0f)
    private val _sliderXValue = MutableStateFlow(0f)
    private val _sliderYValue = MutableStateFlow(0f)
    private val _sliderZValue = MutableStateFlow(0f)
    val slider1Value: StateFlow<Float> get() = _slider1Value
    val slider2Value: StateFlow<Float> get() = _slider2Value
    val slider3Value: StateFlow<Float> get() = _slider3Value
    val sliderXValue: StateFlow<Float> get() = _sliderXValue
    val sliderYValue: StateFlow<Float> get() = _sliderYValue
    val sliderZValue: StateFlow<Float> get() = _sliderZValue

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
            "1" -> _slider1Value.value = value
            "2" -> _slider2Value.value = value
            "3" -> _slider3Value.value = value
            "X" -> _sliderXValue.value = value
            "Y" -> _sliderYValue.value = value
            "Z" -> _sliderZValue.value = value
        }
    }

    // Function to set name
    fun setName(value: String) {
        _positionState.value = _positionState.value.copy(
            name = value
        )
    }

    // Coroutine to update position every 5 seconds
    private fun startUpdatingPosition(commandSendHertz: Int, sensitivity: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                if (_slider1Value.value != 0f || _slider2Value.value != 0f || _slider3Value.value != 0f ||
                    _sliderXValue.value != 0f || _sliderYValue.value != 0f || _sliderZValue.value != 0f) {
                    // Send MoveJog command with current slider values
                    val jog = MoveJog(
                        motionId = ProtocolHandler.generateMotionId(),
                        x = _sliderXValue.value.toDouble() * sensitivity,
                        y = _sliderYValue.value.toDouble() * sensitivity,
                        z = _sliderZValue.value.toDouble() * sensitivity,
                        j1 = _slider1Value.value.toDouble() * sensitivity,
                        j2 = _slider2Value.value.toDouble() * sensitivity,
                        j3 = _slider3Value.value.toDouble() * sensitivity,
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
