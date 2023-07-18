package com.example.angkorshopping.compose.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme

@Composable
fun Button(
    buttonStrokeColor : Color,
    buttonBackgroundColor : Color,
    buttonText : String
) {
    Box(
        modifier = Modifier
            .width(124.dp)
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = buttonStrokeColor,
                shape = RoundedCornerShape(16.dp))
            .background(buttonBackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(text = buttonText,
            style = AngkorShoppingTheme.typography.sansR12,
            color = buttonStrokeColor)
    }

}

@Preview
@Composable
fun ButtonPreview(){
    AngkorShoppingTheme {
            Button(AngkorShoppingTheme.colors.ButtonGray,AngkorShoppingTheme.colors.Background,"Edit Option")
    }
}
