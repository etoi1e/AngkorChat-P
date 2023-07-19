package com.example.feed.screen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feed.activity.OpenFeedDetailContract
import com.example.feed.activity.OpenFeedEditorContract
import com.example.feed.activity.OpenNotificationContract
import com.example.feed.compose.feed.Feed
import com.example.feed.compose.story.StoryCovers
import com.example.feed.compose.suggestion.SuggestedUsers
import com.example.feed.compose.topbar.MainTopAppBar
import com.example.feed.sample.sampleFeeds
import com.example.feed.sample.sampleStoryCovers
import com.example.feed.sample.sampleUsers
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.model.Feed
import com.example.model.StoryCover
import com.example.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    feeds: List<Feed>,
    storyCovers: List<StoryCover>,
    suggestedUsers: List<User>,
    onBack: () -> Unit,
) {
    val feedEditorContract =
        rememberLauncherForActivityResult(contract = OpenFeedEditorContract()) {

        }
    val feedDetailContract =
        rememberLauncherForActivityResult(contract = OpenFeedDetailContract()) {

        }
    val notificationContract =
        rememberLauncherForActivityResult(contract = OpenNotificationContract()) {

        }

    BackHandler(onBack = onBack)

    AngKorChatProtoFeedTheme {
        Scaffold(topBar = {
            MainTopAppBar(
                hasNewNotifications = true,
                onSearchClick = {},
                onNewFeedClick = { feedEditorContract.launch(null) },
                onNotificationClick = { notificationContract.launch(Unit) },
                onProfileClick = {}
            )
        }) { scaffoldPaddingValues ->
            val feedSliced by remember(feeds) {
                derivedStateOf { feeds.take(3) to feeds - feeds.take(3).toSet() }
            }

            LazyColumn(
                modifier = Modifier
                    .background(Color(0xffdadde3))
                    .padding(scaffoldPaddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    StoryCovers(storyCovers = storyCovers)
                }

                items(feedSliced.first, key = { it.id }) {
                    Feed(
                        modifier = Modifier.clickable {
                            feedDetailContract.launch(it)
                        },
                        feed = it,
                        onQuoteClick = { feedEditorContract.launch(it) },
                        onUnfriendClick = { /*TODO*/ },
                        onReportClick = { /*TODO*/ },
                        onHeartsClick = { /*TODO*/ },
                        onCommentsClick = { /*TODO*/ },
                        onReplysClick = { /*TODO*/ },
                    )
                }

                item {
                    SuggestedUsers(users = suggestedUsers, onFollow = { }, onMoreClick = { })
                }

                items(feedSliced.second, key = { it.id }) {
                    Feed(
                        modifier = Modifier.clickable {
                            feedDetailContract.launch(it)
                        },
                        feed = it,
                        onQuoteClick = { feedEditorContract.launch(it) },
                        onUnfriendClick = { /*TODO*/ },
                        onReportClick = { /*TODO*/ },
                        onHeartsClick = { /*TODO*/ },
                        onCommentsClick = { /*TODO*/ },
                        onReplysClick = { /*TODO*/ },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    FeedScreen(
        feeds = sampleFeeds,
        storyCovers = sampleStoryCovers,
        suggestedUsers = sampleUsers,
        onBack = {}
    )
}