package com.example.ui.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val background = Color(0xff111111)
internal val accent = Color(0xffe9327c)
internal val onBackground = Color(0xffffffff)
internal val onAccent = Color(0xffffffff)
internal val textPrimary = Color(0xffffffff)
internal val textSecondary = Color(0xff888888)

@Immutable
data class AngkorEchoesColors(
    val background: Color,
    val accent: Color,
    val onBackground: Color,
    val onAccent: Color,
    val textPrimary: Color,
    val textSecondary: Color
)

val LocalAngkorEchoesColors = staticCompositionLocalOf {
    AngkorEchoesColors(
        background = background,
        accent = accent,
        onBackground = onBackground,
        onAccent = onAccent,
        textPrimary = textPrimary,
        textSecondary = textSecondary
    )
}