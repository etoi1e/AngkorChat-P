package com.example.angkorchatproto.friends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityAddByContactBinding
import com.example.angkorchatproto.databinding.ActivityEmojiStoreBinding
import com.example.angkorchatproto.emojistore.viewmodel.EmojiStoreViewModel
import com.example.angkorchatproto.friends.viewmodel.AddByContactViewModel

class AddByContactActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddByContactBinding
    private var mNavController: NavController? = null
    private var mNavHostFragment: NavHostFragment? = null
    private var viewModel: AddByContactViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddByContactBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AddByContactViewModel::class.java]
        mNavHostFragment = supportFragmentManager.findFragmentById(R.id.add_friend_container) as NavHostFragment
        mNavController = mNavHostFragment?.navController
        setContentView(binding.root)
    }
}