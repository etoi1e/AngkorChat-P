package com.example.angkorgamesprototype.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.angkorgamesprototype.R
import com.example.angkorgamesprototype.ui.theme.AngkorGamesPrototypeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularGames(
    gameImageResources: List<Int>
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageCount = gameImageResources.size,
            pageSpacing = 16.dp,
            state = pagerState
        ) { position ->
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(328.dp)
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(id = gameImageResources[position]),
                contentDescription = "Game ${position + 1}",
                contentScale = ContentScale.Crop
            )
        }

        PopularGamesIndicator(
            currentPosition = pagerState.currentPage,
            maxPositions = gameImageResources.size
        ) {
            coroutineScope.launch { pagerState.animateScrollToPage(it) }
        }
    }
}

@Composable
internal fun PopularGamesIndicator(
    currentPosition: Int,
    maxPositions: Int,
    onDotClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        repeat(maxPositions) { position ->
            PopularGamesIndicatorDot(isSelected = currentPosition == position) {
                onDotClick(position)
            }
        }
    }
}

@Composable
internal fun PopularGamesIndicatorDot(
    isSelected: Boolean,
    onDotClick: () -> Unit
) {
    val dotColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    )

    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .clickable(enabled = !isSelected, onClick = onDotClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(dotColor)
        )
    }
}

@Preview
@Composable
internal fun PopularGamesPreview() {
    AngkorGamesPrototypeTheme {
        PopularGames(gameImageResources = List(3) { R.drawable.angkor_games_img_main_banner_1 })
    }
}