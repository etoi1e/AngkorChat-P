package com.example.angkorchatproto.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.angkorchatproto.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvNameChat.text = intent.getStringExtra("name")

        binding.imgMoveBackChat.setOnClickListener{
            finish()
        }




    }
}