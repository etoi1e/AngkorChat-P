package com.example.angkorechoesprototype.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.angkorechoesprototype.R
import com.example.angkorechoesprototype.home.state.FavoriteUiState
import com.example.angkorechoesprototype.home.state.PlaylistUiState
import com.example.angkorechoesprototype.model.Song
import com.example.angkorechoesprototype.model.sampleSongs
import com.example.angkorechoesprototype.ui.compose.appbar.BottomNavigationBar
import com.example.angkorechoesprototype.ui.compose.artists.Artist
import com.example.angkorechoesprototype.ui.compose.nowplaying.NowPlaying
import com.example.angkorechoesprototype.ui.compose.playlist.Playlist
import com.example.angkorechoesprototype.ui.compose.song.Song
import com.example.angkorechoesprototype.ui.compose.text.HeaderText
import com.example.ui.theme.AngkorEchoesTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AngkorEchoesHomeScreen(
    playlists: List<PlaylistUiState>,
    songs: List<Song>,
    favorites: List<FavoriteUiState>,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    var expanded by remember {
        mutableStateOf(false)
    }
    var nowPlaying by remember {
        mutableStateOf<Song?>(null)
    }
    var nowPlayingDuration by remember {
        mutableStateOf(0L)
    }

    LaunchedEffect(nowPlaying) {
        nowPlayingDuration = 0

        val start = System.currentTimeMillis()

        if(nowPlaying != null) {
            while (System.currentTimeMillis() - start < nowPlaying!!.durationMs) {
                nowPlayingDuration = System.currentTimeMillis() - start
                delay(100)
            }
        }
    }

    BackHandler {
        when {
            expanded -> expanded = false
            else -> onBack()
        }
    }

    AngkorEchoesTheme {
        Scaffold(
            containerColor = AngkorEchoesTheme.colors.background,
            bottomBar = {
                AnimatedVisibility(
                    visible = !expanded,
                    enter = fadeIn() + expandIn { IntSize(it.width, 0) },
                    exit = shrinkOut { IntSize(it.width, 0) } + fadeOut()
                ) {
                    BottomNavigationBar {

                    }
                }
            }
        ) { scaffoldPaddingValues ->
            Column(modifier = Modifier.padding(scaffoldPaddingValues)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Image(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(28.dp)
                                    .clickable { },
                                imageVector = ImageVector.vectorResource(R.drawable.ic_profile_default),
                                contentDescription = "Profile"
                            )
                        }
                    }

                    item {
                        HeaderText(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "This is a song that you will love"
                        )

                        LazyRow(
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(playlists) { playlist ->
                                Playlist(
                                    playlistCoverImage = painterResource(id = playlist.albumArtResourceId),
                                    playlistName = playlist.name,
                                    playlistArtists = playlist.artists
                                )
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .height(56.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HeaderText(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .weight(1f),
                                text = "Popular Songs"
                            )

                            Icon(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clickable { },
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_refresh_line),
                                contentDescription = "Refresh",
                                tint = AngkorEchoesTheme.colors.accent
                            )
                        }
                    }

                    items(songs) { song ->
                        Song(
                            song = song
                        ) {
                            nowPlaying = song
                        }
                    }

                    item {
                        HeaderText(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .padding(top = 40.dp),
                            text = "Your favorites"
                        )

                        LazyRow(
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(favorites) { favorite ->
                                Artist(
                                    albumArt = painterResource(id = favorite.albumArtResourceId),
                                    artistName = favorite.artist,
                                )
                            }
                        }
                    }
                }

                nowPlaying?.let { nowPlaying ->
                    NowPlaying(
                        song = nowPlaying,
                        currentSongDurationMs = nowPlayingDuration,
                        isPlaying = true,
                        onPreviousClick = {},
                        onPlayPauseClick = {},
                        onNextClick = {},
                        expanded = expanded,
                        onExpandedStateChange = { expanded = it }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val playlists = List(10) {
        PlaylistUiState(
            R.drawable.img_album_iam_48,
            "K-pop",
            "IVE, NewJeans, BLACKPINK, TWICE"
        )
    }
    val favorites =
        List(10) { FavoriteUiState(R.drawable.img_artist_ive_120, "IVE") }

    AngkorEchoesHomeScreen(playlists, sampleSongs, favorites) {

    }
}