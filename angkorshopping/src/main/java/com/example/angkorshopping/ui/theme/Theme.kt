package com.example.angkorshopping.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AngkorShoppingTheme(
    content: @Composable () -> Unit
) {
    val angkorShoppingColors = AngkorShoppingColors(
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

    val angkorShoppingType = AngkorShoppingType(
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

    CompositionLocalProvider(
        LocalAngkorShoppingColors provides angkorShoppingColors,
        LocalAngkorShoppingType provides angkorShoppingType,
        content = content
    )
}

object AngkorShoppingTheme {
    val colors: AngkorShoppingColors
        @Composable get() = LocalAngkorShoppingColors.current

    val typography: AngkorShoppingType
        @Composable get() = LocalAngkorShoppingType.current
}




