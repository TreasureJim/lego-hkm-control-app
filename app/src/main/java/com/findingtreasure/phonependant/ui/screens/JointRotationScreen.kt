package com.findingtreasure.phonependant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.findingtreasure.comms.NetworkManager
import com.findingtreasure.phonependant.SliderSnapRelease
import com.findingtreasure.phonependant.model.Position
import com.findingtreasure.phonependant.ui.components.RobotStatusDisplay
import com.findingtreasure.phonependant.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay
import java.util.UUID

@Composable
fun JointRotationScreen(
    onSavePosition: () -> Unit,
    settings: SettingsViewModel
) {
    val slider1 = remember { mutableStateOf(0.0) }
    val slider2 = remember { mutableStateOf(0.0) }
    val slider3 = remember { mutableStateOf(0.0) }

    LaunchedEffect(Unit) {
        while (true) {
            if (slider1.value.toInt() == 0 && slider2.value.toInt() == 0 && slider3.value.toInt() == 0)
                continue

            val sliders = doubleArrayOf(
                slider1.value,
                slider2.value,
                slider3.value
            ).map { x -> x.toDouble() * settings.sensitivity.value }
            NetworkManager.sendJogJoints(UUID.randomUUID(), sliders[0], sliders[1], sliders[2], 0.0)
            delay((1 / settings.commandSendHertz.value * 1000).toLong())
        }
    }

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
                Text(
                    "Joint",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.labelSmall
                )
            }
//            Tab(selected = false, onClick = { onTabSelected("coordinateInput/${position?.id}", position!!.copy(name = positionName)) }) {
//                Text("Coordinate", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.labelSmall)
//            }
//            Tab(selected = false, onClick = { onTabSelected("accelerometerInput/${position?.id}", position!!.copy(name = positionName)) }) {
//                Text("Accelerometer", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.labelSmall)
//            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
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

            // Axis Labels and Sliders
            Text(
                text = "Axis",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sliders in a Row
            Row(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AxisSlider("1", slider1)
                AxisSlider("2", slider2)
                AxisSlider("3", slider3)
            }

            Spacer(modifier = Modifier.weight(1f))

            RobotStatusDisplay(Global, onSavePosition)
        }
    }
}

@Composable
fun AxisSlider(label: String, sliderValue: MutableState<Float>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
    ) {
        // Label Circle
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Slider
        SliderSnapRelease(sliderValue, -100f..100f, 0f)
    }
}
