package com.findingtreasure.phonependant

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp

@Composable
fun SliderSnapRelease(
    sliderPosition: MutableState<Float>,
    defaultPosition: Float) {

    // State to track whether the user is touching the slider
    val isTouching = remember { androidx.compose.runtime.mutableStateOf(false) }

    // Function that gets called when the slider is released
    val onSliderRelease = { value: Float ->
        // Snap to a default position, e.g., defaultPosition
        val snapToPosition = defaultPosition
        sliderPosition.value = snapToPosition
        println("Slider value snapped to: $snapToPosition")
    }

    Column {
        Slider(
            value = sliderPosition.value,
            onValueChange = { value ->
                sliderPosition.value = value
                isTouching.value = true // Set isTouching to true while slider is moving
            },
            onValueChangeFinished = {
                onSliderRelease(sliderPosition.value)  // Snap to default position when released
                isTouching.value = false // Set isTouching to false when interaction ends
            },
            valueRange = -100f..100f,
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = 270f
                    transformOrigin = TransformOrigin(0f, 0f)
                }
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(
                        Constraints(
                            minWidth = constraints.minHeight,
                            maxWidth = constraints.maxHeight,
                            minHeight = constraints.minWidth,
                            maxHeight = constraints.maxHeight,
                        )
                    )
                    layout(placeable.height, placeable.width) {
                        placeable.place(-placeable.width, 0)
                    }
                }
                .width(280.dp)
                .height(50.dp)
        )

        // Show Text only if the user is interacting with the slider
        Box(modifier = Modifier.height(24.dp)) { // Fixed size for the Text
            if (isTouching.value) {
                Text("${sliderPosition.value.toInt()}", Modifier.align(Alignment.Center))
            }
        }
    }
}

@Preview
@Composable
fun PreviewSliderWithSnapOnRelease() {
    val default = 0f
    val sliderPosition = remember { mutableStateOf(default) }
    SliderSnapRelease(sliderPosition = sliderPosition, default)
}
