package com.example.angkorshopping.compose.item


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorshopping.R
import com.example.angkorshopping.compose.box.CheckableCheckbox
import com.example.angkorshopping.compose.buttons.Button
import com.example.angkorshopping.compose.state.MerchandiseInfo
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme
import java.math.RoundingMode
import java.text.DecimalFormat


@Composable
fun MerchandiseInfo(
    item: MerchandiseInfo,
    checkCart: Boolean
) {
    val dataFormat = DecimalFormat("##00.00")
    dataFormat.roundingMode = RoundingMode.DOWN
    Box(
        modifier = Modifier
            .width(328.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = AngkorShoppingTheme.colors.DarkGray,
                shape = RoundedCornerShape(8.dp)
            )

    ) {
        Column(
            modifier = Modifier
                .background(AngkorShoppingTheme.colors.Background)
                .fillMaxWidth()

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(AngkorShoppingTheme.colors.LightGray),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 12.dp)
                ) {
                    Text(text = item.shopName, style = AngkorShoppingTheme.typography.sansR12)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        painter = painterResource(id = R.drawable.ic_delivery_line_lightgray_16),
                        contentDescription = ""
                    )
                    Text(text = "$2.00", style = AngkorShoppingTheme.typography.sansM12)
                }
            }

            if (checkCart) {
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp)

                    ) {
                        CheckableCheckbox()
                    }
                    Icon(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(16.dp),
                        painter = painterResource(id = R.drawable.ic_close_line_yellow_12),
                        tint = AngkorShoppingTheme.colors.DarkGray,
                        contentDescription = ""
                    )
                }
            }


            Row(
                modifier = Modifier
                    .padding(top = 28.dp, bottom = 20.dp)
                    .height(88.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .width(72.dp)
                        .fillMaxHeight(),
                    painter = painterResource(id = item.merchandiseImg),
                    contentDescription = item.merchandiseName,
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 16.dp),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = item.shopName,
                        style = AngkorShoppingTheme.typography.sansR11
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = item.merchandiseName,
                        style = AngkorShoppingTheme.typography.sansR13
                    )
                    Text(
                        text = "$${dataFormat.format(item.merchandisePrice)}",
                        style = AngkorShoppingTheme.typography.sansB17
                    )
                    Text(
                        text = "${item.merchandiseOption} / ${item.quantity}ea",
                        style = AngkorShoppingTheme.typography.sansR12
                    )
                }

            }


            if (checkCart) {
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 24.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        AngkorShoppingTheme.colors.ButtonGray,
                        AngkorShoppingTheme.colors.Background,
                        "Edit Option"
                    )
                    Box(modifier = Modifier.size(8.dp))
                    Button(
                        AngkorShoppingTheme.colors.MainYellow,
                        AngkorShoppingTheme.colors.Background,
                        "Buy Now"
                    )
                }
            }

        }


    }
}

@Preview
@Composable
fun MerchandiseInfoPreview() {
    AngkorShoppingTheme {
        val item = MerchandiseInfo(
            "AngkorMall",
            R.drawable.img_2,
            "YALE T-Shirt",
            20.00,
            "White",
            2,
            true,
            "#top #Sleeveless"
        )

        Column() {
            MerchandiseInfo(item, true)
            Box(modifier = Modifier.size(10.dp))
            MerchandiseInfo(item, false)
        }
    }
}