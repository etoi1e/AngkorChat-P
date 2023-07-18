package com.example.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import com.example.feed.R

@Stable
sealed class EditorVisibility(
    @DrawableRes val icon: Int,
    val text: String
) {
    object Public : EditorVisibility(R.drawable.ic_earth_fill_gray_20, "Public")
    object Friends : EditorVisibility(R.drawable.ic_friends_fill_gray_20, "Friends")
    object Onlyme : EditorVisibility(R.drawable.ic_lock_fill_20, "Only me")
}
