package com.example.ui.compose.artists

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorechoesprototype.R
import com.example.ui.theme.AngkorEchoesTheme

@Composable
fun Artist(
    albumArt: Painter,
    artistName: String
) {
    Column {
        Image(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            painter = albumArt,
            contentDescription = artistName,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .width(120.dp)
                .padding(top = 8.dp),
            text = artistName,
            style = AngkorEchoesTheme.typography.body,
            color = AngkorEchoesTheme.colors.textPrimary,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
internal fun ArtistPreview() {
    AngkorEchoesTheme {
        Surface(
            color = AngkorEchoesTheme.colors.background
        ) {
            Artist(
                albumArt = painterResource(id = R.drawable.img_artist_ive_120),
                artistName = "IVE"
            )
        }
    }
}

//231 237 235