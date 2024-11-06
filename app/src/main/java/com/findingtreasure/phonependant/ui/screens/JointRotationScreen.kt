package com.findingtreasure.phonependant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.findingtreasure.phonependant.model.Position
import com.findingtreasure.phonependant.ui.helper.DisplayField
import com.findingtreasure.phonependant.ui.helper.AxisSlider

@Composable
fun JointRotationScreen(
    position: Position?,
    onTabSelected: (String, Position) -> Unit,
    onSave: (Position) -> Unit
) {
    var slider1Value by remember { mutableStateOf(0f) }
    var slider2Value by remember { mutableStateOf(0f) }
    var slider3Value by remember { mutableStateOf(0f) }
    var positionState by remember { mutableStateOf(position) }

    // Main container
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Tab Row
        TabRow(
            selectedTabIndex = 0,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(selected = true, onClick = { /* Stay on Joint screen */ }) {
                Text("Joint", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.labelSmall)
            }
            Tab(selected = false, onClick = { onTabSelected("coordinateInput/${position?.id}", position!!.copy(name = positionState?.name ?: "")) }) {
                Text("Coordinate", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.labelSmall)
            }
            Tab(selected = false, onClick = { onTabSelected("accelerometerInput/${position?.id}", position!!.copy(name = positionState?.name ?: "")) }) {
                Text("Accelerometer", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.labelSmall)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Title for Joint Rotation
            Text(
                text = "Joint Rotation",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Spacer(modifier = Modifier.height(16.dp))

            // Sliders in a Row
            Row(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AxisSlider(label = "1", sliderValue = slider1Value, onValueChange = { slider1Value = it })
                AxisSlider(label = "2", sliderValue = slider2Value, onValueChange = { slider2Value = it })
                AxisSlider(label = "3", sliderValue = slider3Value, onValueChange = { slider3Value = it })
            }

            // DisplayFields for Joint Values and Coordinates in Two Columns
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Column for Joint Values
                Column(horizontalAlignment = Alignment.Start) {
                    DisplayField(label = "1", value = position?.axis1 ?: "0.0")
                    Spacer(modifier = Modifier.height(8.dp))
                    DisplayField(label = "2", value = position?.axis2 ?: "0.0")
                    Spacer(modifier = Modifier.height(8.dp))
                    DisplayField(label = "3", value = position?.axis3 ?: "0.0")
                }

                // Column for Coordinate Values
                Column(horizontalAlignment = Alignment.Start) {
                    DisplayField(label = "X", value = position?.x ?: "0.0")
                    Spacer(modifier = Modifier.height(8.dp))
                    DisplayField(label = "Y", value = position?.y ?: "0.0")
                    Spacer(modifier = Modifier.height(8.dp))
                    DisplayField(label = "Z", value = position?.z ?: "0.0")
                }
            }

            Spacer(modifier = Modifier.weight(1f))  // Spacer to push content upwards

            // Position Name Input Field
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Transparent TextField with custom underline
                BasicTextField(
                    value = positionState?.name ?: "",
                    onValueChange = { newName ->
                        positionState = positionState!!.copy(name = newName)
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Transparent)
                        .padding(vertical = 8.dp),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.surface),
                    decorationBox = { innerTextField ->
                        Column {
                            innerTextField()

                            Spacer(modifier = Modifier.height(4.dp))

                            // Custom underline
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(MaterialTheme.colorScheme.surface)
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Edit Icon Button
                IconButton(
                    onClick = { onSave(positionState!!) },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}



