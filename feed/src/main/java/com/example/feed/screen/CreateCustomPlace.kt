package com.example.feed.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.feed.R
import com.example.feed.compose.button.LargeButton
import com.example.feed.compose.topbar.AngkorChatFeedTopBar
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCustomPlaceScreen(
    latLng: LatLng,
    name: String,
    onNameChange: (String) -> Unit,
    onSave: () -> Unit
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 15f)
    }

    Scaffold(
        topBar = {
            AngkorChatFeedTopBar(
                title = "Create Custom Place",
                backIcon = R.drawable.ic_close_line_28,
                onBack = {}
            ) { }
        }
    ) { scaffoldPaddingValues ->
        Box(
            modifier = Modifier
                .padding(scaffoldPaddingValues)
                .imePadding()
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Column {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(top = 16.dp),
                            text = "Place name",
                            style = MaterialTheme.typography.titleSmall
                        )

                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth()
                                .height(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xfff7f7fb)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                if (name.isEmpty()) {
                                    Text(
                                        modifier = Modifier.padding(horizontal = 8.dp),
                                        text = "Enter a place name",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                BasicTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp),
                                    value = name,
                                    onValueChange = onNameChange,
                                    textStyle = MaterialTheme.typography.bodySmall
                                )
                            }

                            Icon(
                                modifier = Modifier
                                    .padding(end = 16.dp),
                                painter = painterResource(id = R.drawable.ic_delate_solid_gray_20),
                                contentDescription = "",
                                tint = Color(0xffdadde3)
                            )
                        }
                    }
                }

                item {
                    Column {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(top = 16.dp),
                            text = "Map Location",
                            style = MaterialTheme.typography.titleSmall
                        )
                        GoogleMap(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth()
                                .aspectRatio(328f / 160f)
                                .clip(RoundedCornerShape(8.dp)),
                            cameraPositionState = cameraPositionState,
                            uiSettings = MapUiSettings(
                                compassEnabled = false,
                                indoorLevelPickerEnabled = false,
                                mapToolbarEnabled = false,
                                myLocationButtonEnabled = false,
                                rotationGesturesEnabled = false,
                                scrollGesturesEnabled = false,
                                scrollGesturesEnabledDuringRotateOrZoom = false,
                                tiltGesturesEnabled = false,
                                zoomControlsEnabled = false,
                                zoomGesturesEnabled = false
                            )
                        ) {
                            Marker(
                                state = MarkerState(position = latLng)
                            )
                        }
                    }
                }
            }

            LargeButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                text = "Save Custom Place", enabled = name.isNotBlank()
            ) {
                onSave()
            }
        }
    }
}