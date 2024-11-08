package com.findingtreasure.phonependant.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.findingtreasure.phonependant.model.Position
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.text.font.FontWeight
import com.findingtreasure.phonependant.ui.helper.TrackPath
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PositionListScreen(
    positionList: List<Position>,
    onEditPosition: (Position) -> Unit,
    onAddNewPosition: () -> Unit,
    onTrackPath: () -> Unit,
    onLogout: () -> Unit,
    onSettings: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    // Wrap everything inside a Column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // LazyColumn for positions
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f) // This makes the LazyColumn take up available space
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    // Title "Positions" centered horizontally
                    Text(
                        text = "Positions",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(top = 24.dp, bottom = 16.dp),
                        textAlign = TextAlign.Center
                    )

                    // Logout button aligned to the top-right corner
                    IconButton(
                        onClick = onLogout,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 30.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Log out",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }

                    IconButton(
                        onClick = onSettings,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 30.dp, start = 5.dp) // Add padding to give space from the left
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings, // You can choose any settings icon you want
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

            // List of positions
            items(positionList.size) { index ->
                val position = positionList[index]
                var expanded by remember { mutableStateOf(false) }
                PositionItem(
                    position = position,
                    expanded = expanded,
                    onExpandClick = { expanded = !expanded },
                    onEditClick = { onEditPosition(position) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                // Button to add new position
                Button(
                    onClick = onAddNewPosition,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "+ New position",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        // Button fixed at the bottom of the screen
        Button(
            onClick = { coroutineScope.launch {
                TrackPath(positionList)
            }},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Track Path",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}



@Composable
fun PositionItem(
    position: Position,
    expanded: Boolean,
    onExpandClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpandClick() }
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp, 0.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Arrow to toggle expand/collapse
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.clickable { onExpandClick() }
                )

                Text(
                    text = position.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                // Edit button
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))

                // Expanded details: Coordinates and Axis (formatted in two columns)
                Row(
                    modifier = Modifier.padding(16.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        CoordinateDisplay(label = "X", value = position.x.toString())
                        Spacer(modifier = Modifier.height(8.dp))
                        CoordinateDisplay(label = "Y", value = position.y.toString())
                        Spacer(modifier = Modifier.height(8.dp))
                        CoordinateDisplay(label = "Z", value = position.z.toString())
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        CoordinateDisplay(label = "J1", value = position.j1.toString())
                        Spacer(modifier = Modifier.height(8.dp))
                        CoordinateDisplay(label = "J2", value = position.j2.toString())
                        Spacer(modifier = Modifier.height(8.dp))
                        CoordinateDisplay(label = "J3", value = position.j3.toString())
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun CoordinateDisplay(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circle label
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        // Display value
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}