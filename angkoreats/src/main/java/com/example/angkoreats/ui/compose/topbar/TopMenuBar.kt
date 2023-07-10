package com.example.angkoreats.ui.compose.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.angkoreats.R
import com.example.angkoreats.ui.compose.theme.AngkorEatsTheme

@Composable
fun TopMenuBar(
    menuIcon: Painter,
    type: String,
    downArrow: Painter,
    noticeIcon: Painter
) {
    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(AngkorEatsTheme.colors.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(24.dp),
            painter = menuIcon,
            contentDescription = "menuIcon",
            tint = AngkorEatsTheme.colors.accent
        )

        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "BBQ Park", style = AngkorEatsTheme.typography.body2, fontSize = 18.sp)

            Image(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(24.dp),
                painter = downArrow,
                contentDescription = "downArrow"
            )

        }

        Image(
            modifier = Modifier
                .padding(end = 16.dp)
                .size(24.dp),
            painter = noticeIcon,
            contentDescription = "noticeIcon"
        )

    }
}

@Preview
@Composable
fun TopMenuBarPreview() {
    AngkorEatsTheme {
        TopMenuBar(
            menuIcon = painterResource(id = R.drawable.ic_menu_line_primary_24),
            type = "Address",
            downArrow = painterResource(id = R.drawable.ic_arrow_down_line_primary_24),
            noticeIcon = painterResource(id = R.drawable.ic_bell_line_default_primary_24)
        )
    }
}

