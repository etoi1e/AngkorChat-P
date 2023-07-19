package com.example.angkorchatshopping.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.angkorchatshopping.R

val dmSansFontFamily = FontFamily(
    Font(R.font.dm_sans_regular, FontWeight.Normal),
    Font(R.font.dm_sans_medium, FontWeight.Medium),
    Font(R.font.dm_sans_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with

val sansB24 = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.48).sp
)

val sansM24 = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 24.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.48).sp
)

val sansM18 = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.36).sp
)

val sansB17 = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 17.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.34).sp
)

val sansM16 = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.32).sp
)

val sansM15 = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 15.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.3).sp
)

val sansR15 = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 15.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.3).sp
)

val sansM14 = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    letterSpacing = (-0.35).sp
)

val sansR14 = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 18.sp,
    letterSpacing = (-0.28).sp
)

val sansR13 = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.26).sp
)

val sansM12 = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.24).sp
)

val sansR12 = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 18.sp,
    letterSpacing = (-0.24).sp
)

val sansR11 = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 11.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.22).sp
)

val sansM9 = TextStyle(
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 9.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.18).sp
)


@Immutable
data class AngkorShoppingType(
    val sansB24: TextStyle,
    val sansM24: TextStyle,
    val sansM18: TextStyle,
    val sansB17: TextStyle,
    val sansM16: TextStyle,
    val sansM15: TextStyle,
    val sansR15: TextStyle,
    val sansM14: TextStyle,
    val sansR14: TextStyle,
    val sansR13: TextStyle,
    val sansM12: TextStyle,
    val sansR12: TextStyle,
    val sansR11: TextStyle,
    val sansM9: TextStyle
)

val LocalAngkorShoppingType = staticCompositionLocalOf {
    AngkorShoppingType(
        sansB24 = sansB24,
        sansM24 = sansM24,
        sansM18 = sansM18,
        sansB17 = sansB17,
        sansM16 = sansM16,
        sansM15 = sansM15,
        sansR15 = sansR15,
        sansM14 = sansM14,
        sansR14 = sansR14,
        sansR13 = sansR13,
        sansM12 = sansM12,
        sansR12 = sansR12,
        sansR11 = sansR11,
        sansM9 = sansM9
    )
}