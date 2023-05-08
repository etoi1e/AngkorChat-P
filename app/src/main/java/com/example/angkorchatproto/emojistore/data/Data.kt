package com.example.angkorchatproto.emojistore.data

import java.io.Serializable

interface Data {
    data class EmojiInfo(val imageId:Int?, val title: String?, val make:String?, val coin: Int?) :
        Serializable
}
