package com.example.angkorwebtoonprototype.tab

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorwebtoonprototype.ui.theme.AngkorWebtoonPrototypeTheme

@Composable
fun AngkorWebtoonTab(
    selectedPosition: Int,
    onSelectedPositionChange: (Int) -> Unit
) {
    val items = remember {
        listOf("NEW", "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
    }

    Box(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color(0xffefefef))
        )

        LazyRow(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            itemsIndexed(items, key = { _, item -> item }) { index, item ->
                TabItem(text = item, isSelected = index == selectedPosition) {
                    onSelectedPositionChange(index)
                }
            }
        }
    }
}

@Composable
internal fun TabItem(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    val textColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
    )
    val barColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    )

    Box(
        modifier = modifier
            .height(48.dp)
            .width(IntrinsicSize.Min)
            .clickable(!isSelected, onClick = onItemClick),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 12.dp),
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = textColor
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(barColor)
        )
    }
}

@Preview
@Composable
internal fun AngkorWebtoonTabPreview() {
    AngkorWebtoonPrototypeTheme {
        Surface {
            AngkorWebtoonTab(selectedPosition = 1) {}
        }
    }
}