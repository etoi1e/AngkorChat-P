package com.example.feed.compose.story

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feed.R
import com.example.feed.compose.suggestion.SuggestedUser
import com.example.feed.sample.sampleStoryCovers
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.model.StoryCover

@Composable
fun StoryCovers(
    storyCovers: List<StoryCover>
) {
    LazyRow(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            NewStory()
        }

        items(storyCovers, key = { it.id }) {
            StoryCoverSmall(it)
        }
    }
}

@Composable
fun StoryCoverSmall(
    storyCover: StoryCover
) {
    Box(
        modifier = Modifier.storyCoverModifier {

        },
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        0f to Color(0x00ffffff),
                        0.34f to Color(0x216b6b6b),
                        1f to Color(0xff2f2f2f),
                        startY = size.height / 3,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                },
            painter = painterResource(storyCover.backgroundResId),
            contentDescription = storyCover.title,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            text = storyCover.title,
            style = MaterialTheme.typography.labelMedium,
            letterSpacing = (-0.24).sp,
            color = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun NewStory() {
    Box(modifier = Modifier
        .storyCoverModifier {

        }
        .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            modifier = Modifier.size(90.dp),
            painter = painterResource(id = R.drawable.lemon),
            contentDescription = "New Story",
            contentScale = ContentScale.Crop
        )

        Image(
            modifier = Modifier.padding(top = 78.dp),
            painter = painterResource(id = R.drawable.ic_plus_rounded_square_prime_24),
            contentDescription = "New Story"
        )

        Text(
            text = "Create\nStory",
            modifier = Modifier
                .padding(top = 112.dp, bottom = 15.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            lineHeight = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

internal fun Modifier.storyCoverModifier(
    onClick: () -> Unit
) = this
    .width(90.dp)
    .height(160.dp)
    .clip(RoundedCornerShape(8.dp))
    .clickable(onClick = onClick)

@Preview
@Composable
internal fun StoryCoverSmallPreview() {
    AngKorChatProtoFeedTheme {
        Surface {
            StoryCoverSmall(StoryCover(1, R.drawable.story_1, "Name"))
        }
    }
}

@Preview
@Composable
internal fun NewStoryPreview() {
    AngKorChatProtoFeedTheme {
        Surface {
            NewStory()
        }
    }
}

@Preview
@Composable
internal fun StoryCoversPreview() {
    AngKorChatProtoFeedTheme {
        Surface {
            StoryCovers(sampleStoryCovers)
        }
    }
}