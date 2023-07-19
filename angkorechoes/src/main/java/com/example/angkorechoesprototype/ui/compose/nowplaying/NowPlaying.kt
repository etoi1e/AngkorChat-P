package com.example.angkorechoesprototype.ui.compose.nowplaying

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.angkorechoesprototype.R
import com.example.angkorechoesprototype.model.Song
import com.example.angkorechoesprototype.model.sampleSongs
import com.example.ui.theme.AngkorEchoesTheme

@Composable
fun NowPlaying(
    song: Song,
    currentSongDurationMs: Long,
    isPlaying: Boolean,
    expanded: Boolean,
    onExpandedStateChange: (Boolean) -> Unit,
    onPreviousClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(1f, expanded)
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AngkorEchoesTheme.colors.background)
                ) {
                    NowPlayingToolbar(songName = song.name, songArtist = song.artist) {
                        onExpandedStateChange(false)
                    }

                    Image(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .padding(top = 24.dp)
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        painter = painterResource(id = song.albumArtResourceId),
                        contentDescription = ""
                    )

                    Box(
                        modifier = Modifier
                            .height(56.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {}) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_heart_line_wh_24),
                                    contentDescription = "",
                                    tint = AngkorEchoesTheme.colors.textPrimary
                                )
                            }

                            Text(
                                text = "124,235",
                                style = AngkorEchoesTheme.typography.bodyLarge,
                                color = AngkorEchoesTheme.colors.textPrimary
                            )
                        }

                        IconButton(modifier = Modifier.align(Alignment.CenterEnd), onClick = {}) {
                            Icon(
                                painter = painterResource(id = R.drawable.share_07),
                                contentDescription = "",
                                tint = AngkorEchoesTheme.colors.textPrimary
                            )
                        }
                    }

                    CompactLyrics(
                        lyrics = listOf("Stay in the middle", "Like you a little"),
                        selectedLyricsIndex = 0
                    )
                }
            }
        }
        NowPlayingSlider(
            songDurationMs = song.durationMs,
            songCurrentDurationMs = currentSongDurationMs,
            isChangeDurationAvailable = expanded,
            onDurationChange = {}
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .background(AngkorEchoesTheme.colors.background)
                .clickable(enabled = !expanded) { onExpandedStateChange(true) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(40.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    painter = painterResource(id = song.albumArtResourceId),
                    contentDescription = song.name
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = song.name,
                        style = AngkorEchoesTheme.typography.body,
                        color = AngkorEchoesTheme.colors.textPrimary,
                        fontSize = 15.sp,
                        lineHeight = 20.sp
                    )
                    Text(
                        text = song.artist,
                        style = AngkorEchoesTheme.typography.body2,
                        color = AngkorEchoesTheme.colors.textSecondary,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }
            }


            NowPlayingControl(expanded = expanded)
        }
    }
}

@Preview
@Composable
fun NowPlayingBarPreview() {
    AngkorEchoesTheme {
        var expanded by remember {
            mutableStateOf(true)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AngkorEchoesTheme.colors.background)
                .clickable { expanded = !expanded },
            contentAlignment = Alignment.BottomCenter
        ) {
            NowPlaying(
                song = sampleSongs[0],
                currentSongDurationMs = 1000,
                isPlaying = true,
                onPreviousClick = {},
                onPlayPauseClick = {},
                onNextClick = {},
                expanded = expanded,
                onExpandedStateChange = {
                    expanded = false
                }
            )
        }

    }
}