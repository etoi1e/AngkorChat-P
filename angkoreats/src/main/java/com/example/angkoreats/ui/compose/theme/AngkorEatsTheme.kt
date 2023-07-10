package com.example.angkoreats.ui.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.angkoreats.ui.color.AngkorEatsColors
import com.example.angkoreats.ui.color.LocalAngkorEatsColors
import com.example.angkoreats.ui.color.accent
import com.example.angkoreats.ui.color.background
import com.example.angkoreats.ui.color.searchBox
import com.example.angkoreats.ui.color.textPrimary
import com.example.angkoreats.ui.color.textSecond
import com.example.angkoreats.ui.typography.AngkorEatsTypography
import com.example.angkoreats.ui.typography.LocalAngkorEatsTypography
import com.example.angkoreats.ui.typography.body
import com.example.angkoreats.ui.typography.body2
import com.example.angkoreats.ui.typography.body3
import com.example.angkoreats.ui.typography.body4
import com.example.angkoreats.ui.typography.caption
import com.example.angkoreats.ui.typography.head
import com.example.angkoreats.ui.typography.search

@Composable
fun AngkorEatsTheme(
    content: @Composable () -> Unit
) {
    val angkorEatsColors = AngkorEatsColors(
        background = background,
        searchBox = searchBox,
        accent = accent,
        textPrimary = textPrimary,
        textSecond = textSecond
    )

    val angkorEatsTypography = AngkorEatsTypography(
        head = head,
        body = body,
        body2 = body2,
        body3 = body3,
        body4 = body4,
        search = search,
        caption = caption
    )

    CompositionLocalProvider(
        LocalAngkorEatsColors provides angkorEatsColors,
        LocalAngkorEatsTypography provides angkorEatsTypography,
        content = content
    )


}

object AngkorEatsTheme {
    val colors: AngkorEatsColors
        @Composable get() = LocalAngkorEatsColors.current

    val typography: AngkorEatsTypography
        @Composable get() = LocalAngkorEatsTypography.current
}
