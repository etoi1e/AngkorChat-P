package com.example.angkorchatproto.settings.data

import java.io.Serializable

interface Data {
    data class SettingsItem(val imageId:Int?, val title: String?) :
        Serializable
}
