package com.example.angkorchatproto.emojistore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.angkorchatproto.databinding.ActivityEmojiStoreBinding
import com.example.angkorchatproto.databinding.ActivityMainBinding

class EmojiStoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityEmojiStoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmojiStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}