package com.example.angkorchatproto.Chat

import java.time.LocalDateTime

class ChatVO : java.io.Serializable {
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