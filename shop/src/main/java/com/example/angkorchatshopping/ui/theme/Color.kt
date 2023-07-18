package com.example.angkorchatshopping.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

val background = Color(0xFFFFFFFF)
val textBlack = Color(0xFF111111)

val mainYellow = Color(0xFFFEC11A)
val lightYellow = Color(0x26FEC11A)

val red = Color(0xFFFF4040)

val darkGray = Color(0xFF929498)
val mediumGray = Color(0xFFF7F7FB)
val lightGray = Color(0xFFF7F7FB)

val gray555 = Color(0xFF555555)


@Immutable
data class AngkorShoppingColor(
    val background: Color,
    val textBlack: Color,
    val mainYellow: Color,
    val lightYellow: Color,
    val red: Color,
    val darkGray: Color,
    val mediumGray: Color,
    val lightGray: Color,
    val gray555: Color
)

val LocalAngkorShoppingColor = staticCompositionLocalOf {
    AngkorShoppingColor(
        background = background,
        textBlack = textBlack,
        mainYellow = mainYellow,
        lightYellow = lightYellow,
        red = red,
        darkGray = darkGray,
        mediumGray = mediumGray,
        lightGray = lightGray,
        gray555 = gray555
    )
}