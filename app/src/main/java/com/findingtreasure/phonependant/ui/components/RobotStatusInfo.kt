package com.findingtreasure.phonependant.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.findingtreasure.phonependant.model.Position
import com.findingtreasure.phonependant.ui.helper.DisplayField

fun formatValue(value: Double, decimalPlaces: Int = 2): String {
    return String.format("%.${decimalPlaces}E0", value)
}

@Composable
fun RobotStatusDisplay(positionState: Position) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Column for Joint Values
            Column(horizontalAlignment = Alignment.Start) {
                DisplayField(label = "1", value = formatValue(positionState.j1))
                Spacer(modifier = Modifier.height(8.dp))
                DisplayField(label = "2", value = formatValue(positionState.j2))
                Spacer(modifier = Modifier.height(8.dp))
                DisplayField(label = "3", value = formatValue(positionState.j3))
            }

            Spacer(modifier = Modifier.height(16.dp))  // Extra space between columns

            // Column for Coordinate Values
            Column(horizontalAlignment = Alignment.Start) {
                DisplayField(label = "X", value = formatValue(positionState.x))
                Spacer(modifier = Modifier.height(8.dp))
                DisplayField(label = "Y", value = formatValue(positionState.y))
                Spacer(modifier = Modifier.height(8.dp))
                DisplayField(label = "Z", value = formatValue(positionState.z))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRobotStatusInfo() {
    val status = Position(
        1, "Example Position",
        x = 12.5, y = 9.2, z = 3.1,
        j1 = 45.0, j2 = 30.5, j3 = 15.2
    )
    RobotStatusDisplay(status)
}