package com.example.angkoreats.ui.compose.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.angkoreats.R
import com.example.angkoreats.ui.color.textSecond
import com.example.angkoreats.ui.compose.theme.AngkorEatsTheme

@Composable
fun CategoryBar(
    img: Painter,
    category: String
) {
    Column(
        modifier = Modifier
            .background(AngkorEatsTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(72.dp),
            painter = img, contentDescription = category
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = category, color = textSecond,
            style = AngkorEatsTheme.typography.body2,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.sp
        )

    }


}

@Preview
@Composable
fun CategoryBarPreview() {
    AngkorEatsTheme {
        CategoryBar(
            img = painterResource(id = R.drawable.img_category_pizza_72),
            category = "Pizza"
        )
    }
}

