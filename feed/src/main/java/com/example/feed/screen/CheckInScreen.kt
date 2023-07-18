package com.example.feed.screen

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feed.R
import com.example.feed.compose.search.SearchBar
import com.example.feed.compose.topbar.AngkorChatFeedTopBar
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.feed.util.shorten
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest

private val placeFields: List<Place.Field> =
    listOf(Place.Field.NAME, Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG)
private val request = FindCurrentPlaceRequest.newInstance(placeFields)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun CheckInScreen(
    onBack: () -> Unit,
    onCheckIn: (Place) -> Unit
) {
    val context = LocalContext.current
    val placesClient = remember {
        Places.createClient(context)
    }
    var search by remember {
        mutableStateOf("")
    }
    val nearby = remember {
        mutableStateListOf<PlaceLikelihood>()
    }

    val nearbyContract = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            if (it.values.contains(false)) return@rememberLauncherForActivityResult

            val placeResponse = placesClient.findCurrentPlace(request)
            placeResponse.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val response = task.result
                    nearby.clear()
                    nearby.addAll(
                        response?.placeLikelihoods?.filter { it.place.id != null }?.take(5)
                            ?: emptyList()
                    )
                } else {
                    val exception = task.exception
                    if (exception is ApiException) {
                        Log.e("GoogleMap", "Place not found: ${exception.statusCode}")
                    }
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        nearbyContract.launch(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        )
    }

    BackHandler(onBack = onBack)

    AngKorChatProtoFeedTheme {
        Scaffold(
            topBar = {
                AngkorChatFeedTopBar(
                    title = "Check in",
                    onBack = onBack
                ) { }
            }
        ) { scaffoldPaddingValues ->
            Column(modifier = Modifier.padding(scaffoldPaddingValues)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchBar(modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                        search = search,
                        onSearchChange = { search = it })

                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .height(36.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .clickable(onClick = { /* TODO */ }),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                LazyColumn {
                    items(nearby, key = { it.place.id!! }) {
                        Place(
                            name = it.place.name!!,
                            address = it.place.address!!,
                            checkIns = 1002,
                            onClick = { onCheckIn(it.place) }) {

                        }
                    }

                    item {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clickable {
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier.padding(start = 16.dp),
                                    painter = painterResource(id = R.drawable.ic_plus_circle_line_bk_24),
                                    contentDescription = ""
                                )
                                Text(
                                    modifier = Modifier.padding(horizontal = 2.dp),
                                    text = if (search.isBlank()) "Add a new place" else """Add "$search"""",
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(end = 16.dp)
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Color(0xfff7f7fb))
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Place(
    name: String,
    address: String,
    checkIns: Int,
    onClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    Box {
        Column(
            modifier = Modifier
                .clickable(onClick = onClick)
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = address,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                text = "${checkIns.shorten()} check-ins",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = onMoreClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more_vertical_20),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(end = 16.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xfff7f7fb))
        )
    }

}