package com.example.model

import androidx.annotation.DrawableRes

data class StoryCover(
    val id: Int,
    @DrawableRes val backgroundResId: Int,
    val title: String
)
