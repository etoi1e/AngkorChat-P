package com.example.angkorchatproto

class UserVO : java.io.Serializable {
    var name: String? = null
    var email: String? = null
    var profile: String? = null
    var phone: String? = null
    var id: String? = null

    constructor(name : String?, email : String?,profile : String?,phone : String?, id: String?) {
        this.name = name
        this.email = email
        this.profile = profile
        this.phone = phone
        this.id = id
    }

    constructor() : this(null, null,null,null, null)


}



class JoinVO(var number: String?, var password: String?, var token: String?, var id: String?)

