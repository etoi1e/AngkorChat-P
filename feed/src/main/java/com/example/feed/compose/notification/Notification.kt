package com.example.feed.compose.notification

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.feed.compose.profile.NotificationProfiles
import com.example.model.Notification
import com.example.model.User

@Composable
fun Notification(
    notification: Notification
) {
    with(notification) {
        val images by remember(users) {
            derivedStateOf { users.map { it.profileResId } }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NotificationProfiles(images = images)
            Text(
                modifier = Modifier.weight(1f),
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                        append(
                            if(users.size > 1) {
                                "${users[0].name} and ${users.size - 1} others"
                            } else {
                                users[0].name
                            }
                        )
                    }
                    append(" ")
                    append(message)
                    withStyle(SpanStyle(color = Color(0xff929498))) {
                        append(" ${elapsedTimeMinute}m")
                    }
                },
                style = MaterialTheme.typography.bodyMedium
            )

            if(image != null) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = image),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}