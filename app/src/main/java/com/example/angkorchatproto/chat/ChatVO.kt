package com.example.angkorchatproto.chat

import android.net.Uri
import java.io.Serializable

data class ChatModel (val users: HashMap<String, Boolean> = HashMap(),
                 val comments : HashMap<String, Comment> = HashMap()) {

    class Comment(
        val profile: String? = null, val sender: String? = null, val message: String? = null,
        val time: String? = null, val state:Boolean?=false, val url: String? = null, var key: String? = null,
        val emo:String? = null, val file:String? = null, val reaction:HashMap<String,String>? = null, val reply:String? =null) {
    }
}



class ChatBotVO : Serializable {
    var message: String? = null
    var sentBy: String? = null
    var time: String? = null

    constructor(message: String?, sentBy: String?, time: String?) {
        this.message = message
        this.sentBy = sentBy
        this.time = time
    }

    constructor() : this(null, null,null)

    companion object {
        var SENT_BY_ME = "me"
        var SENT_BY_BOT = "bot"
    }
}