package com.example.feed.compose.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.feed.R

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    search: String,
    onSearchChange: (String) -> Unit
) {
    Row(
        modifier = modifier
            .height(36.dp)
            .clip(RoundedCornerShape(100))
            .background(Color(0xfff7f7fb)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(start = 12.dp),
            painter = painterResource(id = R.drawable.ic_search_lightgray_20),
            contentDescription = ""
        )

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (search.isEmpty()) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "Search",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            BasicTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                value = search,
                onValueChange = onSearchChange,
                textStyle = MaterialTheme.typography.bodySmall
            )
        }

        Icon(
            modifier = Modifier
                .padding(end = 16.dp),
            painter = painterResource(id = R.drawable.ic_delate_solid_gray_20),
            contentDescription = "",
            tint = Color(0xffdadde3)
        )
    }
}