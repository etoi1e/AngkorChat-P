package com.example.bankangkorprototype.appbar

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bankangkorprototype.R
import com.example.bankangkorprototype.ui.theme.BankAngkorPrototypeTheme

internal val items = listOf(
    BottomNavigationItem(R.drawable.ic_tab_home_active_28, "Home"),
    BottomNavigationItem(R.drawable.ic_tab_finances_default_28, "Finances"),
    BottomNavigationItem(R.drawable.ic_tab_notifications_default_28, "Notifications"),
    BottomNavigationItem(R.drawable.ic_tab_more_default_28, "More"),
)

@Composable
fun BankAngkorBottomNavigationBar(
    selectedPosition: Int,
    onSelectionChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .navigationBarsPadding()
            .height(72.dp),
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
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xffbbbbc3)
    )

    Column(
        modifier = Modifier
            .weight(1f)
            .height(72.dp)
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
                modifier = Modifier
                    .padding(top = 2.dp)
                    .fillMaxWidth(),
                text = bottomNavigationItem.text,
                style = MaterialTheme.typography.labelSmall,
                color = contentColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(device = "spec:width=673.5dp,height=841dp,dpi=480")
@Composable
internal fun BottomNavigationBarPreview() {
    BankAngkorPrototypeTheme {
        BankAngkorBottomNavigationBar(1) {

        }
    }
}

data class BottomNavigationItem(
    @DrawableRes val iconResourceId: Int,
    val text: String
)