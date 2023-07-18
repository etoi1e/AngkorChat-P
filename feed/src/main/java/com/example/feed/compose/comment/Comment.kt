package com.example.feed.compose.comment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feed.R
import com.example.feed.compose.profile.ProfileImage
import com.example.feed.sample.sampleComment
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.model.Comment
import com.example.model.User

fun LazyListScope.comments(
    comments: List<Comment>,
    onHeartClick: (Comment) -> Unit,
    onReplyClick: (Comment) -> Unit
) {
    comments(comments, false, null, onHeartClick, onReplyClick)
}

private fun LazyListScope.comments(
    comments: List<Comment>,
    isReply: Boolean,
    mentionedUser: User?,
    onHeartClick: (Comment) -> Unit,
    onReplyClick: (Comment) -> Unit
) {
    comments.forEach {
        item {
            Comment(
                comment = it,
                isReply = isReply,
                mentionedUser = mentionedUser,
                onHeartClick = { onHeartClick(it) },
                onReplyClick = { onReplyClick(it) }
            )
        }
        if (it.replies != null) {
            comments(it.replies, true, if (isReply) it.user else null, onHeartClick, onReplyClick)
        }
    }
}

@Composable
fun Comment(
    comment: Comment,
    isReply: Boolean = false,
    mentionedUser: User? = null,
    onHeartClick: () -> Unit,
    onReplyClick: () -> Unit
) {
    val profileImage = remember(comment.user) {
        comment.user.profileResId
    }
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            if (isReply) {
                Box(
                    modifier = Modifier
                        .padding(start = 59.dp)
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(Color(0xffdadde3))
                )
            }

            ProfileImage(
                modifier = Modifier
                    .padding(start = 16.dp, top = 12.dp)
                    .size(if (isReply) 28.dp else 36.dp)
                    .clip(RoundedCornerShape(12.dp)),
                profileImage = profileImage
            )

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(top = 12.dp)
                            .weight(1f),
                        text = buildAnnotatedString {
                            append(comment.user.name)
                            append(" ")
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)) {
                                append(comment.elapsedTime)
                            }
                        }, style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 16.dp, top = 8.dp),
                    text = buildAnnotatedString {
                        if (mentionedUser != null) {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("@${mentionedUser.name} ")
                            }
                        }

                        append(comment.content)
                    },
                    style = MaterialTheme.typography.bodySmall
                )

                Row(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (comment.likes > 0) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            text = "${comment.likes} Likes",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .clickable(onClick = onReplyClick),
                        text = "${comment.replies?.size ?: ""} Reply",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = (-16).dp),
            onClick = onHeartClick
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = if (comment.isLike) R.drawable.ic_heart_fill_red_24 else R.drawable.ic_heart_outline_red_24),
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
fun CommentPreview() {
    AngKorChatProtoFeedTheme {
        Surface {
            LazyColumn(
                modifier = Modifier.padding(top = 64.dp)
            ) {
                comments(listOf(sampleComment), {}, {})
            }
        }
    }
}