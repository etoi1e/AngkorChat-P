package com.example.angkorechoesprototype.ui.compose.song

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.angkorechoesprototype.R
import com.example.angkorechoesprototype.model.Song
import com.example.ui.theme.AngkorEchoesTheme

@Composable
fun Song(
    song: Song,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(start = 16.dp, top = 11.dp, bottom = 11.dp)
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = song.albumArtResourceId),
            contentDescription = song.name
        )

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = song.name,
                style = AngkorEchoesTheme.typography.body,
                color = AngkorEchoesTheme.colors.textPrimary,
                lineHeight = 24.sp
            )
            Text(
                text = song.artist,
                style = AngkorEchoesTheme.typography.body2,
                color = AngkorEchoesTheme.colors.textSecondary,
                lineHeight = 24.sp
            )
        }

        Icon(
            modifier = Modifier
                .padding(16.dp)
                .size(24.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_more_wh),
            contentDescription = "Play",
            tint = AngkorEchoesTheme.colors.onBackground
        )
    }
}