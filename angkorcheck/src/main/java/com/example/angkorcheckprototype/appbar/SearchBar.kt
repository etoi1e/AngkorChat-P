package com.example.angkorcheckprototype.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorcheckprototype.R
import com.example.angkorcheckprototype.ui.theme.AngkorCheckPrototypeTheme

@Composable
fun SearchBar(
    search: String,
    onSearchChange: (String) -> Unit,
    onFilterButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .height(48.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(24.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_search_circle_primary_32),
            contentDescription = "Search"
        )

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = search,
                onValueChange = onSearchChange,
                textStyle = MaterialTheme.typography.bodyLarge
            )

            if (search.isEmpty()) {
                BasicText(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Where to?",
                    style = MaterialTheme.typography.bodyLarge.merge(TextStyle(Color(0xff313133)))
                )
            }
        }

        Icon(
            modifier = Modifier
                .padding(end = 8.dp)
                .clip(CircleShape)
                .clickable(onClick = onFilterButtonClick)
                .padding(8.dp)
                .size(24.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_filter_line_primary_24),
            contentDescription = "Filter",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    AngkorCheckPrototypeTheme {
        Surface {
            var search by remember {
                mutableStateOf("")
            }
            SearchBar(search = search, onSearchChange = { search = it }) {

            }
        }
    }
}