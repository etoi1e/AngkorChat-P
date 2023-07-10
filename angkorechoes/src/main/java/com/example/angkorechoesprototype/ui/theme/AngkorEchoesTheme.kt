package com.example.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.ui.color.AngkorEchoesColors
import com.example.ui.color.LocalAngkorEchoesColors
import com.example.ui.color.accent
import com.example.ui.color.background
import com.example.ui.color.onAccent
import com.example.ui.color.onBackground
import com.example.ui.color.textPrimary
import com.example.ui.color.textSecondary
import com.example.ui.typography.AngkorEchoesTypography
import com.example.ui.typography.LocalAngkorEchoesTypography
import com.example.ui.typography.body
import com.example.ui.typography.body2
import com.example.ui.typography.caption
import com.example.ui.typography.head

@Composable
fun AngkorEchoesTheme(
    content: @Composable () -> Unit
) {
    val angkorEchoesColors = AngkorEchoesColors(
        background = background,
        accent = accent,
        onBackground = onBackground,
        onAccent = onAccent,
        textPrimary = textPrimary,
        textSecondary = textSecondary
    )
    
    val angkorEchoesTypography = AngkorEchoesTypography(
        head = head,
        body = body,
        body2 = body2,
        caption = caption
    )
    
    CompositionLocalProvider(
        LocalAngkorEchoesColors provides angkorEchoesColors,
        LocalAngkorEchoesTypography provides angkorEchoesTypography,
        content = content
    )
}

object AngkorEchoesTheme {
    val colors: AngkorEchoesColors
        @Composable get() = LocalAngkorEchoesColors.current

    val typography: AngkorEchoesTypography
        @Composable get() = LocalAngkorEchoesTypography.current
}