package com.example.ui.compose.appbar

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorechoesprototype.R
import com.example.ui.theme.AngkorEchoesTheme

internal val items = listOf(
    BottomNavigationItem(R.drawable.ic_home_line, "Home"),
    BottomNavigationItem(R.drawable.ic_search_line, "Search"),
    BottomNavigationItem(R.drawable.ic_chart_line, "Charts"),
    BottomNavigationItem(R.drawable.ic_library_line, "Library"),
)

@Composable
fun BottomNavigationBar(
    selectedPosition: Int = 0,
    onSelectionChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(AngkorEchoesTheme.colors.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        items.forEachIndexed { index, bottomNavigationItem ->
            BottomNavigationItem(
                bottomNavigationItem = bottomNavigationItem,
                isSelected = index == selectedPosition
            ) {
                onSelectionChange(index)
            }
        }
    }
}

@Composable
internal fun RowScope.BottomNavigationItem(
    bottomNavigationItem: BottomNavigationItem,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) AngkorEchoesTheme.colors.accent else AngkorEchoesTheme.colors.textSecondary
    )

    Column(
        modifier = Modifier
            .weight(1f)
            .widthIn(max = 100.dp)
            .fillMaxHeight()
            .clickable(enabled = !isSelected, onClick = onItemClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        with(bottomNavigationItem) {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconResourceId),
                contentDescription = text,
                tint = contentColor
            )

            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = bottomNavigationItem.text,
                style = AngkorEchoesTheme.typography.caption,
                color = contentColor
            )
        }
    }
}

@Preview
@Composable
internal fun BottomNavigationBarPreview() {
    AngkorEchoesTheme {
        BottomNavigationBar {

        }
    }
}

data class BottomNavigationItem(
    @DrawableRes val iconResourceId: Int,
    val text: String
)