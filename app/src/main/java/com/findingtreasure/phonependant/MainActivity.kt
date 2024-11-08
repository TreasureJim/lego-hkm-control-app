package com.findingtreasure.phonependant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.findingtreasure.phonependant.datastore.ConnectionDataStore
import com.findingtreasure.phonependant.model.Position
import com.findingtreasure.phonependant.ui.screens.ConnectionScreen
import com.findingtreasure.phonependant.ui.screens.JointRotationScreen
import com.findingtreasure.phonependant.ui.screens.PositionListScreen
import com.findingtreasure.phonependant.ui.theme.PhonePendantTheme
import com.findingtreasure.phonependant.viewmodel.ConnectionViewModel
import kotlinx.coroutines.launch
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import com.findingtreasure.phonependant.datastore.SettingsDataStore
import com.findingtreasure.phonependant.ui.screens.AccelerometerInputScreen
import com.findingtreasure.phonependant.ui.screens.CoordinateInputScreen
import com.findingtreasure.phonependant.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			PhonePendantTheme {
				MainAppNavigation()
			}
		}
	}
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainAppNavigation() {
	val navController = rememberAnimatedNavController()
	val context = LocalContext.current
	val scope = rememberCoroutineScope()

	val connectionDataStore = remember { ConnectionDataStore(context) }
	val connectionViewModel = remember { ConnectionViewModel(connectionDataStore) }

	val settingsDataStore = remember { SettingsDataStore(context) }
	val settingsViewModel = remember { SettingsViewModel(settingsDataStore) }

	val positions = remember { mutableStateListOf<Position>() }


	AnimatedNavHost(
		navController = navController,
		startDestination = "connection",
		enterTransition = { fadeIn(animationSpec = tween(0)) },    // No animation on enter
		exitTransition = { fadeOut(animationSpec = tween(0)) },    // No animation on exit
		popEnterTransition = { fadeIn(animationSpec = tween(0)) }, // No animation on pop enter
		popExitTransition = { fadeOut(animationSpec = tween(0)) }  // No animation on pop exit
	) {
		// Connection Screen
		composable("connection") {
			ConnectionScreen(
				viewModel = connectionViewModel,
				onConnect = {
					navController.navigate("positionList")
				}
			)
		}

		// Position List Screen
		composable("positionList") {
			PositionListScreen(
				positionList = positions,
				onEditPosition = { position ->
					navController.navigate("jointRotation/${position.id}")
				},
				onAddNewPosition = {
					/* TO IMPLEMENT: should copy current position of robot */
					val newPosition = Position(
						id = positions.size + 1,
						name = "Position ${positions.size + 1}",
						x = 0.0,
						y = 0.0,
						z = 0.0,
						j1 = 0.0,
						j2 = 0.0,
						j3 = 0.0
					)
					positions.add(newPosition)
					navController.navigate("jointRotation/${newPosition.id}")
				},
				onLogout = {
					scope.launch { connectionViewModel.logout() }
					navController.navigate("connection") {
						popUpTo("connection") { inclusive = true }
					}
				}
			)
		}

		// Joint Rotation Screen
		composable(
			route = "jointRotation/{positionId}",
			arguments = listOf(
				navArgument("positionId") { type = NavType.IntType }
			)
		) { backStackEntry ->
			val positionId = backStackEntry.arguments?.getInt("positionId") ?: 0

			JointRotationScreen(
				position = positions.find { it.id == positionId },
				onSave = { updatedPosition ->
					val index = positions.indexOfFirst { it.id == updatedPosition.id }
					if (index != -1) {
						positions[index] = updatedPosition
					}
					navController.popBackStack()
				},
				onTabSelected = { screen, updatedPosition ->
					// Update the position in the list
					val index = positions.indexOfFirst { it.id == updatedPosition.id }
					if (index != -1) {
						positions[index] = updatedPosition
					}
					navController.navigate(screen) {
						popUpTo("jointRotation/$positionId") { inclusive = true }
					}
				},
				settings = settingsViewModel
			)
		}

		// Coordinate Input Screen
		composable(
			route = "coordinateInput/{positionId}",
			arguments = listOf(
				navArgument("positionId") { type = NavType.IntType }
			)
		) { backStackEntry ->
			val positionId = backStackEntry.arguments?.getInt("positionId") ?: 0

			CoordinateInputScreen (
				position = positions.find { it.id == positionId },
				onSave = { updatedPosition ->
					val index = positions.indexOfFirst { it.id == updatedPosition.id }
					if (index != -1) {
						positions[index] = updatedPosition
					}
					navController.popBackStack()
				},
				onTabSelected = { screen, updatedPosition ->
					// Update the position in the list
					val index = positions.indexOfFirst { it.id == updatedPosition.id }
					if (index != -1) {
						positions[index] = updatedPosition
					}
					navController.navigate(screen) {
						popUpTo("coordinateInput/$positionId") { inclusive = true }
					}
				},
				settings = settingsViewModel
			)
		}

		// Accelerometer Input Screen
		composable(
			route = "accelerometerInput/{positionId}",
			arguments = listOf(
				navArgument("positionId") { type = NavType.IntType }
			)
		) { backStackEntry ->
			val positionId = backStackEntry.arguments?.getInt("positionId") ?: 0

			AccelerometerInputScreen (
				position = positions.find { it.id == positionId },
				onSave = { updatedPosition ->
					val index = positions.indexOfFirst { it.id == updatedPosition.id }
					if (index != -1) {
						positions[index] = updatedPosition
					}
					navController.popBackStack()
				},
				onTabSelected = { screen, updatedPosition ->
					// Update the position in the list
					val index = positions.indexOfFirst { it.id == updatedPosition.id }
					if (index != -1) {
						positions[index] = updatedPosition
					}
					navController.navigate(screen) {
						popUpTo("accelerometerInput/$positionId") { inclusive = true }
					}
				}
			)
		}
	}
}
