package com.example.angkorchatproto.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.angkorchatproto.databinding.ActivityReactionBinding

class ReactionActivity : AppCompatActivity() {

    lateinit var binding : ActivityReactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityReactionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}