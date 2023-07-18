package com.example.feed.compose.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.sp
import com.example.feed.sample.sampleUsers
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.model.User

@Composable
fun ProfileFeedWith(
    modifier: Modifier = Modifier,
    @DrawableRes images: List<Int>
) {
    val imageSize = if (images.size < 2) 24.dp else 20.dp
    val imageClipRadius = if (images.size < 2) 8.dp else 7.dp

    Box(
        modifier = modifier
            .width(28.dp)
            .height(24.dp)
    ) {
        if (images.isNotEmpty()) {
            ProfileImage(
                modifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(imageClipRadius))
                    .align(if (images.size >= 2) Alignment.TopStart else Alignment.Center),
                profileImage = images[0]
            )
        }
        if (images.size >= 2) {
            ProfileImage(
                modifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(imageClipRadius))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.background,
                        RoundedCornerShape(imageClipRadius)
                    )
                    .align(Alignment.BottomEnd),
                profileImage = images[1]
            )
        }
    }
}

@Composable
fun NotificationProfiles(
    modifier: Modifier = Modifier,
    @DrawableRes images: List<Int>
) {
    val imageSize = if (images.size < 2) 40.dp else 31.dp
    val imageClipRadius = if (images.size < 2) 12.dp else 10.dp

    Box(
        modifier = modifier
            .size(40.dp)
    ) {
        if (images.isNotEmpty()) {
            ProfileImage(
                modifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(imageClipRadius))
                    .align(if (images.size >= 2) Alignment.TopStart else Alignment.Center),
                profileImage = images[0]
            )
        }
        if (images.size >= 2) {
            ProfileImage(
                modifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(imageClipRadius))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.background,
                        RoundedCornerShape(imageClipRadius)
                    )
                    .align(Alignment.BottomEnd),
                profileImage = images[1]
            )
        }
    }
}



@Composable
internal fun Profile(
    modifier: Modifier = Modifier,
    user: User,
    info: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(vertical = 16.dp)
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp)),
            painter = painterResource(id = user.profileResId),
            contentDescription = user.name,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f)
        ) {
            Text(text = user.name, style = MaterialTheme.typography.titleSmall)
            Text(
                text = info,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 0.sp
            )
        }
    }
}

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    @DrawableRes profileImage: Int
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = profileImage),
        contentDescription = "",
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
fun ProfilePreview() {
    AngKorChatProtoFeedTheme {
        Surface {
            Profile(user = sampleUsers[0], info = "test")
        }
    }
}

@Preview
@Composable
fun FeedWithProfilePreview() {
    AngKorChatProtoFeedTheme {
        Surface {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileFeedWith(images = sampleUsers.take(1).map { it.profileResId })
                ProfileFeedWith(images = sampleUsers.take(2).map { it.profileResId })
            }
        }
    }
}