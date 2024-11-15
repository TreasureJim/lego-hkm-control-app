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
import com.findingtreasure.phonependant._currentPostion
import com.findingtreasure.phonependant.model.Position
import com.findingtreasure.phonependant.model.Status
import com.findingtreasure.phonependant.ui.components.RobotStatusDisplay
import com.findingtreasure.phonependant.ui.components.AxisSlider
import com.findingtreasure.phonependant.viewmodel.JoggingViewModel
import com.findingtreasure.phonependant.viewmodel.JoggingViewModelFactory
import com.findingtreasure.phonependant.viewmodel.SettingsViewModel

@Composable
fun CoordinateInputScreen(
    position: Position?,
    onTabSelected: (String, Position) -> Unit,
    onSave: (Position) -> Unit,
    settings: SettingsViewModel
) {
    // Pass position and ProtocolHandler to the factory
    val viewModel: JoggingViewModel = viewModel(
        factory = JoggingViewModelFactory (
            initialPosition = position ?: Position(0, "", Status(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)),
            settings = settings
        )
    )

    val positionState = viewModel.positionState
    val sliderXValue = viewModel.sliderXValue
    val sliderYValue = viewModel.sliderYValue
    val sliderZValue = viewModel.sliderZValue
    val robotStatus by _currentPostion.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Tab Row
        TabRow(
            selectedTabIndex = 1,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(selected = false, onClick = {
                viewModel.updatePosition(robotStatus)
                onTabSelected("jointRotation/${positionState.value.id}", positionState.value)
            }) {
                Text("Joint", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.labelSmall)
            }
            Tab(selected = true, onClick = { /* Stay on Coordinate screen */ }) {
                Text("Coordinate", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.labelSmall)
            }
            Tab(selected = false, onClick = {
                viewModel.updatePosition(robotStatus)
                onTabSelected("accelerometerInput/${positionState.value.id}", positionState.value)
            }) {
                Text("Accelerometer", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.labelSmall)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Title for Joint Rotation
            Text(
                text = "Coordinate Offset",
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
                AxisSlider(label = "X", sliderValue = sliderXValue, onValueChange = { viewModel.setSliderValue("X", it) })
                AxisSlider(label = "Y", sliderValue = sliderYValue, onValueChange = { viewModel.setSliderValue("Y", it) })
                AxisSlider(label = "Z", sliderValue = sliderZValue, onValueChange = { viewModel.setSliderValue("Z", it) })
            }

            // DisplayFields for Joint Values and Coordinates in Two Columns
            RobotStatusDisplay(_currentPostion.collectAsState().value)

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
                    value = positionState.value.name,
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
                    onClick = {
                        viewModel.updatePosition(robotStatus)
                        onSave(positionState.value)
                              },
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
