package com.example.feed.compose.topbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.feed.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AngkorChatFeedTopBar(
    title: String,
    @DrawableRes backIcon: Int = R.drawable.ic_arrow_left_line_28,
    onBack: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(id = backIcon),
                    contentDescription = "Back"
                )
            }
        },
        actions = actions
    )
}