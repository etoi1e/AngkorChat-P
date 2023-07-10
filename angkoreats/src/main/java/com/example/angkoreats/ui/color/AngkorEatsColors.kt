package com.example.angkoreats.ui.color

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


internal val background = Color(0xffffffff)
internal val searchBox = Color(0xFFF0F5F4)
internal val accent = Color(0xFF58BC36)
internal val textPrimary = Color(0xFF000000)
internal val textSecond = Color(0xFF868686)


@Immutable
data class AngkorEatsColors(
    val background : Color,
    val searchBox : Color,
    val accent : Color,
    val textPrimary : Color,
    val textSecond : Color
)

val LocalAngkorEatsColors = staticCompositionLocalOf {
    AngkorEatsColors(
        background = background,
        searchBox = searchBox,
        accent = accent,
        textPrimary = textPrimary,
        textSecond = textSecond
    )
}