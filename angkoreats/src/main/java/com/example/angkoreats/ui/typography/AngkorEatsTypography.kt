package com.example.angkoreats.ui.typography

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.angkoreats.R

internal val dmSansFontFamily = FontFamily(
    Font(R.font.dm_sans_regular, FontWeight.Normal),
    Font(R.font.dm_sans_medium, FontWeight.Medium),
    Font(R.font.dm_sans_bold, FontWeight.Bold)
)

internal val head = TextStyle(
    fontSize = 18.sp,
    lineHeight = 24.sp,
    letterSpacing = (-0.5).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal
)

internal val body = TextStyle(
    fontSize = 16.sp,
    lineHeight = 21.sp,
    letterSpacing = (-0.5).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Bold
)

internal val body2 = TextStyle(
    fontSize = 14.sp,
    lineHeight = 18.sp,
    letterSpacing = (-0.5).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium
)

internal val body3 = TextStyle(
    fontSize = 12.sp,
    lineHeight = 15.sp,
    letterSpacing = (-0.5).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium
)

internal val body4 = TextStyle(
    fontSize = 12.sp,
    lineHeight = 15.sp,
    letterSpacing = (-0.5).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Medium
)

internal val search = TextStyle(
    fontSize = 15.sp,
    lineHeight = 20.sp,
    letterSpacing = (-0.5).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal
)

internal val caption = TextStyle(
    fontSize = 12.sp,
    lineHeight = 16.sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Bold
)

@Immutable
data class AngkorEatsTypography(
    val head: TextStyle,
    val body: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,
    val body4: TextStyle,
    val search: TextStyle,
    val caption: TextStyle
)

val LocalAngkorEatsTypography = staticCompositionLocalOf {
    AngkorEatsTypography(
        head = head,
        body = body,
        body2 = body2,
        body3 = body3,
        body4 = body4,
        search = search,
        caption = caption
    )
}