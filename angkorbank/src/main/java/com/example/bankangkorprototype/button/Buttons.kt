package com.example.bankangkorprototype.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bankangkorprototype.R
import com.example.bankangkorprototype.ui.theme.BankAngkorPrototypeTheme

@Composable
fun AddButtonLarge(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .aspectRatio(328f / 60f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xffe9e9ef))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus_line_gray_24),
            contentDescription = "Add"
        )

    }
}

@Preview
@Composable
fun AddButtonLargePreview() {
    BankAngkorPrototypeTheme {
        Surface {
            AddButtonLarge {

            }
        }
    }
}