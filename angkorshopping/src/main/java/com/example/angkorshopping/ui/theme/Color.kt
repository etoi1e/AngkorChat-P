package com.example.angkorshopping.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val MainYellow = Color(0xFFFEC11A)
val LightYellow = Color(0x26FEC11A)
val Background = Color(0xFFFFFFFF)

val TextBlack = Color(0xFF111111)
val TextGray = Color(0xFF929498)

val DarkGray = Color(0xFFC4C4C4)
val LightGray = Color(0xFFF7F7FB)

val ButtonGray = Color(0xFF555555)
val red = Color(0xFFFF4040)


@Immutable
data class AngkorShoppingColors(
    val Background: Color,
    val MainYellow: Color,
    val LightYellow: Color,
    val TextBlack: Color,
    val TextGray: Color,
    val DarkGray: Color,
    val LightGray:  Color,
    val ButtonGray:  Color,
    val red: Color


)

val LocalAngkorShoppingColors = staticCompositionLocalOf {
    AngkorShoppingColors(
        Background = Background,
        MainYellow = MainYellow,
        LightYellow = LightYellow,
        TextBlack = TextBlack,
        TextGray = TextGray,
        DarkGray = DarkGray,
        LightGray = LightGray,
        ButtonGray = ButtonGray,
        red = red

    )
}