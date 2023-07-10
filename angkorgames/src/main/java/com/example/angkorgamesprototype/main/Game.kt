package com.example.angkorgamesprototype.main

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorgamesprototype.R
import com.example.angkorgamesprototype.ui.theme.AngkorGamesPrototypeTheme

data class Game(
    @DrawableRes val imageResourceId: Int,
    val name: String,
    val description: String
)

@Composable
fun Game(
    game: Game,
    onPlayClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Image(
            modifier = Modifier
                .size(width = 140.dp, height = 96.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = game.imageResourceId),
            contentDescription = game.name
        )

        Column(
            modifier = Modifier
                .height(96.dp)
                .padding(start = 8.dp)
        ) {
            Text(
                text = game.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .weight(1f),
                text = game.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Ellipsis
            )
            Button(
                modifier = Modifier.size(width = 110.dp, height = 32.dp),
                onClick = onPlayClick,
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues()
            ) {
                Text(
                    text = "Play",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview
@Composable
fun GamePreview() {
    AngkorGamesPrototypeTheme {
        Surface {
            Game(
                game = Game(
                    imageResourceId = R.drawable.img_pre_order_battlegrounds_h_96,
                    name = "Battle grounds",
                    description = "Let’s enjoy playing fun games together!"
                )
            ) {

            }
        }
    }
}