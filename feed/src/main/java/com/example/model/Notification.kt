package com.example.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable

@Stable
data class Notification(
    val id: Int,
    val users: List<User>,
    val message: String,
    val elapsedTimeMinute: Int,
    @DrawableRes val image: Int?
)
