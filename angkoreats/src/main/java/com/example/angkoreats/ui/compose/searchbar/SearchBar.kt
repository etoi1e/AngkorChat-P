package com.example.angkoreats.ui.compose.searchbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkoreats.R
import com.example.angkoreats.ui.compose.theme.AngkorEatsTheme

@Composable
fun SearchBar(
    icon: Painter,
    hint: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
            .background(AngkorEatsTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(
                    color = AngkorEatsTheme.colors.searchBox,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 6.dp , end = 7.dp )
                    .size(24.dp),
                painter = icon,
                contentDescription = "menuIcon"
            )

            Text(
                text = "Search food and restaurants",
                style= AngkorEatsTheme.typography.search,
                color = AngkorEatsTheme.colors.textSecond)
        }


    }

}

@Preview
@Composable
fun SearchBarPreview() {
    AngkorEatsTheme {
        SearchBar(
            icon = painterResource(id = R.drawable.ic_search_primary_24),
            hint = "Search food and restaurants"
        )
    }
}

