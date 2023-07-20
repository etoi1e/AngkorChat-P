package com.example.angkorchatproto.callscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityVideoCallScreenBinding

class VideoCallScreen : AppCompatActivity() {
    lateinit var binding : ActivityVideoCallScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityVideoCallScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val profile = intent.getStringExtra("profile")

        binding.tvVoiceName2.text = name

        val resourceID = resources.getIdentifier(profile, "drawable","com.example.angkorchatproto")

        if (profile == "") {
            Glide.with(this)
                .load(R.drawable.ic_profile_default_72)
                .into(binding.imgVedioProfie)
        } else {
            Glide.with(this)
                .load(resourceID)
                .into(binding.imgVedioProfie)
        }

        binding.btnVideoUnCall.setOnClickListener {
            finish()
        }


    }
}