package com.example.angkoreats.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkoreats.R
import com.example.angkoreats.home.state.CategoryUiState
import com.example.angkoreats.home.state.RestaurantUiState
import com.example.angkoreats.ui.compose.appbar.BottomAppBar
import com.example.angkoreats.ui.compose.banner.TopBanner
import com.example.angkoreats.ui.compose.category.CategoryBar
import com.example.angkoreats.ui.compose.category.RestaurantsBar
import com.example.angkoreats.ui.compose.searchbar.SearchBar
import com.example.angkoreats.ui.compose.theme.AngkorEatsTheme
import com.example.angkoreats.ui.compose.topbar.AllRestaurantsBar
import com.example.angkoreats.ui.compose.topbar.TopMenuBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AngkorEatsHomeScreen(
    categoryList: List<CategoryUiState>,
    restaurantList: List<RestaurantUiState>
) {

    val scrollState = rememberScrollState()

    AngkorEatsTheme {
        Scaffold(
            containerColor = AngkorEatsTheme.colors.background,
            bottomBar = {
                Column {
                    BottomAppBar {

                    }

                }
            }
        ) { scaffoldPaddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPaddingValues)
                    .verticalScroll(scrollState),
            ) {
                TopMenuBar(
                    menuIcon = painterResource(id = R.drawable.ic_menu_line_primary_24),
                    type = "Address",
                    downArrow = painterResource(id = R.drawable.ic_arrow_down_line_primary_24),
                    noticeIcon = painterResource(id = R.drawable.ic_bell_line_default_primary_24)
                )

                TopBanner(bannerImageResources = listOf(
                    R.drawable.img_main_banner_1_h_160,
                    R.drawable.img_main_banner_2_h_160,
                    R.drawable.img_main_banner_3_h_160
                ))

                SearchBar(
                    icon = painterResource(id = R.drawable.ic_search_primary_24),
                    hint = "Search food and restaurants"
                )

                LazyRow(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 12.dp,
                        bottom = 14.dp,
                        end = 16.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categoryList) { categoryList ->
                        CategoryBar(
                            img = painterResource(id = categoryList.img),
                            category = categoryList.name
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .background(AngkorEatsTheme.colors.searchBox)
                        .fillMaxWidth()
                        .height(8.dp)
                ) {

                }

                AllRestaurantsBar(
                    filterIcon = painterResource(id = R.drawable.ic_category_line_primary_24),
                    string = "All Restaurants"
                )

                LazyRow(
                    modifier = Modifier.padding(bottom = 12.dp),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp, vertical = 12.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(restaurantList) { restaurantList ->
                        RestaurantsBar(
                            img = painterResource(id = restaurantList.image),
                            name = restaurantList.name,
                            time = restaurantList.time
                        )
                    }
                }

            }


        }

    }

}


@Preview
@Composable
fun HomeScreenPreview() {

    val categoryList = List(7) { index ->
        when (index) {
            0 -> {
                CategoryUiState(R.drawable.img_category_popular_72, "Popular")
            }

            1 -> {
                CategoryUiState(R.drawable.img_category_drinks_72, "Drinks")
            }

            2 -> {
                CategoryUiState(R.drawable.img_category_hamburger_72, "Hamburger")
            }

            3 -> {
                CategoryUiState(R.drawable.img_category_pizza_72, "Pizza")
            }

            4 -> {
                CategoryUiState(R.drawable.img_category_drinks_72, "Drinks")
            }

            5 -> {
                CategoryUiState(R.drawable.img_category_popular_72, "Drinks")
            }

            6 -> {
                CategoryUiState(R.drawable.img_category_drinks_72, "Drinks")
            }

            else -> throw IllegalArgumentException("Invalid index: $index")
        }
    }

    val restaurantList = List(5) { index ->
        when (index) {
            0 -> {
                RestaurantUiState(
                    R.drawable.img_all_restaurants_1_144,
                    "Eleven Madison park",
                    "20~25 min"
                )
            }

            1 -> {
                RestaurantUiState(
                    R.drawable.img_all_restaurants_1_144,
                    "The Lobster Place",
                    "20~25 min"
                )
            }

            2 -> {
                RestaurantUiState(
                    R.drawable.img_all_restaurants_1_144,
                    "Eleven Madison park",
                    "20~25 min"
                )
            }

            3 -> {
                RestaurantUiState(
                    R.drawable.img_all_restaurants_1_144,
                    "The Lobster Place",
                    "20~25 min"
                )
            }

            4 -> {
                RestaurantUiState(
                    R.drawable.img_all_restaurants_1_144,
                    "Eleven Madison park",
                    "20~25 min"
                )
            }

            else -> throw IllegalArgumentException("Invalid index: $index")
        }
    }


    AngkorEatsHomeScreen(categoryList = categoryList, restaurantList = restaurantList)
}