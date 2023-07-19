package com.example.angkorechoesprototype.ui.compose.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.angkorechoesprototype.R
import com.example.ui.theme.AngkorEchoesTheme

@Composable
fun Playlist(
    playlistCoverImage: Painter,
    playlistName: String,
    playlistArtists: String
) {
    Column {
        Box(
            modifier = Modifier.size(128.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                painter = playlistCoverImage,
                contentDescription = playlistName
            )
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_play),
                contentDescription = "Play",
                tint = AngkorEchoesTheme.colors.accent
            )
        }
        Text(
            modifier = Modifier
                .width(128.dp)
                .padding(top = 8.dp),
            text = playlistName,
            style = AngkorEchoesTheme.typography.body,
            color = AngkorEchoesTheme.colors.textPrimary
        )
        Text(
            modifier = Modifier
                .width(128.dp),
            text = playlistArtists,
            style = AngkorEchoesTheme.typography.body2,
            color = AngkorEchoesTheme.colors.textSecondary
        )
    }
}

@Composable
@Preview
internal fun PlaylistPreview() {
    AngkorEchoesTheme {
        Surface(
            color = AngkorEchoesTheme.colors.background
        ) {
            Playlist(
                playlistCoverImage = painterResource(id = R.drawable.img_album_iam_48),
                playlistName = "K-pop",
                playlistArtists = "IVE, NewJeans, BLACKPINK, TWICE"
            )
        }
    }
}