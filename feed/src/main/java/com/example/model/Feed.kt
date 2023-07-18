package com.example.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Stable
@Parcelize
data class Feed(
    val id: Int,
    val user: User,
    val authorInfo: String,
    val content: String? = null,
    val photos: List<Int> = emptyList(),
    val map: Map? = null,
    val withUser: List<User> = emptyList(),
    val quote: Feed? = null,
    val hearts: Int,
    val replys: Int,
    val isFavorite: Boolean,
    val comments: List<Comment> = emptyList()
) : Parcelable {
    @Stable
    @Parcelize
    data class Map(
        val showingPosition: Pair<Double, Double>,
        val name: String,
        val address: String
    ) : Parcelable
}
