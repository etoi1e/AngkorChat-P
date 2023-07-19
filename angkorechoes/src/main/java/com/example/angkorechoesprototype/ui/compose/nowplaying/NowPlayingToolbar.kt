package com.example.angkorechoesprototype.ui.compose.nowplaying

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.angkorechoesprototype.R
import com.example.ui.theme.AngkorEchoesTheme

@Composable
fun NowPlayingToolbar(
    songName: String,
    songArtist: String,
    onCollapseButtonClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onCollapseButtonClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_down_line_wh_24),
                contentDescription = "",
                tint = AngkorEchoesTheme.colors.textPrimary
            )
        }

        Column(
            modifier = Modifier.weight(1f).padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = songName,
                style = AngkorEchoesTheme.typography.head,
                color = AngkorEchoesTheme.colors.textPrimary,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = songArtist,
                style = AngkorEchoesTheme.typography.body,
                color = AngkorEchoesTheme.colors.textSecondary,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp
            )
        }

        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.ic_dots_horizontal_solid_wh_24),
                contentDescription = "",
                tint = AngkorEchoesTheme.colors.textPrimary
            )
        }
    }
}

@Preview
@Composable
internal fun NowPlayingToolbarPreview() {
    AngkorEchoesTheme {
        Surface(color = AngkorEchoesTheme.colors.background) {
            NowPlayingToolbar(
                "I AM",
                "IVE"
            ) {

            }
        }
    }
}