package com.findingtreasure.phonependant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.findingtreasure.phonependant.datastore.ConnectionDataStore
import com.findingtreasure.phonependant.model.Position
import com.findingtreasure.phonependant.ui.screens.ConnectionScreen
import com.findingtreasure.phonependant.ui.screens.PositionListScreen
import com.findingtreasure.phonependant.ui.theme.PhonePendantTheme
import com.findingtreasure.phonependant.viewmodel.ConnectionViewModel

/**
Hello my name is Heeh
**/

class MainActivity : ComponentActivity() {
	private lateinit var dataStore: ConnectionDataStore

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		dataStore = ConnectionDataStore(applicationContext)

		setContent {
			PhonePendantTheme {
				AppContent()
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
}
