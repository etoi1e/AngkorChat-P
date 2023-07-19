package com.example.angkorechoesprototype.model

import com.example.angkorechoesprototype.R

data class Song(
    val albumArtResourceId: Int,
    val name: String,
    val artist: String,
    val durationMs: Long
)

val sampleSongs = listOf(
    Song(R.drawable.img_album_iam_48, "I AM", "IVE", 123456),
    Song(R.drawable.img_album_flower_48, "Flower", "JISOO(BLACKPINK)", 234567),
    Song(R.drawable.img_album_iam_48, "Kitsch", "IVE", 124242),
    Song(R.drawable.img_album_people_pt_2_48, "People Pt.2(Feat.IU)", "Agust D", 124242),
    Song(R.drawable.img_album_ditto_48, "Ditto", "New Jeans", 124242)
)