package com.example.angkorechoesprototype.ui.compose.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AngkorEchoesTheme

@Composable
fun HeaderText(
    modifier: Modifier = Modifier,
    text: String,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    Text(
        modifier = modifier,
        text = text,
        style = AngkorEchoesTheme.typography.head,
        color = AngkorEchoesTheme.colors.textPrimary,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout
    )
}

@Composable
@Preview
internal fun HeaderPreview() {
    AngkorEchoesTheme {
        Surface(
            color = AngkorEchoesTheme.colors.background
        ) {
            HeaderText(modifier = Modifier.padding(16.dp), text = "Popular songs")
        }
    }
}