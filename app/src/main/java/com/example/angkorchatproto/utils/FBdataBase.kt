package com.example.angkorchatproto.utils

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBdataBase {

    companion object{
        val dataBase = Firebase.database

        fun getChatBotRef():DatabaseReference{
            return dataBase.getReference("chatBot")
        }

        fun getChatRef():DatabaseReference{
            return dataBase.getReference("chat")
        }

        fun getFriendRef():DatabaseReference{
            return dataBase.getReference("friends")
        }




    }
}