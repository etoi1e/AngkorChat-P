package com.example.feed.compose.feed

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.feed.R
import com.example.feed.compose.profile.ProfileFeedWith
import com.example.feed.sample.sampleFeeds
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.feed.util.shorten
import com.example.model.Feed
import com.example.model.User

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Feed(
    modifier: Modifier = Modifier,
    feed: Feed,
    onQuoteClick: () -> Unit,
    onUnfriendClick: () -> Unit,
    onReportClick: () -> Unit,
    onHeartsClick: () -> Unit,
    onCommentsClick: () -> Unit,
    onReplysClick: () -> Unit
) {
    var isHidden by remember {
        mutableStateOf(false)
    }
    AnimatedContent(targetState = isHidden) {
        if (it) {
            HiddenFeed {
                isHidden = false
            }
        } else {
            Column(
                modifier = modifier.background(MaterialTheme.colorScheme.background),
            ) {
                FeedUserWithToolbar(
                    modifier = Modifier.padding(start = 16.dp),
                    user = feed.user,
                    info = feed.authorInfo,
                    onQuoteClick = onQuoteClick,
                    onHideClick = { isHidden = true },
                    onUnfriendClick = onUnfriendClick,
                    onReportClick = onReportClick
                )

                if(feed.content?.isNotBlank() == true) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = feed.content,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if(feed.withUser.isNotEmpty()) {
                    FeedWith(users = feed.withUser)
                }

                if (feed.photos.isNotEmpty()) {
                    FeedImages(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(360f / 328f),
                        images = feed.photos
                    )
                }
                
                Divider(color = Color(0xffedeef5))

                FeedToolbar(
                    hearts = feed.hearts,
                    comments = feed.comments.size,
                    replys = feed.replys,
                    isFavorite = feed.isFavorite,
                    onHeartsClick = onHeartsClick,
                    onCommentsClick = onCommentsClick,
                    onReplysClick = onReplysClick
                )
            }
        }
    }
}

@Composable
internal fun FeedUserWithToolbar(
    modifier: Modifier = Modifier,
    user: User,
    info: String,
    onQuoteClick: () -> Unit,
    onHideClick: () -> Unit,
    onUnfriendClick: () -> Unit,
    onReportClick: () -> Unit
) {
    var showPopup by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
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

        IconButton(onClick = { showPopup = true }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more_vertical_20),
                contentDescription = "More"
            )

            AnimatedVisibility(
                visible = showPopup,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Popup(
                    onDismissRequest = {
                        showPopup = !showPopup
                    }
                ) {
                    FeedPopup(
                        onQuoteClick = onQuoteClick,
                        onHideClick = onHideClick,
                        onUnfriendClick = onUnfriendClick,
                        onReportClick = onReportClick
                    )
                }
            }
        }
    }
}

@Composable
internal fun FeedWith(
    users: List<User>
) {
    val images by remember(users) {
        derivedStateOf { users.map { it.profileResId } }
    }

    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileFeedWith(images = images)
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = buildAnnotatedString {
                append("with ")
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(users[0].name)
                }
                append(" and ${users.size} others")
            },
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
internal fun FeedPopup(
    onQuoteClick: () -> Unit,
    onHideClick: () -> Unit,
    onUnfriendClick: () -> Unit,
    onReportClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp, end = 16.dp)
            .shadow(3.dp, RoundedCornerShape(8.dp), ambientColor = Color(0x29000000))
            .background(MaterialTheme.colorScheme.background)
            .clip(RoundedCornerShape(8.dp))
            .width(120.dp)
            .height(144.dp)
            .border(1.dp, Color(0xffdadde3), RoundedCornerShape(8.dp))
    ) {
        FeedPopupItem(
            imageResId = R.drawable.ic_arrow_uturn_right_bk_20,
            text = "Quote",
            onClick = onQuoteClick
        )
        FeedPopupItem(
            imageResId = R.drawable.ic_eye_slash_fill_bk_20,
            text = "Hide",
            onClick = onHideClick
        )
        FeedPopupItem(
            imageResId = R.drawable.ic_user_minus_fill_bk_20,
            text = "Unfriend",
            onClick = onUnfriendClick
        )
        FeedPopupItem(
            imageResId = R.drawable.ic_exclamation_circle_fill_red_20,
            text = "Report",
            onClick = onReportClick,
            dangerous = true
        )
    }
}

@Composable
internal fun FeedToolbar(
    hearts: Int,
    comments: Int,
    replys: Int,
    isFavorite: Boolean,
    onHeartsClick: () -> Unit,
    onCommentsClick: () -> Unit,
    onReplysClick: () -> Unit
) {
    val heartImage =
        if (isFavorite) {
            R.drawable.ic_heart_fill_red_24
        } else {
            R.drawable.ic_heart_outline_red_24
        }

    val commentImage = R.drawable.ic_chat_24
    val replyImage = R.drawable.ic_share_line_24

    Row(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        FeedToolbarItem(icon = heartImage, number = hearts, onClick = onHeartsClick)
        FeedToolbarItem(icon = commentImage, number = comments, onClick = onCommentsClick)
        FeedToolbarItem(icon = replyImage, number = replys, onClick = onReplysClick)
    }
}

@Composable
internal fun FeedToolbarItem(
    @DrawableRes icon: Int,
    number: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = icon), contentDescription = "")
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = number.shorten(),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
internal fun FeedPopupItem(
    @DrawableRes imageResId: Int,
    text: String,
    dangerous: Boolean = false,
    onClick: () -> Unit
) {
    val image = ImageVector.vectorResource(id = imageResId)
    val contentColor =
        if (dangerous) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground

    Row(
        modifier = Modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(8.dp),
            imageVector = image,
            contentDescription = text,
            tint = contentColor
        )

        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = contentColor
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun FeedImages(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    images: List<Int>
) {

    val pagerState = rememberPagerState()

    Box(
        modifier = modifier
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = contentPadding,
            pageCount = images.size,
            pageSpacing = contentPadding.calculateLeftPadding(LocalLayoutDirection.current),
            state = pagerState
        ) { position ->
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(id = images[position]),
                contentDescription = "Hotel image ${position + 1}",
                contentScale = ContentScale.Crop
            )
        }

        if (images.size > 1) {
            Indicator(
                modifier = Modifier.padding(end = contentPadding.calculateEndPadding(LocalLayoutDirection.current)),
                currentPage = pagerState.currentPage,
                maxPage = images.size
            )
        }
    }
}

@Composable
private fun BoxScope.Indicator(
    modifier: Modifier = Modifier,
    currentPage: Int,
    maxPage: Int
) {
    Box(
        modifier = modifier
            .align(Alignment.TopEnd)
            .padding(end = 8.dp, top = 8.dp)
            .clip(RoundedCornerShape(100))
            .background(
                Color.Black.copy(alpha = 0.6f)
            )
            .padding(vertical = 2.dp, horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${currentPage + 1}/$maxPage",
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}

@Composable
internal fun HiddenFeed(
    onUndo: () -> Unit
) {
    Column(
        modifier = Modifier
            .height(151.dp)
            .fillMaxWidth()
            .background(Color(0xfff7f7fb)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "This post has been hidden.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xff555555)
        )

        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .width(78.dp)
                .height(32.dp)
                .clip(RoundedCornerShape(100))
                .background(Color(0xff555555))
                .clickable(onClick = onUndo),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Undo",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Preview
@Composable
internal fun FeedPopupPreview() {
    AngKorChatProtoFeedTheme {
        Surface {
            Box(modifier = Modifier.padding(16.dp)) {
                FeedPopup(
                    onQuoteClick = { /*TODO*/ },
                    onHideClick = { /*TODO*/ },
                    onUnfriendClick = { /*TODO*/ }) {

                }
            }
        }
    }
}

@Preview
@Composable
internal fun FeedPreview() {
    AngKorChatProtoFeedTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
            }
        }
    }
}