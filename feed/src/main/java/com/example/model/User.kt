package com.example.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Stable
@Parcelize
data class User(
    val id: Int,
    @DrawableRes val profileResId: Int,
    val name: String,
    val subtitle: String
): Parcelable
