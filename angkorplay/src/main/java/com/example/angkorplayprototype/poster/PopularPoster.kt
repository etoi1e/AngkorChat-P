package com.example.angkorplayprototype.poster

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorplayprototype.R
import com.example.angkorplayprototype.ui.theme.AngkorPlayPrototypeTheme

val POPULAR_POSTER_WIDTH_DP = 120.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularPosters(
    imageResIds: List<Int>
) {
    val pagerState = rememberPagerState()

    HorizontalPager(
        pageCount = imageResIds.size,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        pageSpacing = 8.dp,
        pageSize = PageSize.Fixed(POPULAR_POSTER_WIDTH_DP),
        state = pagerState
    ) {
        PopularPoster(
            rank = it + 1,
            imageResId = imageResIds[it]
        )
    }
}

@Composable
fun PopularPoster(
    rank: Int,
    @DrawableRes imageResId: Int
) {

    val image = painterResource(id = imageResId)

    Box(
        modifier = Modifier
            .width(POPULAR_POSTER_WIDTH_DP)
            .aspectRatio(120f / 160f)
            .clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = image,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 9.dp),
            text = rank.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun PopularPosterPreview() {
    AngkorPlayPrototypeTheme {
        Surface {
            PopularPoster(rank = 1, imageResId = R.drawable.img_popular_1)
        }
    }
}