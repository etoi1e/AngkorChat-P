package com.example.feed.screen

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.feed.R
import com.example.feed.activity.OpenAngkorMediaPickerContract
import com.example.feed.activity.OpenCheckInContract
import com.example.feed.activity.OpenTagPeopleContract
import com.example.feed.compose.EditorVisibility
import com.example.feed.compose.content.MapQuote
import com.example.feed.compose.content.Quote
import com.example.feed.compose.divider.FeedDivider
import com.example.feed.compose.topbar.AngkorChatFeedTopBar
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.model.EditorVisibility
import com.example.model.Feed
import com.example.model.User
import com.google.android.libraries.places.api.model.Place

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FeedEditor(
    caption: String,
    visibility: EditorVisibility,
    quote: Feed?,
    onCaptionChange: (String) -> Unit,
    onEditorVisibilityChange: (EditorVisibility) -> Unit,
    onPostClick: () -> Unit,
    onRemoveQuote: () -> Unit,
    onBack: () -> Unit
) {
    val textFieldScrollState = rememberScrollableState { 1f }
    val images = remember {
        mutableStateListOf<Uri>()
    }
    val tagUsers = remember {
        mutableStateListOf<User>()
    }
    var checkIn by remember {
        mutableStateOf<Place?>(null)
    }

    val mediaPickerContract =
        rememberLauncherForActivityResult(contract = OpenAngkorMediaPickerContract()) {
            images.clear()
            images.addAll(it)
        }
    val tagUserContract =
        rememberLauncherForActivityResult(contract = OpenTagPeopleContract()) {
            tagUsers.clear()
            tagUsers.addAll(it)
        }
    val checkInContract =
        rememberLauncherForActivityResult(contract = OpenCheckInContract()) {
            checkIn = it
        }

    BackHandler(onBack = onBack)

    Scaffold(topBar = {
        AngkorChatFeedTopBar(
            title = "Create Post",
            onBack = onBack
        ) {
            IconButton(onClick = onPostClick) {
                Text(text = "Post", color = MaterialTheme.colorScheme.primary)
            }
        }
    }) { scaffoldPaddingValues ->
        LazyColumn(
            modifier = Modifier.padding(scaffoldPaddingValues)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    EditorVisibility(
                        modifier = Modifier.align(Alignment.CenterStart),
                        editorVisibility = visibility,
                        onEditorVisibilityChange = onEditorVisibilityChange
                    )

                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                        text = "${caption.length}/2000",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (caption.isEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            text = "Write a caption...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    BasicTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .aspectRatio(if (quote == null) 328f / 387f else 328f / 240f)
                            .scrollable(textFieldScrollState, Orientation.Vertical),
                        value = caption,
                        onValueChange = {
                            onCaptionChange(it.take(2000))
                        },
                        textStyle = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            if (quote != null) {
                item {
                    Column(
                        modifier = Modifier.animateItemPlacement()
                    ) {
                        Quote(feed = quote) {
                            onRemoveQuote()
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            if (checkIn != null) {
                item {
                    Column {
                        MapQuote(checkIn!!) {
                            checkIn = null
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            item {
                FeedDivider()
            }

            item {
                EditorAttachments(
                    icon = R.drawable.ic_camera_fill_gray_24,
                    name = "Photo/Video",
                    onClick = {
                        mediaPickerContract.launch(Unit)
                    }
                ) {
                    AnimatedVisibility(visible = images.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .fillMaxWidth()
                                .height(80.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(images, key = { it }) {
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .clickable {
                                            images.remove(it)
                                        }
                                ) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .size(72.dp)
                                            .clip(RoundedCornerShape(12.dp)),
                                        model = it,
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop
                                    )

                                    Image(
                                        modifier = Modifier.align(Alignment.TopEnd),
                                        painter = painterResource(id = R.drawable.ic_delate_solid_gray_20),
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                EditorAttachments(
                    icon = R.drawable.ic_user_fill_gray_24,
                    name = when {
                        tagUsers.isEmpty() -> {
                            "Tag people"
                        }

                        tagUsers.size == 1 -> {
                            tagUsers[0].name
                        }

                        else -> {
                            "${tagUsers[0].name} and ${tagUsers.size - 1} others"
                        }
                    },
                    onClick = { tagUserContract.launch(Unit) }
                )
            }

            item {
                EditorAttachments(
                    icon = R.drawable.ic_location_fill_gray_24,
                    name = checkIn?.name ?: "Check in",
                    onClick = { checkInContract.launch(Unit) }
                )
            }
        }
    }
}

@Composable
private fun EditorAttachments(
    @DrawableRes icon: Int,
    name: String,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.padding(start = 16.dp),
                    painter = painterResource(id = icon),
                    contentDescription = name
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f),
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                IconButton(onClick = onClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right_gray_16),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            content()
        }


        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 16.dp)
                .background(Color(0xffdadde3))
        )
    }
}

@Preview
@Composable
fun PostEditorPreview() {
    var caption by remember {
        mutableStateOf("")
    }

    AngKorChatProtoFeedTheme {
        FeedEditor(
            caption = caption,
            visibility = EditorVisibility.Public,
            quote = null,
            onCaptionChange = { caption = it },
            onEditorVisibilityChange = {},
            onPostClick = {},
            onRemoveQuote = {},
            onBack = {}
        )
    }
}