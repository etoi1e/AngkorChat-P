package com.example.angkorplayprototype

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorplayprototype.appbar.AngkorPlayAppBar
import com.example.angkorplayprototype.poster.MainPosters
import com.example.angkorplayprototype.poster.PopularPosters
import com.example.angkorplayprototype.poster.Posters
import com.example.angkorplayprototype.poster.WatchingContent
import com.example.angkorplayprototype.poster.WatchingContents
import com.example.angkorplayprototype.text.HeaderText
import com.example.angkorplayprototype.ui.theme.AngkorPlayPrototypeTheme
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AngkorPlayMainScreen(
    mainPosters: List<Int>,
    watchingContents: List<WatchingContent>,
    popularPosters: List<Int>,
    kContents: List<Int>,
    newContents: List<Int>
) {
    AngkorPlayPrototypeTheme {
        Scaffold(
            topBar = {
                AngkorPlayAppBar()
            }
        ) { scaffoldPaddingValues ->
            LazyColumn(modifier = Modifier.padding(scaffoldPaddingValues)) {
                item { MainPosters(imageResIds = mainPosters) }
                item { HeaderText(text = "Currently watching content") }
                item { WatchingContents(watchingContents) }
                item { HeaderText(text = "This week’s top 20 popular works") }
                item { PopularPosters(popularPosters) }
                item {
                    HeaderText(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "K-Contents"
                    )
                }
                item { Posters(kContents) }
                item {
                    HeaderText(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "Newly released contents"
                    )
                }
                item { Posters(newContents) }
                item { Spacer(modifier = Modifier.height(41.dp)) }
            }
        }
    }
}

internal val mainPosters = listOf(
    R.drawable.img_main_poster_1,
    R.drawable.img_main_poster_2,
    R.drawable.img_main_poster_1,
    R.drawable.img_main_poster_2
)
internal val watchingContents = listOf(
    WatchingContent(
        imageResId = R.drawable.img_watching_1,
        name = "Sky Castle ep.5",
        progress = Random.nextFloat()
    ),
    WatchingContent(
        imageResId = R.drawable.img_watching_3,
        name = "Little Women ep.10",
        progress = Random.nextFloat()
    ),
    WatchingContent(
        imageResId = R.drawable.img_watching_3,
        name = "Little Women ep.10",
        progress = Random.nextFloat()
    ),
)
internal val popularPosters = listOf(
    R.drawable.img_popular_1,
    R.drawable.img_popular_2,
    R.drawable.img_popular_3,
    R.drawable.img_k_contents_3,
    R.drawable.img_k_contents_4,
)
internal val kContents = listOf(
    R.drawable.img_k_contents_1,
    R.drawable.img_k_contents_2,
    R.drawable.img_k_contents_3,
    R.drawable.img_k_contents_4,
    R.drawable.img_k_contents_3,
    R.drawable.img_k_contents_4
)
internal val newContents = listOf(
    R.drawable.img_new_1,
    R.drawable.img_new_2,
    R.drawable.img_new_3,
    R.drawable.img_new_4,
    R.drawable.img_popular_2,
    R.drawable.img_popular_3,
    R.drawable.img_new_3,
    R.drawable.img_new_4
)

@Preview
@Composable
fun MainScreenPreview() {
    AngkorPlayMainScreen(
        mainPosters = mainPosters,
        watchingContents = watchingContents,
        popularPosters = popularPosters,
        kContents = kContents,
        newContents = newContents
    )
}