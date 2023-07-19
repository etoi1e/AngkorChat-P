package com.example.angkorshopping.compose.appbar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.BottomSheetScaffold
//import androidx.compose.material.BottomSheetState
//import androidx.compose.material.BottomSheetValue
//import androidx.compose.material.Card
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorshopping.R
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme
import kotlinx.coroutines.launch

@Composable
fun BuyBottomBar(
) {

    val coroutineScope = rememberCoroutineScope()
    Column() {
        Row(
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
                .background(AngkorShoppingTheme.colors.Background)
                .padding(top = 8.dp, start = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .width(72.dp)
                    .height(44.dp)
                    .border(
                        width = 1.dp,
                        color = AngkorShoppingTheme.colors.DarkGray,
                        shape = RoundedCornerShape(22.dp)
                    ),
                colors = ButtonDefaults.buttonColors(AngkorShoppingTheme.colors.Background),
                onClick = { }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_heart_active_red_24),
                    contentDescription = ""
                )
            }

            Button(
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .fillMaxWidth()
                    .height(44.dp)
                    .border(
                        width = 1.dp,
                        color = AngkorShoppingTheme.colors.LightGray,
                        shape = RoundedCornerShape(22.dp)
                    ),
                colors = ButtonDefaults.buttonColors(AngkorShoppingTheme.colors.MainYellow),
                onClick = {

                }) {
                Text(
                    text = "Buy",
                    style = AngkorShoppingTheme.typography.sansM17,
                    color = AngkorShoppingTheme.colors.Background
                )
            }
        }


    }


}

@Preview
@Composable
fun BuyBottomBarPreview() {
    AngkorShoppingTheme {
        BuyBottomBar()
    }
}