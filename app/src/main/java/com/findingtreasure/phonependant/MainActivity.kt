package com.findingtreasure.phonependant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.findingtreasure.phonependant.ui.theme.PhonePendantTheme
import com.findingtreasure.phonependant.viewmodel.ConnectionViewModel
import com.findingtreasure.phonependant.ui.screens.ConnectionScreen

class MainActivity : ComponentActivity() {
	private val connectionViewModel: ConnectionViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			PhonePendantTheme {
				ConnectionScreen(
					viewModel = connectionViewModel,
					onConnect = {

					}
				)
			}
		}
	}
}