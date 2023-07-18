package com.example.feed.screen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feed.R
import com.example.feed.activity.OpenFeedEditorContract
import com.example.feed.compose.comment.CommentEdit
import com.example.feed.compose.comment.comments
import com.example.feed.compose.feed.FeedImages
import com.example.feed.compose.feed.FeedToolbar
import com.example.feed.compose.feed.FeedUserWithToolbar
import com.example.feed.compose.feed.FeedWith
import com.example.feed.sample.sampleFeeds
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.model.Feed
import com.example.model.User

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun FeedDetailScreen(
    feed: Feed,
    onBack: () -> Unit
) {
    val feedEditorContract =
        rememberLauncherForActivityResult(contract = OpenFeedEditorContract()) {

        }

    val commentText = rememberSaveable {
        mutableStateOf("")
    }

    val commentMention = rememberSaveable {
        mutableStateOf<User?>(null)
    }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler(onBack = onBack)

    AngKorChatProtoFeedTheme {
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    FeedUserWithToolbar(
                        user = feed.user,
                        info = feed.authorInfo,
                        onQuoteClick = { feedEditorContract.launch(feed) },
                        onHideClick = { /*TODO*/ },
                        onUnfriendClick = { /*TODO*/ }) {

                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left_line_28),
                            contentDescription = "Back"
                        )
                    }
                },
            )
        }, bottomBar = {
            CommentEdit(
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding(),
                text = commentText.value,
                mention = commentMention.value,
                focusRequester = focusRequester,
                onTextChange = {
                    commentText.value = it
                },
                onMentionRemove = {
                    commentMention.value = null
                }
            ) {
                commentText.value = ""
                commentMention.value = null
                focusRequester.freeFocus()
                keyboardController?.hide()
            }
        }) { scaffoldPaddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(scaffoldPaddingValues)
                    .imeNestedScroll()
            ) {
                item {
                    if (feed.content?.isNotBlank() == true) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = feed.content,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                item {
                    if (feed.withUser.isNotEmpty()) {
                        FeedWith(users = feed.withUser)
                    }
                }

                item {
                    if (feed.photos.isNotEmpty()) {
                        FeedImages(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(360f / 328f),
                            images = feed.photos
                        )
                    }
                }

                item {
                    Divider(color = Color(0xffedeef5))
                }

                item {
                    FeedToolbar(
                        hearts = feed.hearts,
                        comments = feed.comments.size,
                        replys = feed.replys,
                        isFavorite = feed.isFavorite,
                        onHeartsClick = {},
                        onCommentsClick = {},
                        onReplysClick = {},
                    )
                }

                comments(
                    feed.comments,
                    onHeartClick = {

                    }, onReplyClick = {
                        commentMention.value = it.user
                        focusRequester.requestFocus()
                        keyboardController?.show()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun FeedDetailPreview() {
    AngKorChatProtoFeedTheme {
        FeedDetailScreen(feed = sampleFeeds[0]) {

        }
    }
}