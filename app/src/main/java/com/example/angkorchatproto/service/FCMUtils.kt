package com.example.angkorchatproto.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage

// 푸시 알림을 보내기 위한 유틸리티 클래스입니다.
object FCMUtils {
    // 토큰을 이용해 FCM 푸시 알림 보내는 함수
    fun sendFCMNotification(token: String, message: String) {
        Log.d("jongchan.won", token)
        val data = hashMapOf(
            "phoneNumber" to message,
        )

        val message = RemoteMessage.Builder(token)
            .setData(data)
            .build()

        FirebaseMessaging.getInstance().send(message)
    }
}