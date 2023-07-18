package com.example.angkorchatshopping.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AngkorShoppingTheme(
    content: @Composable () -> Unit
) {
    val angkorEchoesColors = AngkorShoppingColor(
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

    val angkorEchoesTypography = AngkorShoppingType(
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

    CompositionLocalProvider(
        LocalAngkorShoppingColor provides angkorEchoesColors,
        LocalAngkorShoppingType provides angkorEchoesTypography,
        content = content
    )
}

object AngkorEchoesTheme {
    val colors: AngkorShoppingColor
        @Composable get() = LocalAngkorShoppingColor.current

    val typography: AngkorShoppingType
        @Composable get() = LocalAngkorShoppingType.current
}