package com.example.angkorplayprototype.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorplayprototype.R
import com.example.angkorplayprototype.ui.theme.AngkorPlayPrototypeTheme

@Composable
fun AngkorPlayAppBar() {
    Row(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(48.dp)
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            painter = painterResource(id = R.drawable.img_logo_28),
            contentDescription = "BankAngkor"
        )

        Row(
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                painter = painterResource(id = R.drawable.ic_search_line_wh_24),
                contentDescription = "Search"
            )

            Image(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(28.dp),
                painter = painterResource(id = R.drawable.img_profile_default_28),
                contentDescription = "Profile"
            )
        }
    }
}

@Preview
@Composable
fun BankAngkorAppBarPreview() {
    AngkorPlayPrototypeTheme {
        Surface {
            AngkorPlayAppBar()
        }
    }
}