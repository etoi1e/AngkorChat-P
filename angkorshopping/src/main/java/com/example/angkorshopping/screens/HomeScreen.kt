package com.example.angkorshopping.screens


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.example.angkorshopping.R
import com.example.angkorshopping.compose.appbar.TopBar
import com.example.angkorshopping.compose.item.Merchandise
import com.example.angkorshopping.compose.state.MerchandiseInfo
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme


@Composable
fun HomeScreen(
    content: List<MerchandiseInfo>,
) {

    val scrollState = rememberScrollState()

    var leftList = ArrayList<MerchandiseInfo>()
    var rightList = ArrayList<MerchandiseInfo>()

    if (content.size > 1) {
        for (i in content.indices) {
            if (i % 2 == 0) {
                leftList.add(content[i])
            }
            if (i % 2 != 0) {
                rightList.add(content[i])
            }
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AngkorShoppingTheme.colors.Background),

        ) {
        TopBar()

        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            val width =
            //왼쪽그리드
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                for (item in leftList) {
                    Box(modifier = Modifier
                        .fillMaxWidth(0.5f)){
                        Merchandise(
                            content = item, modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }

                }
            }
            //오른쪽그리드
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                for (item in rightList) {
                    Box(modifier = Modifier
                        .fillMaxWidth(1f)){
                        Merchandise(
                            content = item, modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }

                }
            }


        }


    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AngkorShoppingTheme {


    }
}






