package com.digitalangkor.mobility.dash

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex


@Composable
fun PickupDestinationDash() {
    Canvas(modifier = Modifier.padding(start = (36 + 24).dp).height(16.dp).zIndex(-1f)) {
        drawLine(
            color = Color(0xffc8c7cc),
            start = Offset.Zero,
            end = Offset(0f, size.height),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(4.dp.toPx(), 4.dp.toPx()),
                2.dp.toPx()
            ),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}