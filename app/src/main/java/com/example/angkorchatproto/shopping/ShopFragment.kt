package com.example.angkorchatproto.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.setContent
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentShopBinding
import com.example.angkorshopping.compose.state.MerchandiseInfo
import com.example.angkorshopping.screens.HomeScreen


class ShopFragment : Fragment() {

    lateinit var binding: FragmentShopBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreate(savedInstanceState)

        binding = FragmentShopBinding.inflate(inflater, container, false)

        binding.composeView.setContent {

            val homeItemList = listOf<MerchandiseInfo>(
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_1,
                    "Top Banding Sleeveless",
                    15.00,
                    "Black",
                    1,
                    true,
                    "#Top #Sleeveless"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_2,
                    "YALE White T-Shirts",
                    27.00,
                    "White",
                    1,
                    true,
                    "#Top #T-Shirts"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_3,
                    "Asics white sneakers with green line ",
                    58.00,
                    "White",
                    1,
                    true,
                    "#Shoes #Sneakers"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_4,
                    "Black long Sleeve",
                    8.00,
                    "Black",
                    1,
                    true,
                    "#Top #long-Sleeve"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_5,
                    "Mini Dress Black and SkyBlue",
                    30.00,
                    "Black/SkyBlue",
                    1,
                    true,
                    "#Dress #Mini-Dress"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_6,
                    "Converse White low",
                    30.00,
                    "White",
                    1,
                    true,
                    "#Shoes #Converse"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_7,
                    "Denim skirt mini",
                    20.00,
                    "Blue",
                    1,
                    true,
                    "#Skirt #Denim"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_8,
                    "Pink Training Zip up",
                    45.00,
                    "Pink",
                    1,
                    true,
                    "#Top #Zip-Up"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_1,
                    "Top Banding Sleeveless",
                    15.00,
                    "Black",
                    1,
                    true,
                    "#Top #Sleeveless"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_2,
                    "YALE White T-Shirts",
                    27.00,
                    "White",
                    1,
                    true,
                    "#Top #T-Shirts"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_3,
                    "Asics white sneakers with green line ",
                    58.00,
                    "White",
                    1,
                    true,
                    "#Shoes #Sneakers"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_4,
                    "Black long Sleeve",
                    8.00,
                    "Black",
                    1,
                    true,
                    "#Top #long-Sleeve"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_5,
                    "Mini Dress Black and SkyBlue",
                    30.00,
                    "Black/SkyBlue",
                    1,
                    true,

                    "#Dress #Mini-Dress"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_6,
                    "Converse White low",
                    30.00,
                    "White",
                    1,
                    true,

                    "#Shoes #Converse"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_7,
                    "Denim skirt mini",
                    20.00,
                    "Blue",
                    1,
                    true,
                    "#Skirt #Denim "
                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_8,
                    "Pink Training Zip up",
                    45.00,
                    "Pink",
                    1,
                    true,
                    "#Top #Zip-Up"

                ),
                MerchandiseInfo(
                    "AngkorShop",
                    com.example.angkorshopping.R.drawable.img_7,
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






        return binding.root
    }


}