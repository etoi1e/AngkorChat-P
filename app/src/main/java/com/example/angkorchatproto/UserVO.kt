package com.example.angkorchatproto

class UserVO : java.io.Serializable {
    var name: String? = null
    var email: String? = null
    var profile: String? = null
    var phone: String? = null

    constructor(name : String?, email : String?,profile : String?,phone : String?) {
        this.name = name
        this.email = email
        this.profile = profile
        this.phone = phone
    }

    constructor() : this(null, null,null,null)


}



class JoinVO(val number : String, val email : String)

