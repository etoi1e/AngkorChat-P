package com.example.angkorwebtoonprototype.webtoon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorwebtoonprototype.R
import com.example.angkorwebtoonprototype.ui.theme.AngkorWebtoonPrototypeTheme

data class Webtoon(
    @DrawableRes val imageResId: Int,
    val title: String,
    val writer: String
)

@Composable
fun Webtoon(
    webtoon: Webtoon,
    onWebtoonClick: () -> Unit
) {
    val image = painterResource(id = webtoon.imageResId)

    Column(
        modifier = Modifier.clickable(onClick = onWebtoonClick)
    ) {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .aspectRatio(104f / 148f),
            painter = image,
            contentDescription = webtoon.title,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = webtoon.title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = Modifier.padding(top = 3.dp),
            text = webtoon.writer,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
internal fun WebtoonPreview() {
    AngkorWebtoonPrototypeTheme {
        Surface(modifier = Modifier
            .width(104.dp)
            .padding(top = 16.dp)) {
            Webtoon(webtoon = Webtoon(
                imageResId = R.drawable.img_main_banner_h_232,
                title = "Webtoon title",
                writer = "Writer"
            )) {

            }
        }
    }
}