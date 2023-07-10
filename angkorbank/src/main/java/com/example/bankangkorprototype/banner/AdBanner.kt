package com.example.bankangkorprototype.banner

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bankangkorprototype.R
import com.example.bankangkorprototype.ui.theme.BankAngkorPrototypeTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdBanner(
    modifier: Modifier = Modifier,
    ads: List<Int>
) {
    val pagerState = rememberPagerState()
    
    Box(
        modifier = modifier
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageCount = ads.size,
            pageSpacing = 16.dp,
            state = pagerState
        ) { position ->
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(328f / 80f)
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(id = ads[position]),
                contentDescription = "Hotel image ${position + 1}",
                contentScale = ContentScale.Crop
            )
        }

        Indicator(
            currentPage = pagerState.currentPage,
            maxPage = ads.size
        )
    }
}

@Composable
internal fun BoxScope.Indicator(
    currentPage: Int,
    maxPage: Int
) {
    Box(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(end = 16.dp)
            .padding(8.dp)
            .size(width = 36.dp, height = 16.dp)
            .clip(RoundedCornerShape(100))
            .background(
                Color.Black.copy(alpha = 0.6f)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${currentPage + 1}/$maxPage",
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}

@Preview
@Composable
internal fun AdBannerPreview() {
    BankAngkorPrototypeTheme {
        Surface {
            AdBanner(ads = List(5) { R.drawable.img_banner_1 })
        }
    }
}