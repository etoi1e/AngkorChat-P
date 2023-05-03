package com.example.angkorchatproto.Chat

class ChatModel (val users: HashMap<String, Boolean> = HashMap(),
                 val comments : HashMap<String, Comment> = HashMap()) {

    class Comment(val profile : String? = null , val sender: String? = null, val message: String? = null,
                  val time: String? = null, val state:Boolean?=false, val url :String? = null, val key:String? = null,val emo:Int? = null)
}

class ChatVO : java.io.Serializable {
    var message: String? = null
    var user: String? = null
    var time: String? = null


    constructor(message: String?, user: String?, time: String?) {
        this.message = message
        this.user = user
        this.time = time

    }

    companion object {
        var SENT_BY_ME = "me"
        var SENT_BY_OTHER = "other"
    }

    constructor() : this(null, null,null)

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