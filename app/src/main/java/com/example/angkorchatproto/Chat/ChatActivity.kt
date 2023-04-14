package com.example.angkorchatproto.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.ChatVO
import com.example.angkorchatproto.UserVO
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

        var chatList = arrayListOf<ChatVO>()

        chatList.add(ChatVO("0101234567","테스트하기"))
        chatList.add(ChatVO("0101234567","하"))

         val adapter = ChatAdapter(this, chatList)

        binding.rvChatListChat.adapter = adapter
        binding.rvChatListChat.layoutManager = GridLayoutManager(this@ChatActivity,1)






    }
}