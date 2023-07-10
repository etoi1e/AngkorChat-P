package com.example.bankangkorprototype.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.bankangkorprototype.R

val dmSansFontFamily = FontFamily(
    Font(R.font.dm_sans_regular, FontWeight.Normal),
    Font(R.font.dm_sans_medium, FontWeight.Medium),
    Font(R.font.dm_sans_bold, FontWeight.Bold)
)

val Typography = Typography(
    displayMedium = TextStyle(
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        letterSpacing = (-0.56).sp
    ),
    displaySmall = TextStyle(
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = (-0.4).sp
    ),
    bodyLarge = TextStyle(
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = (-0.32).sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = (-0.28).sp,
    ),
    bodySmall = TextStyle(
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = (-0.24).sp,
        color = Color(0xff919191)
    ),
    titleLarge = TextStyle(
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = (-0.32).sp,
    ),
    titleMedium = TextStyle(
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = (-0.3).sp
    ),
    labelMedium = TextStyle(
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
    )
)