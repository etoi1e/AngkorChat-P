package com.example.angkorcheckprototype.card

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.angkorcheckprototype.R
import com.example.angkorcheckprototype.ui.theme.AngkorCheckPrototypeTheme

data class HotelCard(
    val name: String,
    val images: List<Int>,
    val pricePerDay: Int,
    val reviewsCount: Int,
    val star: Float
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HotelCardLarge(
    hotelCard: HotelCard,
    isFavorite: Boolean,
    onHeartClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        HotelCardLargeImages(
            images = hotelCard.images,
            isFavorite = isFavorite,
            onHeartClick = onHeartClick
        )

        Column(
            modifier = Modifier.padding(horizontal = 16.dp).padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            HotelStarsAndReviews(stars = hotelCard.star, reviewsCount = hotelCard.reviewsCount)
            Text(
                text = hotelCard.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            HotelPrice(price = hotelCard.pricePerDay)
        }
    }
}

@Composable
fun HotelCardSmall(
    hotelCard: HotelCard
) {
    Column(
        modifier = Modifier.width(200.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            modifier = Modifier
                .aspectRatio(200f / 120f)
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = hotelCard.images.first()),
            contentDescription = hotelCard.name,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(4.dp))

        HotelStarsAndReviews(
            stars = hotelCard.star,
            reviewsCount = hotelCard.reviewsCount,
            starsTextSize = 14.sp,
            reviewsTextSize = 12.sp
        )
        Text(
            text = hotelCard.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        HotelPrice(price = hotelCard.pricePerDay, priceTextSize = 16.sp, unitsTextSize = 12.sp)
    }
}

@Composable
internal fun HotelStarsAndReviews(
    stars: Float,
    reviewsCount: Int,
    starsTextSize: TextUnit = 16.sp,
    reviewsTextSize: TextUnit = 14.sp
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_star_fill_yellow_20),
            contentDescription = "Stars: $stars"
        )

        Text(
            modifier = Modifier
                .alignByBaseline()
                .padding(start = 4.dp),
            text = String.format("%.1f", stars),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = starsTextSize
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .width(1.dp)
                .fillMaxHeight()
                .background(Color(0xffd9d9e0))
        )

        Text(
            modifier = Modifier.alignByBaseline(),
            text = String.format("reviews %d", reviewsCount),
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xff7c7c84),
            fontSize = reviewsTextSize
        )
    }
}

@Composable
internal fun HotelPrice(
    price: Int,
    priceTextSize: TextUnit = 22.sp,
    unitsTextSize: TextUnit = 18.sp
) {
    Text(
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
        text = buildAnnotatedString {
            withStyle(SpanStyle(fontSize = unitsTextSize)) {
                append("$")
            }
            withStyle(SpanStyle(fontSize = priceTextSize)) {
                append(price.toString())
            }
            withStyle(SpanStyle(fontSize = unitsTextSize)) {
                append("/day")
            }
        })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HotelCardLargeImages(
    images: List<Int>,
    isFavorite: Boolean,
    onHeartClick: () -> Unit
) {
    val pagerState = rememberPagerState()

    val heartIcon = if (isFavorite) {
        ImageVector.vectorResource(id = R.drawable.ic_heart_line_active_primary_24)
    } else {
        ImageVector.vectorResource(id = R.drawable.ic_heart_line_default_gray_24)
    }

    Box {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageCount = images.size,
            pageSpacing = 16.dp,
            state = pagerState
        ) { position ->
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(328f / 240f)
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(id = images[position]),
                contentDescription = "Hotel image ${position + 1}",
                contentScale = ContentScale.Crop
            )
        }

        HotelCardImageIndicator(
            currentPage = pagerState.currentPage,
            maxPage = images.size
        )

        Image(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 24.dp)
                .clip(CircleShape)
                .clickable(onClick = onHeartClick),
            imageVector = heartIcon,
            contentDescription = "Favorite"
        )
    }
}

@Composable
internal fun BoxScope.HotelCardImageIndicator(
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
fun HotelCardLargePreview() {
    val hotelCard = HotelCard(
        name = "Angkor Hotel",
        images = List(5) { R.drawable.img_list_1 },
        pricePerDay = 200,
        reviewsCount = 123,
        star = 4.9f
    )

    AngkorCheckPrototypeTheme {
        Surface {
            HotelCardLarge(hotelCard = hotelCard, isFavorite = true) {

            }
        }
    }
}

@Preview
@Composable
fun HotelCardSmallPreview() {
    val hotelCard = HotelCard(
        name = "Angkor Hotel",
        images = List(5) { R.drawable.img_list_1 },
        pricePerDay = 200,
        reviewsCount = 123,
        star = 4.9f
    )

    AngkorCheckPrototypeTheme {
        Surface {
            HotelCardSmall(hotelCard = hotelCard)
        }
    }
}