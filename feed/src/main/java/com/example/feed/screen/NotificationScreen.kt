package com.example.feed.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.feed.compose.notification.Notification
import com.example.feed.compose.topbar.AngkorChatFeedTopBar
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.model.Notification

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    notifications: List<Notification>,
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)

    AngKorChatProtoFeedTheme {
        Scaffold(topBar = {
            AngkorChatFeedTopBar(title = "Notifications", onBack = { /*TODO*/ })
        }) { scaffoldPaddingValues ->
            LazyColumn(modifier = Modifier.padding(scaffoldPaddingValues)) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = buildAnnotatedString {
                                append("Activities ")

                                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)) {
                                    append(notifications.size.toString())
                                }
                            },
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                items(notifications, key = {
                    it.hashCode()
                }) {
                    Notification(notification = it)
                }
            }
        }
    }
}