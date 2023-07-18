package com.example.feed.compose.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feed.R
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    hasNewNotifications: Boolean,
    onSearchClick: () -> Unit,
    onNewFeedClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "Feed", style = MaterialTheme.typography.titleLarge)
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_bk_28),
                    contentDescription = "Search"
                )
            }
            IconButton(onClick = onNewFeedClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus_square_bk_28),
                    contentDescription = "New"
                )
            }
            IconButton(onClick = onNotificationClick) {
                Image(
                    painter = painterResource(id = R.drawable.ic_bell_fill_active_28),
                    contentDescription = "Search"
                )
            }
            IconButton(onClick = onProfileClick) {
                Image(
                    painter = painterResource(id = R.drawable.img_profile_default_28),
                    contentDescription = "Search"
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Preview
@Composable
fun MainTopAppBarPreview() {
    AngKorChatProtoFeedTheme {
        MainTopAppBar(
            true,
            onSearchClick = {},
            onNewFeedClick = {},
            onNotificationClick = {},
            onProfileClick = {},
        )
    }
}