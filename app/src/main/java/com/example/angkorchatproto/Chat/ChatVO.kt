package com.example.angkorchatproto.Chat

class ChatVO {
    var message: String? = null
    var sentBy: String? = null

    constructor(message: String?, sentBy: String?) {
        this.message = message
        this.sentBy = sentBy
    }

    companion object {
        var SENT_BY_ME = "me"
        var SENT_BY_BOT = "bot"
    }
}