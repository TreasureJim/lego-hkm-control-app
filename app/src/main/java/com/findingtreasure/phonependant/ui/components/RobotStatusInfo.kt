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

@Composable
fun RobotStatusDisplay(status: Position, addOnClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Robot Status",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Row for x, y, z coordinates
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("x: ${status.x}", fontSize = 16.sp)
            Text("y: ${status.y}", fontSize = 16.sp)
            Text("z: ${status.z}", fontSize = 16.sp)
        }

        // Row for j1, j2, j3, j4 joint angles
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("j1: ${status.j1}", fontSize = 16.sp)
            Text("j2: ${status.j2}", fontSize = 16.sp)
            Text("j3: ${status.j3}", fontSize = 16.sp)
            //Text("j4: ${status.j4}", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        IconButton(onClick = addOnClick, Modifier.fillMaxWidth(), colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0xFF2196F3))) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier.height(24.dp),
                tint = MaterialTheme.colorScheme.primary // Color of the icon
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewRobotStatusInfo() {
//    val status = Position(
//        x = 12.5, y = 9.2, z = 3.1,
//        j1 = 45.0, j2 = 30.5, j3 = 15.2, j4 = 90.0
//    )
//    RobotStatusDisplay(status = status, {})
//}