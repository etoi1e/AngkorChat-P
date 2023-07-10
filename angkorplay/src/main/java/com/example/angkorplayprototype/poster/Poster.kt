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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

internal val POSTER_WIDTH_DP = 96.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Posters(
    imageResIds: List<Int>
) {
    val pagerState = rememberPagerState()

    HorizontalPager(
        pageCount = imageResIds.size,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        pageSpacing = 8.dp,
        pageSize = PageSize.Fixed(POSTER_WIDTH_DP),
        state = pagerState
    ) {
        Poster(
            imageResId = imageResIds[it]
        )
    }
}

@Composable
fun Poster(
    @DrawableRes imageResId: Int
) {

    val image = painterResource(id = imageResId)

    Box(
        modifier = Modifier
            .width(POSTER_WIDTH_DP)
            .aspectRatio(POSTER_WIDTH_DP.value / 136f)
            .clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = image,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}