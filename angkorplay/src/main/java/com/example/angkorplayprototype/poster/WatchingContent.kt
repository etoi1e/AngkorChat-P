package com.example.angkorplayprototype.poster

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorplayprototype.R
import com.example.angkorplayprototype.ui.theme.AngkorPlayPrototypeTheme

internal val WATCHING_CONTENT_WIDTH_DP = 148.dp

data class WatchingContent(
    @DrawableRes val imageResId: Int,
    val name: String,
    val progress: Float
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WatchingContents(
    watchingContents: List<WatchingContent>
) {
    val pagerState = rememberPagerState()

    HorizontalPager(
        pageCount = watchingContents.size,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        pageSpacing = 8.dp,
        pageSize = PageSize.Fixed(WATCHING_CONTENT_WIDTH_DP),
        state = pagerState
    ) {
        WatchingContent(
            watchingContents[it]
        )
    }
}

@Composable
fun WatchingContent(
    watchingContent: WatchingContent
) {
    val image = painterResource(id = watchingContent.imageResId)
    val icon = ImageVector.vectorResource(id = R.drawable.ic_play_fill_wh_24)

    Column(
        modifier = Modifier.width(WATCHING_CONTENT_WIDTH_DP)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(148f / 96f)
                .clip(RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = image,
                contentDescription = watchingContent.name,
                contentScale = ContentScale.Crop
            )

            Icon(
                modifier = Modifier.align(Alignment.Center),
                imageVector = icon,
                contentDescription = "Play"
            )

            Box(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth(watchingContent.progress)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            text = watchingContent.name,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun WatchingContentPreview() {
    AngkorPlayPrototypeTheme {
        Surface {
            WatchingContent(
                watchingContent = WatchingContent(
                    imageResId = R.drawable.img_watching_1,
                    name = "Sky Castle ep.5",
                    progress = 0.6f
                )
            )
        }
    }
}