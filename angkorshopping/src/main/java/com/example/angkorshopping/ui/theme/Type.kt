package com.example.angkorshopping.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.angkorshopping.R

internal val dmSansFontFamily = FontFamily(
    Font(R.font.dm_sans_bold, FontWeight.Bold),
    Font(R.font.dm_sans_medium, FontWeight.Medium),
    Font(R.font.dm_sans_regular, FontWeight.Normal)
)

internal val sansB24 = TextStyle(
    fontSize = 24.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.48).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Bold
)

internal val sansM24 = TextStyle(
    fontSize = 24.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.48).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium
)

internal val sansB22 = TextStyle(
    fontSize = 22.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.44).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Bold
)

internal val sansM19 = TextStyle(
    fontSize = 19.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.38).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium
)

internal val sansM18 = TextStyle(
    fontSize = 18.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.36).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium
)

internal val sansB17 = TextStyle(
    fontSize = 17.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.34).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Bold
)

internal val sansM17 = TextStyle(
    fontSize = 17.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.34).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium
)

internal val sansR16 = TextStyle(
    fontSize = 16.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.32).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal
)

internal val sansM15 = TextStyle(
    fontSize = 15.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.3).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium
)

internal val sansR15 = TextStyle(
    fontSize = 15.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.3).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal
)

internal val sansM14 = TextStyle(
    fontSize = 14.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.28).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium
)

internal val sansR14 = TextStyle(
    fontSize = 14.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.28).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal
)

internal val sansR13 = TextStyle(
    fontSize = 11.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.26).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal
)

internal val sansM12 = TextStyle(
    fontSize = 12.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.24).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium
)

internal val sansR12 = TextStyle(
    fontSize = 12.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.24).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal
)

internal val sansR11 = TextStyle(
    fontSize = 11.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.22).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal
)

internal val sansR10 = TextStyle(
    fontSize = 10.sp,
    lineHeight = 11.sp,
    letterSpacing = (-0.2).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal
)

internal val sansM9 = TextStyle(
    fontSize = 9.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.18).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium
)

@Immutable
data class AngkorShoppingType(
    val sansB24: TextStyle,
    val sansM24: TextStyle,
    val sansB22: TextStyle,
    val sansM19: TextStyle,
    val sansM18: TextStyle,
    val sansB17: TextStyle,
    val sansM17: TextStyle,
    val sansR16: TextStyle,
    val sansM15: TextStyle,
    val sansR15: TextStyle,
    val sansM14: TextStyle,
    val sansR14: TextStyle,
    val sansR13: TextStyle,
    val sansM12: TextStyle,
    val sansR12: TextStyle,
    val sansR11: TextStyle,
    val sansR10: TextStyle,
    val sansM9: TextStyle
)

val LocalAngkorShoppingType = staticCompositionLocalOf {
    AngkorShoppingType(
        sansB24 = sansB24,
        sansM24 = sansM24,
        sansB22 = sansB22,
        sansM19 = sansM19,
        sansM18 = sansM18,
        sansB17 = sansB17,
        sansM17 = sansM17,
        sansR16 = sansR16,
        sansM15 = sansM15,
        sansR15 = sansR15,
        sansM14 = sansM14,
        sansR14 = sansR14,
        sansR13 = sansR13,
        sansM12 = sansM12,
        sansR12 = sansR12,
        sansR11 = sansR11,
        sansR10 = sansR10,
        sansM9 = sansM9
    )
}