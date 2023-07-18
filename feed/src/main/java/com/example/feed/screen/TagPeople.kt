package com.example.feed.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feed.R
import com.example.feed.compose.search.SearchBar
import com.example.feed.compose.topbar.AngkorChatFeedTopBar
import com.example.feed.sample.sampleUsers
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagPeople(
    peoples: List<User>,
    onTagButtonClicked: (List<User>) -> Unit,
    onBack: () -> Unit
) {
    val selectedPeoples = remember {
        mutableStateListOf<User>()
    }

    var search by remember {
        mutableStateOf("")
    }

    val searchResult by remember(search) {
        derivedStateOf {
            if (search.isBlank()) peoples else peoples.filter {
                it.name.lowercase().contains(search.lowercase())
            }
        }
    }

    BackHandler(onBack = onBack)

    AngKorChatProtoFeedTheme {
        Scaffold(topBar = {
            AngkorChatFeedTopBar(
                title = "Tag people",
                onBack = onBack
            ) {
                IconButton(enabled = selectedPeoples.isNotEmpty(), onClick = {
                    onTagButtonClicked(selectedPeoples)
                }) {
                    Text(
                        text = "Add",
                        color = if (selectedPeoples.isEmpty()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary
                    )
                }
            }
        }) { scaffoldPaddingValues ->
            Column(
                modifier = Modifier
                    .padding(scaffoldPaddingValues)
                    .fillMaxSize()
            ) {
                AnimatedVisibility(visible = selectedPeoples.isNotEmpty()) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        items(selectedPeoples, key = { it.id }) {
                            TagProfile(user = it) {
                                selectedPeoples.remove(it)
                            }
                        }
                    }
                }

                SearchBar(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                    search = search,
                    onSearchChange = { search = it }
                )

                LazyColumn {
                    items(searchResult, key = { it.id }) {
                        UserListItem(user = it, search = search, isChecked = it in selectedPeoples) {
                            if (it in selectedPeoples) {
                                selectedPeoples.remove(it)
                            } else {
                                selectedPeoples += it
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TagProfile(
    user: User,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onDismiss)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 7.dp
                    )
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp)),
                painter = painterResource(id = user.profileResId),
                contentDescription = user.name,
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = user.name,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Image(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp),
            painter = painterResource(id = R.drawable.ic_delate_solid_gray_20),
            contentDescription = ""
        )
    }
}

@Composable
private fun UserListItem(
    user: User,
    search: String,
    isChecked: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp)),
            painter = painterResource(id = user.profileResId),
            contentDescription = user.name,
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .weight(1f),
            text = user.name,
            style = MaterialTheme.typography.bodyLarge
        )

        IconButton(onClick = onClick) {
            AnimatedVisibility(visible = isChecked, enter = fadeIn(), exit = fadeOut()) {
                Image(
                    painter = painterResource(id = R.drawable.btn_check_circle_16),
                    contentDescription = ""
                )
            }
            AnimatedVisibility(visible = !isChecked, enter = fadeIn(), exit = fadeOut()) {
                Image(
                    painter = painterResource(id = R.drawable.btn_radio_default_16),
                    contentDescription = ""
                )
            }
        }

    }
}

@Preview
@Composable
fun TagPeoplePerview() {
    AngKorChatProtoFeedTheme {
        TagPeople(peoples = sampleUsers, onTagButtonClicked = {}, onBack = {})
    }
}