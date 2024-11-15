package com.findingtreasure.phonependant

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun SliderSnapRelease(
    sliderPosition: MutableState<Float>,
    range: ClosedFloatingPointRange<Float>,
    defaultPosition: Float
) {

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

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(50.dp)
                .height(280.dp)
        ) {
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
                valueRange = range,
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
                    .height(50.dp),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = Color.Transparent,
                    inactiveTrackColor = MaterialTheme.colorScheme.surface
                ),
            )


            val rectColor = MaterialTheme.colorScheme.primary
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        val sliderHeight = size.height
                        val centerY = sliderHeight / 2
                        val thumbPositionY = centerY - (sliderPosition.value / 100f) * centerY

                        // Draw the orange bar up to the thumb position
                        if (sliderPosition.value > 0f) {
                            drawRect(
                                color = rectColor,
                                topLeft = Offset(center.x - 4.dp.toPx(), thumbPositionY),
                                size = Size(8.dp.toPx(), centerY - thumbPositionY)
                            )
                        } else if (sliderPosition.value < 0f) {
                            drawRect(
                                color = rectColor,
                                topLeft = Offset(center.x - 4.dp.toPx(), centerY),
                                size = Size(8.dp.toPx(), abs(thumbPositionY - centerY))
                            )
                        }
                    }
            )
        }

        // Show Text only if the user is interacting with the slider
        Box(
            modifier = Modifier
                .height(24.dp)
                .align(Alignment.CenterHorizontally)

        ) { // Fixed size for the Text
            if (isTouching.value) {
                Text("${sliderPosition.value.toInt()}",
                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onBackground))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSliderWithSnapOnRelease() {
    val default = 0f
    val sliderPosition = remember { mutableStateOf(default) }
    SliderSnapRelease(sliderPosition = sliderPosition, -100f..100f, default)
}
