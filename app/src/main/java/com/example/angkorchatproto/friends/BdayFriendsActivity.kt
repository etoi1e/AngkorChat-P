package com.example.angkorchatproto.friends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.angkorchatproto.databinding.ActivityBdayFriendsBinding

class BdayFriendsActivity : AppCompatActivity() {

    lateinit var binding : ActivityBdayFriendsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityBdayFriendsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imgBtnBack.setOnClickListener {
            finish()
        }








    }



}