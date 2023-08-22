package com.example.angkorwebtoonprototype.main

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorwebtoonprototype.AngkorWebtoonDetailActivity
import com.example.angkorwebtoonprototype.R
import com.example.angkorwebtoonprototype.appbar.AngkorWebtoonAppBar
import com.example.angkorwebtoonprototype.banner.TopBanner
import com.example.angkorwebtoonprototype.tab.AngkorWebtoonTab
import com.example.angkorwebtoonprototype.ui.theme.AngkorWebtoonPrototypeTheme
import com.example.angkorwebtoonprototype.webtoon.Webtoon
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AngkorWebtoonMainScreen(
    bannerImageResources: List<Int>,
    webtoons: List<Webtoon>
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val webtoonsList = List(8) {
        remember(webtoons) {
            derivedStateOf { webtoons.shuffled() }
        }
    }

    val angkorToons = listOf(
        Webtoon(
            imageResId = R.drawable.eg_1_0,
            title = "[ENG]AngkorToon Ep.1",
            writer = "Digital Angkor",
            ep = "eg_1"
        ),
        Webtoon(
            imageResId = R.drawable.eg_2_0,
            title = "[ENG]AngkorToon Ep.2",
            writer = "Digital Angkor",
            ep = "eg_2"
        ),
        Webtoon(
            imageResId = R.drawable.eg_3_0,
            title = "[ENG]AngkorToon Ep.3",
            writer = "Digital Angkor",
            ep = "eg_3"
        ),
        Webtoon(
            imageResId = R.drawable.kr_1_0,
            title = "[KR]AngkorToon Ep.1",
            writer = "Digital Angkor",
            ep = "kr_1"
        ),

        Webtoon(
            imageResId = R.drawable.kr_2_0,
            title = "[KR]AngkorToon Ep.2",
            writer = "Digital Angkor",
            ep = "kr_2"
        ),
        Webtoon(
            imageResId = R.drawable.kr_3_0,
            title = "[KR]AngkorToon Ep.3",
            writer = "Digital Angkor",
            ep = "kr_3"
        )
    )

    val context = LocalContext.current

    val intent = Intent(context,AngkorWebtoonDetailActivity::class.java)


    AngkorWebtoonPrototypeTheme {
        Scaffold(
            topBar = {
                Box {
                    TopBanner(bannerImageResources = bannerImageResources)
                    AngkorWebtoonAppBar(onSearchButtonClick = { /*TODO*/ }) {

                    }
                }
            }
        ) { scaffoldPaddingValues ->
            Column(
                Modifier.padding(top = scaffoldPaddingValues.calculateTopPadding())
            ) {
                AngkorWebtoonTab(selectedPosition = pagerState.currentPage) {
                    coroutineScope.launch { pagerState.animateScrollToPage(it) }
                }
                HorizontalPager(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    pageCount = 8,
                    pageSpacing = 16.dp,
                    state = pagerState
                ) { page ->

                    if (page == 0) {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns = GridCells.Fixed(3),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(
                                start = 16.dp,
                                end = 16.dp,
                                top = 12.dp,
                                bottom = scaffoldPaddingValues.calculateBottomPadding()
                            ),
                        ) {
                            items(angkorToons) { webtoon ->
                                Webtoon(webtoon = webtoon) {
                                    intent.putExtra("ep",webtoon.ep)
                                    context.startActivity(intent)
                                }
                            }
                        }

                    }

                    if (page != 0) {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns = GridCells.Fixed(3),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(
                                start = 16.dp,
                                end = 16.dp,
                                top = 12.dp,
                                bottom = scaffoldPaddingValues.calculateBottomPadding()
                            ),
                        ) {
                            items(webtoonsList[page].value) { webtoon ->
                                Webtoon(webtoon = webtoon) {
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    AngkorWebtoonMainScreen(
        bannerImageResources = List(5) { R.drawable.img_main_banner_h_232 },
        webtoons = List(25) {
            Webtoon(
                imageResId = R.drawable.img_category_1_h_148,
                title = "Webtoon title",
                writer = "Writer"
            )
        })
}