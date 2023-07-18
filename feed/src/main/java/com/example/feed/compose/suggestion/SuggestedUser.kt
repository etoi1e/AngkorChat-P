package com.example.feed.compose.suggestion

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feed.R
import com.example.feed.sample.sampleUsers
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.model.User

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuggestedUsers(
    users: List<User>,
    onFollow: (User) -> Unit,
    onMoreClick: () -> Unit
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "Suggested for You",
                style = MaterialTheme.typography.titleSmall
            )

            IconButton(
                modifier = Modifier.width(IntrinsicSize.Max),
                onClick = onMoreClick,
                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "more",
                        style = MaterialTheme.typography.labelMedium,
                        letterSpacing = (-0.3).sp,
                        lineHeight = 0.sp
                    )
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right_gray_16),
                        contentDescription = "more",
                        tint = LocalContentColor.current
                    )
                }
            }
        }

        HorizontalPager(
            pageCount = users.size,
            pageSize = PageSize.Fixed(132.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
            pageSpacing = 8.dp,
            key = {
                users[it].id
            }
        ) {
            SuggestedUser(user = users[it]) {
                onFollow(users[it])
            }
        }
    }
}

@Composable
internal fun SuggestedUser(user: User, onFollow: () -> Unit) {
    var isFollowing by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .width(132.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xfff7f7fb)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp)),
            painter = painterResource(id = user.profileResId),
            contentDescription = user.name,
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = user.name,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )

        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = user.subtitle,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        FollowButton(modifier = Modifier.padding(top = 14.dp), isFollowing = isFollowing) {
            isFollowing = true
        }
    }
}

@Composable
fun FollowButton(modifier: Modifier = Modifier, isFollowing: Boolean, onClick: () -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isFollowing) {
            MaterialTheme.colorScheme.background
        } else {
            MaterialTheme.colorScheme.primary
        }
    )

    val contentColor by animateColorAsState(
        targetValue = if (isFollowing) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.background
        }
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable(enabled = !isFollowing, onClick = onClick)
            .background(backgroundColor)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = if (isFollowing) "Following" else "Follow",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp,
            color = contentColor,
            letterSpacing = (-0.22).sp
        )
    }
}

@Preview
@Composable
fun SuggestedUserPreview() {
    AngKorChatProtoFeedTheme {
        Surface {
            SuggestedUsers(users = sampleUsers, onFollow = {}) {

            }
        }
    }
}