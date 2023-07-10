package com.example.angkorgamesprototype.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorgamesprototype.R
import com.example.angkorgamesprototype.appbar.AngkorGamesAppBar
import com.example.angkorgamesprototype.header.HeaderText
import com.example.angkorgamesprototype.ui.theme.AngkorGamesPrototypeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AngkorGamesMainScreen(
    popularGames: List<Int>,
    preOrderGames: List<Game>,
    recommendGames: List<Game>
) {
    AngkorGamesPrototypeTheme {
        Scaffold(
            topBar = {
                AngkorGamesAppBar(onSearchButtonClick = {}) {
                    
                }
            }
        ) { scaffoldPaddingValues ->
            LazyColumn(modifier = Modifier.padding(scaffoldPaddingValues)) {
                item {
                    HeaderText(text = "Popular games at the moment")
                    PopularGames(gameImageResources = popularGames)
                }

                item {
                    HeaderText(text = "Pre-order game")
                }

                items(preOrderGames) { game ->
                    Game(game = game) {

                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .background(Color(0xffe6e6e6))
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                    HeaderText(text = "Recommended mobile games.")
                }

                items(recommendGames) { game ->
                    Game(game = game) {

                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    AngkorGamesMainScreen(
        popularGames = List(3) { R.drawable.angkor_games_img_main_banner_1 },
        preOrderGames = List(3) {
            Game(
                imageResourceId = R.drawable.img_pre_order_battlegrounds_h_96,
                name = "Battle grounds",
                description = "Let’s enjoy playing fun games together!"
            )
        },
        recommendGames = List(3) {
            Game(
                imageResourceId = R.drawable.img_pre_order_battlegrounds_h_96,
                name = "Battle grounds",
                description = "Let’s enjoy playing fun games together!"
            )
        }
    )
}