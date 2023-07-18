package com.example.angkorshopping.compose.state

import java.util.ArrayList

data class MerchandiseInfo(
    val shopName : String,
    val merchandiseImg : Int,
    val merchandiseName : String,
    val merchandisePrice : Double,
    val merchandiseOption : String,
    val quantity : Int,
    val pickup : Boolean,
    val tags : String
)