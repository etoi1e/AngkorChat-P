package com.example.angkorgamesprototype.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorgamesprototype.R
import com.example.angkorgamesprototype.ui.theme.AngkorGamesPrototypeTheme

@Composable
fun AngkorGamesAppBar(
    onSearchButtonClick: () -> Unit,
    onHamburgerButtonClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
                text = "AngkorGames",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Icon(
                modifier = Modifier
                    .padding(6.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onSearchButtonClick),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.primary
            )

            Icon(
                modifier = Modifier
                    .padding(6.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onSearchButtonClick),
                painter = painterResource(id = R.drawable.ic_menu_line),
                contentDescription = "More",
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(10.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffe5e5e5))
        )
    }
}

@Preview
@Composable
fun AngkorGamesAppBarPreview() {
    AngkorGamesPrototypeTheme {
        Surface {
            AngkorGamesAppBar(onSearchButtonClick = {}) {

            }
        }
    }
}