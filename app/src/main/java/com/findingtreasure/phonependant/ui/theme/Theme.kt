package com.findingtreasure.phonependant.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ColorScheme = darkColorScheme(
	primary = Orange,
	onPrimary = White,
	secondary = Purple,
	onSecondary = Black,
	background = Teal,
	onBackground = White,
	surface = White,
	onSurface = Black,
	error = Red
)

@Composable
fun PhonePendantTheme(
	content: @Composable () -> Unit
) {
	val colorScheme = ColorScheme

	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		content = content
	)
}