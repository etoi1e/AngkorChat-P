package com.example.angkorechoesprototype.ui.compose.nowplaying

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AngkorEchoesTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NowPlayingSlider(
    songDurationMs: Long,
    songCurrentDurationMs: Long,
    isChangeDurationAvailable: Boolean,
    onDurationChange: (Long) -> Unit
) {
    val sliderHorizontalPadding by animateDpAsState(targetValue = if (isChangeDurationAvailable) 24.dp else 0.dp)
    val sliderVerticalPadding by animateDpAsState(targetValue = if (isChangeDurationAvailable) 14.dp else 0.dp)
    val sliderThumbSize by animateDpAsState(targetValue = if (isChangeDurationAvailable) 12.dp else 0.dp)

    val sliderHeight by animateDpAsState(targetValue = if (isChangeDurationAvailable) 4.dp else 2.dp)
    val sliderInactiveTrackColor by animateColorAsState(
        targetValue = if (isChangeDurationAvailable) Color(0xff202020) else Color(0xffbbbbbb)
    )
    val sliderRound by animateDpAsState(targetValue = if (isChangeDurationAvailable) 2.dp else 0.dp)

    val songDurationPercentage = remember(songDurationMs, songCurrentDurationMs) {
        songCurrentDurationMs / songDurationMs.toFloat()
    }

    var absoluteSliderWidth by remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier.padding(
            vertical = sliderVerticalPadding
        )
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = sliderHorizontalPadding)
                    .clip(RoundedCornerShape(sliderRound))
                    .fillMaxWidth()
                    .height(sliderHeight)
                    .background(sliderInactiveTrackColor)
                    .onGloballyPositioned {
                        absoluteSliderWidth = it.size.width
                    }
                    .pointerInput(Unit) {
                        detectTapGestures(onPress = {
                            onDurationChange((it.x / absoluteSliderWidth * songDurationMs).toLong())
                        })
                    }
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = sliderHorizontalPadding)
                    .clip(RoundedCornerShape(sliderRound))
                    .fillMaxWidth(songDurationPercentage)
                    .height(sliderHeight)
                    .background(AngkorEchoesTheme.colors.accent)
            )
            Box(
                modifier = Modifier
                    .offset {
                        return@offset IntOffset(
                            x = (18.dp.toPx() + absoluteSliderWidth * songDurationPercentage).toInt(),
                            y = 0
                        )
                    }
                    .size(sliderThumbSize)
                    .clip(CircleShape)
                    .background(AngkorEchoesTheme.colors.accent)
            )
        }

        AnimatedVisibility(visible = isChangeDurationAvailable) {
            Row(
                modifier = Modifier
                    .padding(horizontal = sliderHorizontalPadding)
                    .padding(top = 4.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = songCurrentDurationMs.formatToDuration(),
                    style = AngkorEchoesTheme.typography.captionLarge,
                    color = AngkorEchoesTheme.colors.accent,
                    textAlign = TextAlign.Start
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = songDurationMs.formatToDuration(),
                    style = AngkorEchoesTheme.typography.captionLarge,
                    color = AngkorEchoesTheme.colors.textSecondary,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Preview
@Composable
fun SliderPreview() {
    AngkorEchoesTheme {
        var duration by remember {
            mutableStateOf(12345L)
        }
        var isChangeDurationAvailable by remember {
            mutableStateOf(true)
        }
        LaunchedEffect(Unit) {
            while (true) {
                duration += 1000
                delay(1000)
            }
        }
        Surface(
            color = AngkorEchoesTheme.colors.background,
            modifier = Modifier.clickable {
                isChangeDurationAvailable = !isChangeDurationAvailable
            }) {
            NowPlayingSlider(
                songDurationMs = 67890L,
                songCurrentDurationMs = duration,
                isChangeDurationAvailable = isChangeDurationAvailable,
                onDurationChange = {
                    duration = it
                }
            )
        }
    }

}


internal fun Long.formatToDuration(): String {
    val stringBuilder = StringBuilder()

    var rest = this

    if (this >= 3600 * 1000) {
        val hour = rest / (3600 * 1000)
        stringBuilder.append(hour)
        stringBuilder.append(":")
        rest -= hour * (3600 * 1000)
    }

    val minute = rest / (60 * 1000)

    if (minute < 10 && this >= 3600 * 1000) stringBuilder.append('0')
    stringBuilder.append(minute)
    stringBuilder.append(":")

    rest -= minute * (60 * 1000)
    val second = rest / 1000

    if (second < 10) stringBuilder.append('0')
    stringBuilder.append(second)

    return stringBuilder.toString()
}