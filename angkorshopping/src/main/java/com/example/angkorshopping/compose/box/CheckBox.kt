package com.example.angkorshopping.compose.box

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme

@Composable
fun CheckableCheckbox(
) {
    val isCheck = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .size(16.dp)
            .border(
                width = 1.dp,
                color = AngkorShoppingTheme.colors.MainYellow,
                shape = RoundedCornerShape(4.dp)
            )
            .size(25.dp)
            .background(AngkorShoppingTheme.colors.Background)
            .clickable {
                isCheck.value = !isCheck.value
            },
        contentAlignment = Alignment.Center
    ) {
        if (isCheck.value)
            Icon(
                Icons.Default.Check,
                contentDescription = "",
                tint = AngkorShoppingTheme.colors.MainYellow
            )
    }


}

@Preview
@Composable
fun CheckBoxPreview() {
    AngkorShoppingTheme {
        CheckableCheckbox()
    }
}
