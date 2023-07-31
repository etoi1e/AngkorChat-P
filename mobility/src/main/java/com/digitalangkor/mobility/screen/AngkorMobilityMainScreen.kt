package com.digitalangkor.mobility.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digitalangkor.mobility.R
import com.digitalangkor.mobility.sheet.AngkorMobilityBottomSheet
import com.digitalangkor.mobility.topbar.AngkorMobilityTopBar
import com.digitalangkor.mobility.ui.theme.AngKorMobilityTheme
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

data class MyLocation(
    val accuracy: Double,
    val lat: Double,
    val lng: Double
)

@Preview
@OptIn(ExperimentalMaterial3Api::class, MapsComposeExperimentalApi::class)
@Composable
fun AngkorMobilityMainScreen() {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(37.532600, 127.024612), 10f)
    }
    var myLocation by remember {
        mutableStateOf<MyLocation?>(null)
    }
    val myLocationMarkerState by remember(myLocation) {
        mutableStateOf(
            MarkerState(
                position = LatLng(
                    myLocation?.lat ?: 0.0,
                    myLocation?.lng ?: 0.0
                )
            )
        )
    }

    AngKorMobilityTheme {
        Scaffold(topBar = {
            AngkorMobilityTopBar()
        },
            bottomBar = {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    /*Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .iconShadow(
                                color = Color(0x1a000000),
                                offsetY = 4.dp,
                                blurRadius = 4.dp
                            )
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.background)
                            .clickable {

                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier,
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = ""
                        )
                    }*/

                    AngkorMobilityBottomSheet()
                }
            }
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = true),
                uiSettings = MapUiSettings(
                    compassEnabled = true,
                    myLocationButtonEnabled = true,
                    zoomControlsEnabled = false
                ),
                contentPadding = PaddingValues(
                    start = 4.dp,
                    end = 4.dp,
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding() - 56.dp
                )
            ) {
                MapEffect {
                    it.setOnMyLocationChangeListener {
                        myLocation = MyLocation(
                            it.accuracy.toDouble(),
                            it.latitude,
                            it.longitude
                        )
                    }
                }

                if (myLocation != null) {
                    Marker(
                        state = myLocationMarkerState,
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.img_current_location),
                        zIndex = 100f,
                        anchor = Offset(0.5f, 0.5f)
                    )

                    Circle(
                        center = myLocationMarkerState.position,
                        radius = myLocation!!.accuracy,
                        fillColor = Color(0x338f00ff),
                        strokeColor = Color.Transparent,
                        strokeWidth = 0f,
                        zIndex = 99f
                    )
                }
            }
        }
    }

}