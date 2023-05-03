package com.example.angkorchatproto.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityChatBinding
import com.example.angkorchatproto.databinding.ActivityReactionBinding

class ReactionActivity : AppCompatActivity() {

    lateinit var binding : ActivityReactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityReactionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}