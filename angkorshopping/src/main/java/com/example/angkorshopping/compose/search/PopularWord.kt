package com.example.angkorshopping.compose.search

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.angkorshopping.ui.theme.AngkorShoppingTheme

@Composable
fun PopularWord(
    word: String
) {
    Row(
        modifier = Modifier
            .padding(start = 8.dp)
            .width(58.dp)
            .height(28.dp)
            .border(
                width = 1.dp,
                color = AngkorShoppingTheme.colors.MainYellow,
                shape = RoundedCornerShape(14.dp)
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = word,
            style = AngkorShoppingTheme.typography.sansR13,
            color = AngkorShoppingTheme.colors.TextBlack
        )

    }

}


@Preview
@Composable
fun PopularWordPreview() {
    AngkorShoppingTheme {
        PopularWord("Word")
    }
}