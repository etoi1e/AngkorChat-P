package com.example.angkorechoesprototype.ui.compose.nowplaying

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorechoesprototype.R
import com.example.ui.theme.AngkorEchoesTheme

@SuppressLint("UnrememberedMutableState")
@Composable
fun animateAlignmentAsState(
    targetAlignment: Alignment,
): State<Alignment> {
    val biased = targetAlignment as BiasAlignment
    val horizontal by animateFloatAsState(biased.horizontalBias)
    val vertical by animateFloatAsState(biased.verticalBias)
    return derivedStateOf { BiasAlignment(horizontal, vertical) }
}

@Composable
fun NowPlayingControl(
    expanded: Boolean
) {
    val iconSize by animateDpAsState(targetValue = if (expanded) 36.dp else 24.dp)
    val buttonPadding by animateDpAsState(targetValue = if (expanded) 24.dp else 0.dp)

    Row {
        AnimatedVisibility(visible = expanded) {
            IconButton(modifier = Modifier.padding(start = 16.dp), onClick = {}) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_music_controller_shuffle_wh_24),
                    contentDescription = "Previous",
                    tint = AngkorEchoesTheme.colors.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f, fill = expanded))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(buttonPadding)
        ) {

            IconButton(onClick = {}) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_music_controller_skip_pre),
                    contentDescription = "Previous",
                    tint = AngkorEchoesTheme.colors.onBackground
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_music_controller_pause),
                    contentDescription = "Play/Pause",
                    tint = AngkorEchoesTheme.colors.onBackground
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_music_controller_skip_next),
                    contentDescription = "Next",
                    tint = AngkorEchoesTheme.colors.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f, fill = expanded))

        AnimatedVisibility(visible = expanded) {
            IconButton(modifier = Modifier.padding(end = 16.dp), onClick = {}) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_music_controller_repeat_wh_24),
                    contentDescription = "Previous",
                    tint = AngkorEchoesTheme.colors.onBackground
                )
            }
        }
    }
}

@Preview
@Composable
fun NowPlayingControlPreview() {
    AngkorEchoesTheme {
        var expanded by remember {
            mutableStateOf(false)
        }

        Surface(
            color = AngkorEchoesTheme.colors.background,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                }) {
            NowPlayingControl(expanded = expanded)
        }
    }
}