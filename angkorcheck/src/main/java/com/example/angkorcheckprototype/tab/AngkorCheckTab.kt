package com.example.angkorcheckprototype.tab

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorcheckprototype.ui.theme.AngkorCheckPrototypeTheme

@Composable
fun AngkorCheckTab(
    selectedPosition: Int,
    onSelectedPositionChange: (Int) -> Unit
) {
    val items = remember {
        listOf("Hotels", "Resorts", "Hostels", "Motel lodge")
    }

    Box(
        modifier = Modifier
            .height(42.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color(0xffefefef))
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            items.forEachIndexed { index, item ->
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
            .height(42.dp)
            .width(IntrinsicSize.Min)
            .clickable(!isSelected, onClick = onItemClick)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.Center)
                .width(IntrinsicSize.Max),
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = textColor,
            fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(barColor)
                .align(Alignment.BottomCenter)
        )
    }
}

@Preview
@Composable
internal fun AngkorCheckTabPreview() {
    AngkorCheckPrototypeTheme {
        Surface {
            AngkorCheckTab(selectedPosition = 1) {}
        }
    }
}