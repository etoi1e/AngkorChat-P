package com.example.bankangkorprototype.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bankangkorprototype.R
import com.example.bankangkorprototype.ui.theme.BankAngkorPrototypeTheme

@Composable
fun BankAngkorAppBar() {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(48.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Image(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterStart),
            painter = painterResource(id = R.drawable.img_bank_angkor_symbol_28),
            contentDescription = "BankAngkor"
        )

        Image(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(28.dp)
                .align(Alignment.CenterEnd),
            painter = painterResource(id = R.drawable.img_profile_default_28),
            contentDescription = "Profile"
        )
    }
}

@Preview
@Composable
fun BankAngkorAppBarPreview() {
    BankAngkorPrototypeTheme {
        Surface {
            BankAngkorAppBar()
        }
    }
}