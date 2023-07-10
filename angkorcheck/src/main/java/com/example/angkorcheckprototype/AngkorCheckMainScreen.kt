package com.example.angkorcheckprototype

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorcheckprototype.appbar.AngkorCheckBottomNavigationBar
import com.example.angkorcheckprototype.appbar.SearchBar
import com.example.angkorcheckprototype.card.HotelCard
import com.example.angkorcheckprototype.card.HotelCardLarge
import com.example.angkorcheckprototype.card.HotelCardSmall
import com.example.angkorcheckprototype.tab.AngkorCheckTab
import com.example.angkorcheckprototype.ui.theme.AngkorCheckPrototypeTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AngkorCheckMainScreen(
    popularHotels: List<HotelCard>,
    hotels: List<HotelCard>,
) {
    var search by rememberSaveable {
        mutableStateOf("")
    }

    var selectedTabPosition by rememberSaveable {
        mutableStateOf(0)
    }

    var selectedBottomNavigationPosition by rememberSaveable {
        mutableStateOf(0)
    }

    AngkorCheckPrototypeTheme {
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier.statusBarsPadding()
                ) {
                    SearchBar(search = search, onSearchChange = { search = it }) {}
                    AngkorCheckTab(
                        selectedPosition = selectedTabPosition,
                        onSelectedPositionChange = { selectedTabPosition = it }
                    )
                }
            },
            bottomBar = {
                AngkorCheckBottomNavigationBar(selectedPosition = selectedBottomNavigationPosition) {
                    selectedBottomNavigationPosition = it
                }
            }
        ) { scaffoldPaddingValues ->
            LazyColumn(
                modifier = Modifier.padding(scaffoldPaddingValues)
            ) {
                item {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(60.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Populars Hotels",
                                style = MaterialTheme.typography.displaySmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "See More",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xffa2a2a2)
                            )
                        }

                        HorizontalPager(
                            modifier = Modifier.padding(bottom = 8.dp),
                            pageCount = popularHotels.size,
                            pageSpacing = 8.dp,
                            pageSize = PageSize.Fixed(200.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) { position ->
                            HotelCardSmall(hotelCard = popularHotels[position])
                        }

                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                                .height(8.dp)
                                .background(Color(0xffe9e9ef))
                        )
                    }
                }

                items(hotels) {
                    var isFavorite by rememberSaveable {
                        mutableStateOf(false)
                    }

                    HotelCardLarge(hotelCard = it, isFavorite = isFavorite) {
                        isFavorite = !isFavorite
                    }
                }
            }
        }
    }
}

@Preview
@Composable
internal fun MainScreenPreview() {

    val hotelCard = HotelCard(
        name = "Angkor Hotel",
        images = List(5) { R.drawable.img_list_1 },
        pricePerDay = 200,
        reviewsCount = 123,
        star = 4.9f
    )

    AngkorCheckMainScreen(popularHotels = List(5) { hotelCard }, hotels = List(8) { hotelCard })
}