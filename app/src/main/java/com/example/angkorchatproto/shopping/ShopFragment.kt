package com.example.angkorchatproto.shopping

import android.annotation.SuppressLint
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

    @SuppressLint("DiscouragedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreate(savedInstanceState)

        binding = FragmentShopBinding.inflate(inflater, container, false)



        binding.composeView.setContent {

            val homeList = listOf(
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_1",
                    requireActivity().resources.getIdentifier(
                        "img_1_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Red Dotted Shirts",
                    20.00,
                    "Red",
                    1,
                    false,
                    "#Top #Shirts"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_2",
                    requireActivity().resources.getIdentifier(
                        "img_2_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "White Two Piece Sets",
                    35.00,
                    "White",
                    1,
                    true,
                    "#Top #Bottom #Setup"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_3",
                    requireActivity().resources.getIdentifier(
                        "img_3_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "YALE University White T-Shirts",
                    25.00,
                    "White",
                    1,
                    false,
                    "#Top #T-shirts"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_4",
                    requireActivity().resources.getIdentifier(
                        "img_4_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Apple pencil case",
                    10.00,
                    "Black, Yellow",
                    1,
                    false,
                    "#digital #accessory"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_5",
                    requireActivity().resources.getIdentifier(
                        "img_5_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Black classic slacks",
                    30.00,
                    "Black",
                    1,
                    false,
                    "#Bottom #Slacks #Trousers"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_6",
                    requireActivity().resources.getIdentifier(
                        "img_6_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Khaki cargo skirt",
                    20.00,
                    "Khaki",
                    1,
                    true,
                    "#Bottom #Skirt"
                ),

                MerchandiseInfo(
                    "Angkor Shop",
                    "img_7",
                    requireActivity().resources.getIdentifier(
                        "img_7_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Black skirts pants",
                    20.00,
                    "Black",
                    1,
                    false,
                    "#Bottom #Skirt #Trousers"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_8",
                    requireActivity().resources.getIdentifier(
                        "img_8_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Rockfish Rain Boots",
                    40.00,
                    "Black, White",
                    1,
                    true,
                    "#Shoes  #Rainboots"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_9",
                    requireActivity().resources.getIdentifier(
                        "img_9_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Stanley oil Black Bag",
                    70.00,
                    "Black",
                    1,
                    false,
                    "#Bag"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_10",
                    requireActivity().resources.getIdentifier(
                        "img_10_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Archivepke mini bowling bag",
                    100.00,
                    "Ivory",
                    1,
                    false,
                    "#Bag"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_11",
                    requireActivity().resources.getIdentifier(
                        "img_11_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Lee small logo cap",
                    30.00,
                    "Ivory",
                    1,
                    false,
                    "#Cap"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_12",
                    requireActivity().resources.getIdentifier(
                        "img_12_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "LA small logo cap",
                    50.00,
                    "Blue",
                    1,
                    false,
                    "#Cap"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_13",
                    requireActivity().resources.getIdentifier(
                        "img_13_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Wide brown sunglasses",
                    70.00,
                    "Brown",
                    1,
                    false,
                    "#Sunglasses #accessory"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_14",
                    requireActivity().resources.getIdentifier(
                        "img_14_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Visible frame sunglasses",
                    80.00,
                    "White",
                    1,
                    false,
                    "#Sunglasses #accessory"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_15",
                    requireActivity().resources.getIdentifier(
                        "img_15_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "mobile charger cable",
                    10.00,
                    "Black, White, Blue",
                    1,
                    false,
                    "#digital #accessory"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_16",
                    requireActivity().resources.getIdentifier(
                        "img_16_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Cartier square watch",
                    1000.00,
                    "Black, Gold",
                    1,
                    false,
                    "#Watch #accessory"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_17",
                    requireActivity().resources.getIdentifier(
                        "img_17_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Rolex green face watch",
                    1300.00,
                    "Green, Silver",
                    1,
                    true,
                    "#Watch #accessory"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_18",
                    requireActivity().resources.getIdentifier(
                        "img_18_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Lee Big logo Black T-Shirts",
                    30.00,
                    "Black, Silver",
                    1,
                    false,
                    "#Top #T-Shirts"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_19",
                    requireActivity().resources.getIdentifier(
                        "img_19_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "Rolex gold face watch",
                    1430.00,
                    "Gold, Silver",
                    1,
                    false,
                    "#Watch #accessory"
                ),
                MerchandiseInfo(
                    "Angkor Shop",
                    "img_20",
                    requireActivity().resources.getIdentifier(
                        "img_20_1",
                        "drawable",
                        requireActivity().packageName
                    ),
                    "YALE Pets Hoodie",
                    40.00,
                    "Gold, Silver",
                    1,
                    false,
                    "#Pet"
                )
            )
            val randomList = homeList.shuffled()


            HomeScreen(content = randomList)

        }









        return binding.root
    }


}