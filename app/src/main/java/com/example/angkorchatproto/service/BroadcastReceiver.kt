package com.example.angkorchatproto.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.angkorchatproto.JoinVO
import com.example.angkorchatproto.video.VideoActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // 브로드캐스트 수신 시 처리할 로직을 여기에 작성합니다.
        Log.d("jongchan.won", "onReceive")
        if (intent.action == "videoCall") {
            Log.d("jongchan.won", "브로드캐스트로 데이터 받음")
            val token = intent.getStringExtra("token")
            val phoneNumber = intent.getStringExtra("phoneNumber")
            val videoIntent = Intent(context, VideoActivity::class.java)
            videoIntent.putExtra("mode", "receive")
            videoIntent.putExtra("token", token)
            videoIntent.putExtra("phoneNumber", phoneNumber)
            context.startActivity(videoIntent)
        }
    }
}