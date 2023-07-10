package com.example.ui.compose.nowplaying

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun NowPlayingBar(
    albumArt: Painter,
    songName: String,
    songArtist: String,
    songDurationMs: Long,
    songCurrentDurationMs: Long,
    isPlaying: Boolean,
    onPreviousClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val songDurationPercentage = remember {
        songCurrentDurationMs / songDurationMs.toFloat()
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color(0xffbbbbbb))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(songDurationPercentage)
                    .height(2.dp)
                    .background(AngkorEchoesTheme.colors.accent)
            )
        }
        Row(
            modifier = Modifier.height(68.dp).background(AngkorEchoesTheme.colors.background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(4.dp)),
                painter = albumArt,
                contentDescription = songName
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = songName,
                    style = AngkorEchoesTheme.typography.body,
                    color = AngkorEchoesTheme.colors.textPrimary,
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                )
                Text(
                    text = songArtist,
                    style = AngkorEchoesTheme.typography.body2,
                    color = AngkorEchoesTheme.colors.textSecondary,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }

            IconButton(onClick = onPreviousClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_music_controller_skip_pre),
                    contentDescription = "Previous",
                    tint = AngkorEchoesTheme.colors.onBackground
                )
            }

            IconButton(onClick = onPlayPauseClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_music_controller_pause),
                    contentDescription = "Play/Pause",
                    tint = AngkorEchoesTheme.colors.onBackground
                )
            }

            IconButton(onClick = onNextClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_music_controller_skip_next),
                    contentDescription = "Next",
                    tint = AngkorEchoesTheme.colors.onBackground
                )
            }
        }
    }
}

@Preview
@Composable
fun NowPlayingBarPreview() {
    AngkorEchoesTheme {
        NowPlayingBar(
            albumArt = painterResource(id = R.drawable.img_album_iam_48),
            songName = "I AM",
            songArtist = "IVE",
            songDurationMs = 1000,
            songCurrentDurationMs = 600,
            isPlaying = true,
            onPreviousClick = {},
            onPlayPauseClick = {},
            onNextClick = {}
        )
    }
}