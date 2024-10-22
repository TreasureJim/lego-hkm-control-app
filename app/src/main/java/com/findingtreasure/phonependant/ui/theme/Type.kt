package com.findingtreasure.phonependant.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.findingtreasure.phonependant.R

// Define the Poppins font family
val Poppins = FontFamily(
	Font(R.font.poppins_regular, FontWeight.Normal),
	Font(R.font.poppins_semibold, FontWeight.SemiBold),
	Font(R.font.poppins_bold, FontWeight.Bold)
)

// Define the typography styles
val Typography = Typography(
	titleLarge = TextStyle(
		fontFamily = Poppins,
		fontWeight = FontWeight.SemiBold,
		fontSize = 40.sp
	),
	titleSmall = TextStyle(
		fontFamily = Poppins,
		fontWeight = FontWeight.SemiBold,
		fontSize = 25.sp
	),
	bodyLarge = TextStyle(
		fontFamily = Poppins,
		fontWeight = FontWeight.Normal,
		fontSize = 20.sp
	),
	labelLarge = TextStyle(
		fontFamily = Poppins,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp
	),
	labelSmall = TextStyle(
		fontFamily = Poppins,
		fontWeight = FontWeight.Bold,
		fontSize = 14.sp
	),
	bodySmall = TextStyle(
		fontFamily = Poppins,
		fontWeight = FontWeight.Normal,
		fontSize = 14.sp
	)
)
