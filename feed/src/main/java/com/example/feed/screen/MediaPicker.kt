package com.example.feed.screen

import android.Manifest
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.feed.compose.topbar.AngkorChatFeedTopBar
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import kotlinx.coroutines.launch
import java.util.PriorityQueue

sealed class Media(
    open val uri: Uri,
    open val date: Long
) {
    data class Image(
        override val uri: Uri,
        override val date: Long
    ) : Media(uri, date)

    data class Video(
        override val uri: Uri,
        override val date: Long,
        val duration: Int
    ) : Media(uri, date)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaPicker(
    onAddClick: (List<Uri>) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val contentResolver = LocalContext.current.contentResolver
    val coroutineScope = rememberCoroutineScope()
    val list = remember {
        mutableStateListOf<Media>()
    }
    val checked = remember {
        mutableStateListOf<Uri>()
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (!permissions.values.reduce { acc, b -> acc && b }) return@rememberLauncherForActivityResult
            coroutineScope.launch {
                val pictureCollection =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        MediaStore.Images.Media.getContentUri(
                            MediaStore.VOLUME_EXTERNAL
                        )
                    } else {
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }

                val videoCollection =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        MediaStore.Video.Media.getContentUri(
                            MediaStore.VOLUME_EXTERNAL
                        )
                    } else {
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }

                val pictureProjection = arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DATE_ADDED
                )

                val videoProjection = arrayOf(
                    MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.DATE_ADDED
                )

// Display videos in alphabetical order based on their display name.
                val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} ASC"

                val imageQuery = contentResolver.query(
                    pictureCollection,
                    pictureProjection,
                    null,
                    null,
                    sortOrder
                )

                val videoQuery = contentResolver.query(
                    videoCollection,
                    videoProjection,
                    null,
                    null,
                    sortOrder
                )

                val priorityQueue = PriorityQueue(
                    (imageQuery?.columnCount ?: 0) + (videoQuery?.columnCount ?: 0) + 1,
                    Comparator<Media> { o1, o2 -> o1.date.compareTo(o2.date) })

                imageQuery?.use { cursor ->
                    // Cache column indices.
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                    val dateColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)

                    while (cursor.moveToNext()) {
                        // Get values of columns for a given video.
                        val id = cursor.getLong(idColumn)
                        val date = cursor.getLong(dateColumn)
                        val contentUri: Uri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id
                        )

                        val image = Media.Image(contentUri, date)
                        priorityQueue.add(image)
                    }
                }

                videoQuery?.use { cursor ->
                    // Cache column indices.
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                    val durationColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                    val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)

                    while (cursor.moveToNext()) {
                        // Get values of columns for a given video.
                        val id = cursor.getLong(idColumn)
                        val duration = cursor.getInt(durationColumn)
                        val date = cursor.getLong(dateColumn)
                        val contentUri: Uri = ContentUris.withAppendedId(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            id
                        )

                        val video = Media.Video(contentUri, date, duration)
                        priorityQueue.add(video)
                    }
                }

                list.clear()
                list.addAll(priorityQueue)
            }
        }
    )

    LaunchedEffect(Unit) {
        launcher.launch(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO)
            } else {
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        )
    }

    BackHandler(onBack = onBack)

    AngKorChatProtoFeedTheme {
        Scaffold(topBar = {
            AngkorChatFeedTopBar(
                title = "Photo/Video",
                onBack = {}
            ) {
                IconButton(enabled = checked.isNotEmpty(), onClick = { onAddClick(checked) }) {
                    Text(
                        text = "Add",
                        color = if (checked.isEmpty()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary
                    )
                }
            }
        }) { scaffoldPaddingValues ->
            LazyVerticalGrid(
                modifier = Modifier.padding(scaffoldPaddingValues),
                columns = GridCells.Fixed(3)
            ) {
                items(list.size, key = { index ->
                    list[index].uri
                }) { index ->
                    val isChecked = list[index].uri in checked

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clickable {
                                if (isChecked) checked.remove(list[index].uri) else checked += list[index].uri
                            }
                    ) {
                        AsyncImage(
                            model = list[index].uri,
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )

                        Checkbox(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp),
                            checked = isChecked,
                            onCheckedChange = {
                                if (isChecked) checked.remove(list[index].uri) else checked += list[index].uri
                            }
                        )
                    }
                }
            }
        }
    }

}