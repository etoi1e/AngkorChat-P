package com.example.angkorchatproto.base


import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.angkorchatproto.service.BroadcastReceiver


open class BaseActivity : AppCompatActivity() {
    companion object {
        private var intentFilter: IntentFilter? = null
        private val broadcastReceiver = BroadcastReceiver()
    }

    open fun setBroadCastReceiver() {
        Log.d("jongchan.won","BaseActivity setBroadCastReceiver()")
        if (intentFilter == null) {
            intentFilter = IntentFilter()
            intentFilter?.addAction("videoCall")
            registerReceiver(broadcastReceiver, intentFilter)
        }
    }

    open fun releaseBroadCastReceiver() {
        intentFilter = null
        unregisterReceiver(broadcastReceiver)
    }
}