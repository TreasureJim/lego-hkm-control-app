package com.findingtreasure.phonependant.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.findingtreasure.phonependant.model.Status

@Composable
fun RobotStatusDisplay(robotStatus: Status) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 16.dp, 8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Column for Joint Values
            Column(horizontalAlignment = Alignment.Start) {
                DisplayField(label = "J1", value = robotStatus.j1)
                Spacer(modifier = Modifier.height(8.dp))
                DisplayField(label = "J2", value = robotStatus.j2)
                Spacer(modifier = Modifier.height(8.dp))
                DisplayField(label = "J3", value = robotStatus.j3)
            }

            Spacer(modifier = Modifier.height(16.dp))  // Extra space between columns

            // Column for Coordinate Values
            Column(horizontalAlignment = Alignment.Start) {
                DisplayField(label = "X", value = robotStatus.x)
                Spacer(modifier = Modifier.height(8.dp))
                DisplayField(label = "Y", value = robotStatus.y)
                Spacer(modifier = Modifier.height(8.dp))
                DisplayField(label = "Z", value = robotStatus.z)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRobotStatusInfo() {
    val status = Status(
        x = 12.5, y = 9.2, z = 3.1,
        j1 = 45.0, j2 = 30.5, j3 = 15.2, j4 = 0.0
    )
    RobotStatusDisplay(status)
}