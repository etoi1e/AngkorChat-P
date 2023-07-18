package com.example.feed.compose.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.material.bottomappbar.BottomAppBar

@Composable
fun BottomAppBar(
    selectedPosition: Int,
    onSelectPosition: (Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth().height(70.dp).background(Color(0xfff7f7fb))
    )
}