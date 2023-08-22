package com.example.angkorwebtoonprototype.banner

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorwebtoonprototype.R
import com.example.angkorwebtoonprototype.ui.theme.AngkorWebtoonPrototypeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopBanner(
    bannerImageResources: List<Int>
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            pageCount = bannerImageResources.size,
            state = pagerState
        ) { position ->
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(232.dp),
                painter = painterResource(id = bannerImageResources[position]),
                contentDescription = "Game ${position + 1}",
                contentScale = ContentScale.Crop
            )
        }

        TopBannerIndicator(
            currentPosition = pagerState.currentPage,
            maxPositions = bannerImageResources.size
        ) {
            coroutineScope.launch { pagerState.animateScrollToPage(it) }
        }
    }
}

@Composable
internal fun TopBannerIndicator(
    currentPosition: Int,
    maxPositions: Int,
    onDotClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        repeat(maxPositions) { position ->
            TopBannerIndicatorDot(isSelected = currentPosition == position) {
                onDotClick(position)
            }
        }
    }
}

@Composable
internal fun TopBannerIndicatorDot(
    isSelected: Boolean,
    onDotClick: () -> Unit
) {
    val dotColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    )

    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .clickable(enabled = !isSelected, onClick = onDotClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .clip(CircleShape)
                .background(dotColor)
        )
    }
}

@Preview
@Composable
internal fun PopularGamesPreview() {
    AngkorWebtoonPrototypeTheme {
        TopBanner(bannerImageResources = List(5) { R.drawable.eg_2_0 })
    }
}