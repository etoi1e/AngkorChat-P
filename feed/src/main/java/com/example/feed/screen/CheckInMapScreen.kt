package com.example.feed.screen

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.feed.compose.topbar.AngkorChatFeedTopBar
import com.example.feed.util.shadow
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckInMapScreen() {
    var permissionGranted by remember {
        mutableStateOf(false)
    }
    val permissionContract = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            permissionGranted = !it.values.contains(false)
        }
    )

    LaunchedEffect(Unit) {
        permissionContract.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
    }

    Scaffold(topBar = {
        AngkorChatFeedTopBar(
            title = "Check in",
            onBack = {}
        ) { }
    }) { scaffoldPaddingValues ->
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(37.532600, 127.024612), 10f)
        }
        val clickedMarkerState = rememberMarkerState()
        if (permissionGranted) {
            GoogleMap(
                modifier = Modifier
                    .padding(scaffoldPaddingValues)
                    .fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = true),
                uiSettings = MapUiSettings(compassEnabled = true),
                onMapClick = {
                    clickedMarkerState.position = it
                    clickedMarkerState.showInfoWindow()
                }
            ) {
                MarkerInfoWindow(
                    state = clickedMarkerState,
                    onClick = {
                        false
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .width(180.dp)
                            .height(72.dp)
                            .padding(bottom = 16.dp)
                            .shadow(3.dp, RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.background),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Save this location",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
        }
    }
}