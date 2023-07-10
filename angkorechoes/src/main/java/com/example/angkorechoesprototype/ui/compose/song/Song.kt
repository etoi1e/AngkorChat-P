package com.example.ui.compose.song

import androidx.compose.foundation.Image
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
import com.example.ui.theme.AngkorEchoesTheme

@Composable
fun Song(
    albumArt: Painter,
    songName: String,
    songArtist: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(start = 16.dp, top = 11.dp, bottom = 11.dp)
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = albumArt,
            contentDescription = songName
        )

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = songName,
                style = AngkorEchoesTheme.typography.body,
                color = AngkorEchoesTheme.colors.textPrimary,
                lineHeight = 24.sp
            )
            Text(
                text = songArtist,
                style = AngkorEchoesTheme.typography.body2,
                color = AngkorEchoesTheme.colors.textSecondary,
                lineHeight = 24.sp
            )
        }

        Icon(
            modifier = Modifier.padding(16.dp).size(24.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_more_wh),
            contentDescription = "Play",
            tint = AngkorEchoesTheme.colors.onBackground
        )
    }
}

@Composable
@Preview
internal fun SongPreview() {
    AngkorEchoesTheme {
        Surface(
            color = AngkorEchoesTheme.colors.background
        ) {
            Song(
                albumArt = painterResource(id = R.drawable.img_album_iam_48),
                songName = "I AM",
                songArtist = "IVE"
            )
        }
    }
}