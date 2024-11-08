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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.findingtreasure.phonependant.model.Position
import com.findingtreasure.phonependant.ui.helper.DisplayField
import com.findingtreasure.phonependant.ui.helper.AxisSlider
import com.findingtreasure.phonependant.viewmodel.JoggingViewModel
import com.findingtreasure.phonependant.viewmodel.JoggingViewModelFactory
import com.findingtreasure.phonependant.viewmodel.SettingsViewModel

@Composable
fun JointRotationScreen(
    position: Position?,
    onTabSelected: (String, Position) -> Unit,
    onSave: (Position) -> Unit,
    settings: SettingsViewModel
) {
    // Pass position and ProtocolHandler to the factory
    val viewModel: JoggingViewModel = viewModel(
        factory = JoggingViewModelFactory (
            initialPosition = position ?: Position(0, "", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            settings = settings,
        )
    )

    val positionState by viewModel.positionState.collectAsState()
    val slider1Value by viewModel.slider1Value.collectAsState()
    val slider2Value by viewModel.slider2Value.collectAsState()
    val slider3Value by viewModel.slider3Value.collectAsState()

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
            Tab(selected = false, onClick = { onTabSelected("coordinateInput/${positionState.id}", positionState)}) {
                Text("Coordinate", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.labelSmall)
            }
            Tab(selected = false, onClick = { onTabSelected("accelerometerInput/${positionState.id}", positionState) }) {
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
                AxisSlider(label = "J1", sliderValue = slider1Value, onValueChange = { viewModel.setSliderValue("1", it) })
                AxisSlider(label = "J2", sliderValue = slider2Value, onValueChange = { viewModel.setSliderValue("2", it) })
                AxisSlider(label = "J3", sliderValue = slider3Value, onValueChange = { viewModel.setSliderValue("3", it) })
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
                    DisplayField(label = "J1", value = positionState.j1.toString())
                    Spacer(modifier = Modifier.height(8.dp))
                    DisplayField(label = "J2", value = positionState.j2.toString())
                    Spacer(modifier = Modifier.height(8.dp))
                    DisplayField(label = "J3", value = positionState.j3.toString())
                }

                // Column for Coordinate Values
                Column(horizontalAlignment = Alignment.Start) {
                    DisplayField(label = "X", value = positionState.x.toString())
                    Spacer(modifier = Modifier.height(8.dp))
                    DisplayField(label = "Y", value = positionState.y.toString())
                    Spacer(modifier = Modifier.height(8.dp))
                    DisplayField(label = "Z", value = positionState.z.toString())
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
                    value = positionState.name,
                    onValueChange = { newName ->
                        viewModel.setName(newName)
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
                    onClick = { onSave(positionState) },
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



