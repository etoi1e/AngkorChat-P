package com.example.angkorplayprototype.poster

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorplayprototype.R
import com.example.angkorplayprototype.ui.theme.AngkorPlayPrototypeTheme

internal val MAIN_POSTER_WIDTH_DP = 296.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainPosters(
    imageResIds: List<Int>
) {
    val pagerState = rememberPagerState()

    HorizontalPager(
        pageCount = imageResIds.size,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        pageSpacing = 8.dp,
        pageSize = PageSize.Fixed(MAIN_POSTER_WIDTH_DP),
        state = pagerState
    ) {
        MainPoster(
            posterImageResId = imageResIds[it],
            isShowButtons = pagerState.currentPage == it
        )
    }
}

@Composable
fun MainPoster(
    @DrawableRes posterImageResId: Int,
    isShowButtons: Boolean
) {
    val image = painterResource(id = posterImageResId)
    val playIcon = ImageVector.vectorResource(id = R.drawable.ic_play_fill_bk_24)
    val saveIcon = ImageVector.vectorResource(id = R.drawable.ic_download_line_wh_24)

    Box(
        modifier = Modifier
            .width(MAIN_POSTER_WIDTH_DP)
            .aspectRatio(296f / 408f)
            .clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = image,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        AnimatedVisibility(visible = isShowButtons, enter = fadeIn(), exit = fadeOut()) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MainPosterButton(
                    text = "Play",
                    icon = playIcon,
                    backgroundColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.background
                )
                MainPosterButton(
                    text = "Save",
                    icon = saveIcon,
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
internal fun RowScope.MainPosterButton(
    text: String,
    icon: ImageVector,
    backgroundColor: Color,
    contentColor: Color
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .clickable { }
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 12.dp)
                .padding(top = 7.dp, bottom = 6.dp),
            imageVector = icon,
            contentDescription = text,
            tint = contentColor
        )

        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 16.dp),
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = contentColor
        )
    }
}

@Preview
@Composable
internal fun MainPosterPreview() {
    AngkorPlayPrototypeTheme {
        Surface {
            MainPoster(R.drawable.img_main_poster_1, true)
        }
    }
}

@Preview
@Composable
internal fun MainPostersPreview() {
    AngkorPlayPrototypeTheme {
        Surface {
            MainPosters(
                List(5) { R.drawable.img_main_poster_1 }
            )
        }
    }
}
