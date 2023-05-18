package com.example.angkorchatproto.service

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


/**
 * Package Name : com.example.angkorchatproto.service
 * Class Name : FirebaseMessagingService
 * Description :
 * Created by de5ember on 2023/05/17.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d("jongchan.won",token)
        super.onNewToken(token)
        val shard = getSharedPreferences("loginNumber", 0)
        val edit = shard.edit()
        edit.putString("token", token)
        edit.apply()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("jongchan.won","${remoteMessage.data["token"]}, ${remoteMessage.data["phoneNumber"]}")
        super.onMessageReceived(remoteMessage)
        // 서비스에서 브로드캐스트를 전송할 때 사용
        val broadcastIntent = Intent("videoCall")
        broadcastIntent.putExtra("token", remoteMessage.data["token"])
        broadcastIntent.putExtra("phoneNumber", remoteMessage.data["phoneNumber"])
        sendBroadcast(broadcastIntent)
    }
}