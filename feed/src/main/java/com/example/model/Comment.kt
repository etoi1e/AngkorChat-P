package com.example.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class Comment(
    val id: Int,
    val user: User,
    val elapsedTime: String,
    val content: String,
    val likes: Int,
    val isLike: Boolean,
    val replies: List<Comment>?
) : Parcelable
