package com.example.angkorshopping.compose.item


import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.angkorshopping.DetailActivity
import com.example.angkorshopping.R
import com.example.angkorshopping.compose.state.MerchandiseInfo
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme

@Composable
fun Merchandise(
    content: MerchandiseInfo,
    modifier : Modifier
) {
    val context = LocalContext.current
    val intent = Intent(context, DetailActivity::class.java)

    Column(
        modifier = Modifier
            .clickable {
                intent.putExtra("shopName", content.shopName)
                intent.putExtra("imgNumber", content.imgNumber)
                intent.putExtra("img", content.merchandiseImg)
                intent.putExtra("name", content.merchandiseName)
                intent.putExtra("price", content.merchandisePrice)
                intent.putExtra("option", content.merchandiseOption)
                intent.putExtra("quantity", content.quantity)
                intent.putExtra("pickup", content.pickup)
                intent.putExtra("tags", content.tags)

                context.startActivity(intent)
            }
    ) {
        Image(
            modifier = modifier,
            painter = painterResource(id = content.merchandiseImg),
            contentDescription = content.merchandiseName,
            contentScale = ContentScale.FillWidth
        )
    }


}
