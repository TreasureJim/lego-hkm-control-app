package com.findingtreasure.phonependant

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.findingtreasure.phonependant.datastore.ConnectionDataStore
import com.findingtreasure.phonependant.model.Position
import com.findingtreasure.phonependant.sensor.Accelerometer
import com.findingtreasure.phonependant.ui.screens.ConnectionScreen
import com.findingtreasure.phonependant.ui.screens.PositionListScreen
import com.findingtreasure.phonependant.ui.theme.PhonePendantTheme
import com.findingtreasure.phonependant.viewmodel.ConnectionViewModel

class MainActivity : ComponentActivity() {
	private lateinit var dataStore: ConnectionDataStore

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		dataStore = ConnectionDataStore(applicationContext)

		setContent {
			PhonePendantTheme {
				AccelerometerTesting(this)
				//AppContent()
			}
		}
	}

	@Composable
	fun AppContent() {
		var currentScreen by remember { mutableStateOf("connection") }
		val viewModel = remember { ConnectionViewModel(dataStore) }
		val positions = remember { mutableStateListOf<Position>() }

		if (currentScreen == "connection") {
			ConnectionScreen(
				viewModel = viewModel,
				onConnect = {
					currentScreen = "positionList"
				}
			)
		} else if (currentScreen == "positionList") {
			PositionListScreen(
				positionList = positions,
				onEditPosition = { /* Handle edit */ },
				onAddNewPosition = {
					positions.add(
						Position(
							id = positions.size + 1,
							name = "Position ${positions.size + 1}",
							x = "12345678",
							y = "12345678",
							z = "12345678",
							axis1 = "12345678",
							axis2 = "12345678",
							axis3 = "12345678"
						)
					)
				},
				onLogout = {
					viewModel.logout()
					currentScreen = "connection"
				}
			)
		}
	}

	@Composable
	fun AccelerometerTesting(context: Context) {
		// Create a state to hold accelerometer values
		var xValue by remember { mutableFloatStateOf(0f) }
		var yValue by remember { mutableFloatStateOf(0f) }
		var zValue by remember { mutableFloatStateOf(0f) }

		// Create an instance of the accelerometer
		val accelerometer = remember { Accelerometer(context) }

		// Use LaunchedEffect to start and stop listening to accelerometer values
		DisposableEffect(Unit) {
			accelerometer.startListening()

			// Set the callback for the accelerometer data
			accelerometer.onAccelerometerDataChanged = { values ->
				xValue = values[0]
				yValue = values[1]
				zValue = values[2]
			}

			// Cleanup on exit
			onDispose {
				accelerometer.stopListening()
			}
		}

		// Display accelerometer values on the screen
		Column {
			Text(text = "X-axis: $xValue")
			Text(text = "Y-axis: $yValue")
			Text(text = "Z-axis: $zValue")
		}
	}
}
