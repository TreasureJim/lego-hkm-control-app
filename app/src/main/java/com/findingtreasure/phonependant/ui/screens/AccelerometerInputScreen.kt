package com.findingtreasure.phonependant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.findingtreasure.phonependant._currentPostion
import com.findingtreasure.phonependant.model.Position
import com.findingtreasure.phonependant.model.Status
import com.findingtreasure.phonependant.sensor.Accelerometer
import com.findingtreasure.phonependant.ui.components.DisplayField
import com.findingtreasure.phonependant.viewmodel.JoggingViewModel
import com.findingtreasure.phonependant.viewmodel.JoggingViewModelFactory
import com.findingtreasure.phonependant.viewmodel.SettingsViewModel

@Composable
fun AccelerometerInputScreen(
    position: Position?,
    onTabSelected: (String, Position) -> Unit,
    onSave: (Position) -> Unit,
    settings: SettingsViewModel
) {
    // Initialize Accelerometer
    val context = LocalContext.current
    val accelerometer = remember { Accelerometer(context) }

    // Pass position and ProtocolHandler to the factory
    val viewModel: JoggingViewModel = viewModel(
        factory = JoggingViewModelFactory (
            initialPosition = position ?: Position(0, "", Status(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)),
            settings = settings,
        )
    )

    val positionState = viewModel.positionState
    val robotStatus by _currentPostion.collectAsState()
    var isTracking by remember { mutableStateOf(false) }

    // Callback to handle accelerometer data
    LaunchedEffect(isTracking) {
        if (isTracking) {
            accelerometer.onAccelerometerDataChanged = { data ->
                // Update ViewModel sliders based on accelerometer data
                viewModel.setSliderValue("X", data[0])
                viewModel.setSliderValue("Y", data[1])
                viewModel.setSliderValue("Z", data[2])
            }
            accelerometer.startListening()
        } else {
            accelerometer.stopListening()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Tab Row
        TabRow(
            selectedTabIndex = 2,
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
            Tab(selected = false, onClick = {
                viewModel.updatePosition(robotStatus)
                onTabSelected("coordinateInput/${positionState.value.id}", positionState.value)
            }) {
                Text("Coordinate", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.labelSmall)
            }
            Tab(selected = true, onClick = { /* Stay on Accelerometer screen */ }) {
                Text("Accelerometer", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.labelSmall)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title for Coordinate Mode
            Text(
                text = "Accelerometer input",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(96.dp))

            // Center-aligned Coordinate Display
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DisplayField("J1", robotStatus.j1)
                DisplayField("J2", robotStatus.j2)
                DisplayField("J3", robotStatus.j3)
                DisplayField("X", robotStatus.x)
                DisplayField("Y", robotStatus.y)
                DisplayField("Z", robotStatus.z)
            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = { isTracking = !isTracking },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp)
            ) {
                Text(if (isTracking) "Stop" else "Track", style = MaterialTheme.typography.titleSmall)
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