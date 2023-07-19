package com.example.angkorshopping


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.angkorshopping.compose.state.MerchandiseInfo
import com.example.angkorshopping.screens.DetailScreen
import com.example.angkorshopping.ui.theme.AngkorShoppingTheme

class DetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AngkorShoppingTheme {



                val shopName = intent.getStringExtra("shopName").toString()
                val imgNumber = intent.getStringExtra("imgNumber").toString()
                val img = intent.getIntExtra("img", R.drawable.img_1_1)
                val name = intent.getStringExtra("name").toString()
                val price = intent.getDoubleExtra("price", 0.0)
                val option = intent.getStringExtra("option").toString()
                val quantity = intent.getIntExtra("quantity", 0)
                val pickup = intent.getBooleanExtra("pickup", false)
                val tags = intent.getStringExtra("tags").toString()

                val merchandise = MerchandiseInfo(
                    shopName,
                    imgNumber,
                    img,
                    name,
                    price,
                    option,
                    quantity,
                    pickup,
                    tags
                )


                DetailScreen(merchandise) { this@DetailActivity.finish() }

            }
        }
    }

}

