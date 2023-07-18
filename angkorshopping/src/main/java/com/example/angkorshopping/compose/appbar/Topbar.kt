package com.example.angkorshopping.compose.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorshopping.R
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme


@Composable
fun TopBar(
    onSearchButtonClick: () -> Unit,
    onHamburgerButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier.statusBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Shopping",
                style = AngkorShoppingTheme.typography.sansM18,
            )
        }
        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {


            Icon(
                modifier = Modifier
                    .padding(6.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onSearchButtonClick),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_search_line_bk_28),
                contentDescription = "Search",
            )

            Icon(
                modifier = Modifier
                    .padding(6.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onHamburgerButtonClick),
                painter = painterResource(id = R.drawable.ic_tap_shop_active_28),
                contentDescription = "More",
            )

            Icon(
                modifier = Modifier
                    .padding(6.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onHamburgerButtonClick),
                painter = painterResource(id = R.drawable.ic_menu_line_bk_28),
                contentDescription = "More",
            )

            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Preview
@Composable
fun TopBar() {
    AngkorShoppingTheme {
            TopBar(onSearchButtonClick = {}) {
            }
    }
}

