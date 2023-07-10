package com.example.angkorplayprototype.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.angkorplayprototype.R

val dmSansFontFamily = FontFamily(
    Font(R.font.dm_sans_regular, FontWeight.Normal),
    Font(R.font.dm_sans_medium, FontWeight.Medium),
    Font(R.font.dm_sans_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 26.sp,
        letterSpacing = (-0.52).sp
    ),
    titleMedium = TextStyle(
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        letterSpacing = (-0.36).sp
    ),
    bodyMedium = TextStyle(
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    )
)