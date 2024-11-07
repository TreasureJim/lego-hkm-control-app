package com.findingtreasure.phonependant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findingtreasure.comms.CommunicationHandler
import com.findingtreasure.comms.MoveJog
import com.findingtreasure.comms.ProtocolHandler
import com.findingtreasure.comms.RobotRequestStatus
import com.findingtreasure.comms.RobotStatus
import com.findingtreasure.phonependant.model.Position
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JoggingViewModel(
    initialPosition: Position
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
        /* TO IMPLEMENT: move point to point to initialPosition */
        startUpdatingPosition()
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
    private fun startUpdatingPosition() {
        viewModelScope.launch {
            while (true) {
                if (_slider1Value.value != 0f || _slider2Value.value != 0f || _slider3Value.value != 0f ||
                    _sliderXValue.value != 0f || _sliderYValue.value != 0f || _sliderZValue.value != 0f) {
                    // Send MoveJog command with current slider values
                    /* TO IMPLEMENT: speed based on slider value? */
                    val moveJog = MoveJog(
                        motionId = ProtocolHandler.generateMotionId(),
                        x = _sliderXValue.value.toDouble(),
                        y = _sliderYValue.value.toDouble(),
                        z = _sliderZValue.value.toDouble(),
                        j1 = _slider1Value.value.toDouble(),
                        j2 = _slider2Value.value.toDouble(),
                        j3 = _slider3Value.value.toDouble(),
                        j4 = 0.0
                    )
                    CommunicationHandler.transmit(
                        ProtocolHandler.encode(moveJog)
                    )

                    // Request robot status
                    CommunicationHandler.transmit(
                        ProtocolHandler.encode(RobotRequestStatus)
                    )

                    // Fetch the updated RobotStatus and update positionState
                    val updatedStatus = ProtocolHandler.decode(
                        CommunicationHandler.receive()
                    ) as? RobotStatus

                    if (updatedStatus != null) {
                        _positionState.value = _positionState.value.copy(
                            x = updatedStatus.x,
                            y = updatedStatus.y,
                            z = updatedStatus.z,
                            axis1 = updatedStatus.j1,
                            axis2 = updatedStatus.j2,
                            axis3 = updatedStatus.j3
                        )
                    }
                }

                // Delay for 5 seconds
                delay(5000)
            }
        }
    }
}
