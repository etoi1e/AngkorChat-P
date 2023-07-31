package com.digitalangkor.mobility.recent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.digitalangkor.mobility.R

@Composable
fun RecentItem(
    title: String,
    description: String
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(72.dp)
        .background(Color(0xfffafafa))
        .clickable { }
        .padding(horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.ic_clock), contentDescription = "")

        Column {
            Text(text = title, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xff656565)
            )
        }
    }
}