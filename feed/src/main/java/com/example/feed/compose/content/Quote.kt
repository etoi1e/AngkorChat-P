package com.example.feed.compose.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feed.R
import com.example.feed.compose.feed.FeedImages
import com.example.feed.compose.profile.Profile
import com.example.feed.sample.sampleFeeds
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.model.Feed

@Composable
fun Quote(
    feed: Feed,
    onRemove: (() -> Unit)?
) {
    ContentContainer {
        Column {
            Row {
                Profile(modifier = Modifier.weight(1f), user = feed.user, info = feed.authorInfo)
                if (onRemove != null) {
                    IconButton(onClick = onRemove) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close_line_gray_16),
                            contentDescription = "Remove",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            if (feed.content?.isNotBlank() == true) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = feed.content,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (!feed.photos.isNullOrEmpty()) {
                FeedImages(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .heightIn(max = 152.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    images = feed.photos
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}

@Preview
@Composable
fun QuotePreview() {
    AngKorChatProtoFeedTheme {
        Surface {
            Quote(feed = sampleFeeds[0]) {

            }
        }
    }
}