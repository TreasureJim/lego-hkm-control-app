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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.findingtreasure.phonependant.model.Position

@Composable
fun AccelerometerInputScreen(
    position: Position?,
    onTabSelected: (String, Position) -> Unit,
    onSave: (Position) -> Unit
) {
    var positionState by remember { mutableStateOf(position) }

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
            Tab(selected = false, onClick = { onTabSelected("jointRotation/${position?.id}", position!!.copy(name = positionState?.name ?: ""))  }) {
                Text("Joint", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.labelSmall)
            }
            Tab(selected = false, onClick = { onTabSelected("coordinateInput/${position?.id}", position!!.copy(name = positionState?.name ?: "")) }) {
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
                CoordinateDisplayFields(
                    label = "X",
                    value = positionState?.x.toString()
                )
                CoordinateDisplayFields(
                    label = "Y",
                    value = positionState?.y.toString()
                )
                CoordinateDisplayFields(
                    label = "Z",
                    value = positionState?.z.toString()
                )
                CoordinateDisplayFields(
                    label = "1",
                    value = positionState?.j1.toString()
                )
                CoordinateDisplayFields(
                    label = "2",
                    value = positionState?.j2.toString()
                )
                CoordinateDisplayFields(
                    label = "3",
                    value = positionState?.j3.toString()
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = { /* Handle save button click */ },
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                ),
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp)
            ) {
                Text("Track", style = MaterialTheme.typography.titleSmall)
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

@Composable
fun CoordinateDisplayFields(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Circular label for X, Y, Z
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

        Spacer(modifier = Modifier.width(8.dp))

        // Display coordinate value
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier
                .width(196.dp)
                .padding(vertical = 8.dp)
        )
    }
}
