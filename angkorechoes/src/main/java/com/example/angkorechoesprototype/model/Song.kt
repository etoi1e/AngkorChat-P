package com.example.angkorechoesprototype.model

import androidx.annotation.DrawableRes
import com.example.angkorechoesprototype.R

data class Song(
    @DrawableRes val albumArtResourceId: Int,
    @DrawableRes val bigAlbumArtResourceId: Int,
    val name: String,
    val artist: String,
    val durationMs: Long
)

val sampleSongs = listOf(
    Song(R.drawable.img_album_iam_48, R.drawable.iam_2048, "I AM", "IVE", 184000),
    Song(R.drawable.img_album_flower_48, R.drawable.flower_640, "Flower", "JISOO(BLACKPINK)", 185000),
    Song(R.drawable.img_album_iam_48, R.drawable.iam_2048, "Kitsch", "IVE", 202000),
    Song(R.drawable.img_album_people_pt_2_48, R.drawable.ditto_640, "People Pt.2(Feat.IU)", "Agust D", 270000),
    Song(R.drawable.img_album_ditto_48, R.drawable.ditto_640, "Ditto", "New Jeans", 190000)
)