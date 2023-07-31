package com.digitalangkor.mobility.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.digitalangkor.mobility.R

val kantumruyProFontFamily = FontFamily(
    Font(resId = R.font.kantumruy_pro_regular, weight = FontWeight.Normal),
    Font(resId = R.font.kantumruy_pro_semibold, weight = FontWeight.SemiBold),
    Font(resId = R.font.kantumruypro_medium, weight = FontWeight.Medium)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = kantumruyProFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = kantumruyProFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = kantumruyProFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    bodySmall = TextStyle(
        fontFamily = kantumruyProFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
)