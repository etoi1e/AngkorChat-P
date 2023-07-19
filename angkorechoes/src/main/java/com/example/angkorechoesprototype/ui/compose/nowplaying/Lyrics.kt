package com.example.angkorechoesprototype.ui.compose.nowplaying

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AngkorEchoesTheme

@Composable
fun ColumnScope.CompactLyrics(
    lyrics: List<String>,
    selectedLyricsIndex: Int
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(lyrics.size) { index ->
                val textColor by animateColorAsState(targetValue = if (index == selectedLyricsIndex) AngkorEchoesTheme.colors.accent else AngkorEchoesTheme.colors.textPrimary)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = lyrics[index],
                    style = AngkorEchoesTheme.typography.head,
                    color = textColor,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}