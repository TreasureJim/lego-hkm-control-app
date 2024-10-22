package com.findingtreasure.phonependant.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.findingtreasure.phonependant.R
import com.findingtreasure.phonependant.viewmodel.ConnectionViewModel

@Composable
fun ConnectionScreen(
    viewModel: ConnectionViewModel,
    onConnect: () -> Unit
) {
    val ip by viewModel.ip.collectAsState()
    val port by viewModel.port.collectAsState()
    val isRememberMe by viewModel.isRememberMe.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()

    // Collect error states
    val ipError by viewModel.ipError.collectAsState()
    val portError by viewModel.portError.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Nice to see you again, lets connect!",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 40.dp, bottom = 20.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // IP Input
                Text(
                    text = "IP",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                TextField(
                    value = ip,
                    onValueChange = { viewModel.onIpChanged(it) },
                    label = { Text("Enter IP") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.onSurface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                // Show IP error if it exists
                if (ipError != null) {
                    Text(
                        text = ipError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Port Input
                Text(
                    text = "Port",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                TextField(
                    value = port,
                    onValueChange = { viewModel.onPortChanged(it) },
                    label = { Text("Enter port") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.onSurface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                // Show Port error if it exists
                if (portError != null) {
                    Text(
                        text = portError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // "Remember me" switch
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Switch(
                    checked = isRememberMe,
                    onCheckedChange = { viewModel.onRememberMeChanged(it) },
                    colors = SwitchDefaults.colors(
                        checkedIconColor = MaterialTheme.colorScheme.secondary,
                        checkedThumbColor = MaterialTheme.colorScheme.onSecondary,
                        uncheckedThumbColor = Color.Gray
                    )
                )
                Text(
                    text = "Remember me",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Connect Button
            Button(
                onClick = {
                    viewModel.connect()
                    if (isConnected) {
                        onConnect()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Connect", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.labelLarge)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Logo at the bottom
            Image(
                painter = painterResource(id = R.drawable.cognibotics_logo),  // Assuming you have a logo in resources
                contentDescription = "Cognibotics Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
