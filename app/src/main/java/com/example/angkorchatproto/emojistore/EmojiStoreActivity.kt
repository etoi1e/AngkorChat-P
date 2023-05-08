package com.example.angkorchatproto.emojistore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityEmojiStoreBinding
import com.example.angkorchatproto.emojistore.viewmodel.EmojiStoreViewModel

class EmojiStoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityEmojiStoreBinding
    private var mNavController: NavController? = null
    private var mNavHostFragment: NavHostFragment? = null
    private var viewModel: EmojiStoreViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmojiStoreBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[EmojiStoreViewModel::class.java]
        mNavHostFragment = supportFragmentManager.findFragmentById(R.id.emoji_container) as NavHostFragment
        mNavController = mNavHostFragment?.navController
        setContentView(binding.root)
    }
}