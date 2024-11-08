package com.findingtreasure.phonependant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.findingtreasure.phonependant.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit
) {
    val jointSensitivity by viewModel.jointsensitivity.collectAsState()
    val coordSensitivity by viewModel.coordsensitivity.collectAsState()
    val commandSendHertz by viewModel.commandSendHertz.collectAsState()

    var localJointSensitivity by remember { mutableStateOf(jointSensitivity) }
    var localCoordSensitivity by remember { mutableStateOf(coordSensitivity) }
    var localCommandSendHertz by remember { mutableStateOf(commandSendHertz.toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
    ) {
        // Top bar with "Settings" title
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp)
        )

        // Joint Sensitivity Slider
        Text(
            text = "Joint Sensitivity",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Slider(
            value = localJointSensitivity,
            onValueChange = { localJointSensitivity = it },
            valueRange = 0.01f..0.1f,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Coordinate Sensitivity Slider
        Text(
            text = "Coordinate Sensitivity",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Slider(
            value = localCoordSensitivity,
            onValueChange = { localCoordSensitivity = it },
            valueRange = 0.01f..1f,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Command Send Hertz Input
        Text(
            text = "Command Send Hertz",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        BasicTextField(
            value = localCommandSendHertz,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) localCommandSendHertz = newValue
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimary),
            decorationBox = { innerTextField ->
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    Box(
                        Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        innerTextField()
                    }
                }
            }
        )


        Spacer(modifier = Modifier.weight(1f))

        // Save Button
        Button(
            onClick = {
                val commandHertz = localCommandSendHertz.toIntOrNull() ?: commandSendHertz
                viewModel.saveSettings(localJointSensitivity, localCoordSensitivity, commandHertz)
                onBack()
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Save Settings", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}
