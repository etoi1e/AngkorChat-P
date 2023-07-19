package com.example.angkorechoesprototype.ui.typography

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.angkorechoesprototype.R

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
    fontWeight = FontWeight.Bold
)

internal val bodyLarge = TextStyle(
    fontSize = 16.sp,
    lineHeight = 24.sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal
)

internal val body = TextStyle(
    fontSize = 14.sp,
    lineHeight = 24.sp,
    letterSpacing = (-0.5).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Bold
)

internal val body2 = TextStyle(
    fontSize = 12.sp,
    lineHeight = 14.sp,
    letterSpacing = (-0.5).sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal
)

internal val captionLarge = TextStyle(
    fontSize = 13.sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal
)

internal val caption = TextStyle(
    fontSize = 11.sp,
    fontFamily = dmSansFontFamily,
    fontWeight = FontWeight.Normal
)

@Immutable
data class AngkorEchoesTypography(
    val head: TextStyle,
    val body: TextStyle,
    val body2: TextStyle,
    val bodyLarge: TextStyle,
    val captionLarge: TextStyle,
    val caption: TextStyle
)

val LocalAngkorEchoesTypography = staticCompositionLocalOf {
    AngkorEchoesTypography(
        head = head,
        body = body,
        body2 = body2,
        bodyLarge = bodyLarge,
        captionLarge = captionLarge,
        caption = caption
    )
}