package com.example.angkorshopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.angkorshopping.compose.state.MerchandiseInfo
import com.example.angkorshopping.screens.HomeScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val homeItemList = listOf<MerchandiseInfo>(
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_1,
                    "Top Banding Sleeveless",
                    15.00,
                    "Black",
                    1,
                    true,
                    "#Top #Sleeveless"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_2,
                    "YALE White T-Shirts",
                    27.00,
                    "White",
                    1,
                    true,
                    "#Top #T-Shirts"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_3,
                    "Asics white sneakers with green line ",
                    58.00,
                    "White",
                    1,
                    true,
                    "#Shoes #Sneakers"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_4,
                    "Black long Sleeve",
                    8.00,
                    "Black",
                    1,
                    true,
                    "#Top #long-Sleeve"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_5,
                    "Mini Dress Black and SkyBlue",
                    30.00,
                    "Black/SkyBlue",
                    1,
                    true,
                    "#Dress #Mini-Dress"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_6,
                    "Converse White low",
                    30.00,
                    "White",
                    1,
                    true,
                    "#Shoes #Converse"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_7,
                    "Denim skirt mini",
                    20.00,
                    "Blue",
                    1,
                    true,
                    "#Skirt #Denim"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_8,
                    "Pink Training Zip up",
                    45.00,
                    "Pink",
                    1,
                    true,
                    "#Top #Zip-Up"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_1,
                    "Top Banding Sleeveless",
                    15.00,
                    "Black",
                    1,
                    true,
                    "#Top #Sleeveless"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_2,
                    "YALE White T-Shirts",
                    27.00,
                    "White",
                    1,
                    true,
                    "#Top #T-Shirts"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_3,
                    "Asics white sneakers with green line ",
                    58.00,
                    "White",
                    1,
                    true,
                    "#Shoes #Sneakers"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_4,
                    "Black long Sleeve",
                    8.00,
                    "Black",
                    1,
                    true,
                    "#Top #long-Sleeve"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_5,
                    "Mini Dress Black and SkyBlue",
                    30.00,
                    "Black/SkyBlue",
                    1,
                    true,

                    "#Dress #Mini-Dress"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_6,
                    "Converse White low",
                    30.00,
                    "White",
                    1,
                    true,

                    "#Shoes #Converse"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_7,
                    "Denim skirt mini",
                    20.00,
                    "Blue",
                    1,
                    true,
                    "#Skirt #Denim "
                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_8,
                    "Pink Training Zip up",
                    45.00,
                    "Pink",
                    1,
                    true,
                    "#Top #Zip-Up"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    R.drawable.img_7,
                    "Denim skirt mini",
                    20.00,
                    "Blue",
                    1,
                    true,

                    "#Skirt #Denim"

                )
            )

            HomeScreen(content = homeItemList)


        }


    }
}





