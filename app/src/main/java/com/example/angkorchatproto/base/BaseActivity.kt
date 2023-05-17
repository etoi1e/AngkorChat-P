package com.example.angkorchatproto.base


import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.angkorchatproto.chat.ChatFragment
import com.example.angkorchatproto.friends.FriendsFragment
import com.example.angkorchatproto.databinding.ActivityMainBinding


open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onResume() {
        super.onResume()
    }
}