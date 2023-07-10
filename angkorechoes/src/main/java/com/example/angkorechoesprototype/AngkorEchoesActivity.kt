package com.example.angkorechoesprototype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.angkorechoesprototype.home.AngkorEchoesHomeScreen
import com.example.angkorechoesprototype.home.state.FavoriteUiState
import com.example.angkorechoesprototype.home.state.PlaylistUiState
import com.example.angkorechoesprototype.home.state.SongUiState
import com.example.angkorechoesprototype.splash.AngkorEchoesSplashScreen
import com.example.ui.theme.AngkorEchoesTheme
import kotlinx.coroutines.delay

class AngkorEchoesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isSplash by rememberSaveable {
                mutableStateOf(true)
            }
            LaunchedEffect(true) {
                delay(1000)
                isSplash = false
            }

            AnimatedVisibility(visible = isSplash, enter = fadeIn(), exit = fadeOut()) {
                AngkorEchoesSplashScreen()
            }

            AnimatedVisibility(visible = !isSplash, enter = fadeIn(), exit = fadeOut()) {

                val playlistImages = listOf(
                    R.drawable.img_artist_ive_128,
                    R.drawable.img_artist_newjeans_128,
                    R.drawable.img_artist_ive_128,
                    R.drawable.img_artist_newjeans_128
                )

                val playlists = playlistImages.map { image ->
                    PlaylistUiState(
                        image,
                        "K-pop",
                        "IVE, NewJeans, BLACKPINK, TWICE"
                    )
                }

                val songs = List(5) { index ->
                    when (index) {
                        0 -> SongUiState(R.drawable.img_album_iam_48, "I AM", "IVE")
                        1 -> SongUiState(R.drawable.img_album_flower_48, "Flower", "JISOO(BLACKPINK)")
                        2 -> SongUiState(R.drawable.img_album_iam_48, "Kitsch", "IVE")
                        3 -> SongUiState(R.drawable.img_album_people_pt_2_48, "People Pt.2(Feat.IU)", "Agust D")
                        4 -> SongUiState(R.drawable.img_album_ditto_48, "Ditto", "New Jeans")
                        else -> throw IllegalArgumentException("Invalid index: $index")
                    }
                }

                val favorites = List(10) { index ->
                    when (index) {
                        0 -> { FavoriteUiState(R.drawable.img_artist_ive_120, "IVE") }
                        1 -> { FavoriteUiState(R.drawable.img_artist_newjeans_120, "IVE") }
                        2 -> { FavoriteUiState(R.drawable.img_artist_ive_120, "IVE") }
                        3 -> { FavoriteUiState(R.drawable.img_artist_newjeans_120, "IVE") }
                        4 -> { FavoriteUiState(R.drawable.img_artist_ive_120, "IVE") }
                        5 -> { FavoriteUiState(R.drawable.img_artist_newjeans_120, "IVE") }
                        6 -> { FavoriteUiState(R.drawable.img_artist_ive_120, "IVE") }
                        7 -> { FavoriteUiState(R.drawable.img_artist_newjeans_120, "IVE") }
                        8 -> { FavoriteUiState(R.drawable.img_artist_ive_120, "IVE") }
                        9 -> { FavoriteUiState(R.drawable.img_artist_newjeans_120, "IVE") }
                        else -> throw IllegalArgumentException("Invalid index: $index")
                    }
                }


                val nowPlaying = SongUiState(R.drawable.img_album_iam_48, "I AM", "IVE")

                AngkorEchoesHomeScreen(playlists, songs, favorites, nowPlaying)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AngkorEchoesTheme {
        Greeting("Android")
    }
}