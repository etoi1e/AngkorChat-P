package com.example.angkorchatproto.Chat

class ChatVO : java.io.Serializable {
    var message: String? = null
    var sender: String? = null
    var receiver: String? = null
    var time: String? = null


    constructor(message: String?, sender: String?,receiver:String?, time: String?) {
        this.message = message
        this.sender = sender
        this.receiver = receiver
        this.time = time

    }

    companion object {
        var SENT_BY_ME = "me"
        var SENT_BY_OTHER = "other"
    }

    constructor() : this(null, null,null,null)

}

class ChatBotVO : java.io.Serializable {
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