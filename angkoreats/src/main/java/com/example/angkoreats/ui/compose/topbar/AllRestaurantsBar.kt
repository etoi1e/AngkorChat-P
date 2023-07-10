package com.example.angkoreats.ui.compose.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkoreats.R
import com.example.angkoreats.ui.compose.theme.AngkorEatsTheme

@Composable
fun AllRestaurantsBar(
    filterIcon: Painter,
    string: String,

    ) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AngkorEatsTheme.colors.background)
            .padding( top = 12.dp , bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp ),
            text = "All Restaurants",
            color = AngkorEatsTheme.colors.accent,
            style = AngkorEatsTheme.typography.head,
            fontWeight = FontWeight.Medium
        )
        Image(
            modifier = Modifier.padding(end = 16.dp),
            painter = filterIcon,
            contentDescription = string
        )


    }

}

@Preview
@Composable
fun AllRestaurantsBarPreview() {
    AngkorEatsTheme {
        AllRestaurantsBar(
            filterIcon = painterResource(id = R.drawable.ic_category_line_primary_24),
            string = "All Restaurants"
        )
    }
}
