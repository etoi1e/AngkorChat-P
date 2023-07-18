package com.example.model

import com.google.android.gms.maps.model.LatLng

data class MapPosition(
    val name: String,
    val address: String,
    val checkins: Int,
    val latLng: LatLng
)
