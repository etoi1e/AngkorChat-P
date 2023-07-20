package com.example.angkorchatproto.callscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityVoiceCallScreenBinding

class VoiceCallScreen : AppCompatActivity() {

    lateinit var binding : ActivityVoiceCallScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityVoiceCallScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val name = intent.getStringExtra("name")
        val profile = intent.getStringExtra("profile")

        binding.tvVoiceName.text = name

        val resourceID = resources.getIdentifier(profile, "drawable","com.example.angkorchatproto")

        if (profile == "") {
            Glide.with(this)
                .load(R.drawable.ic_profile_default_72)
                .into(binding.imgVoiceProfie)
        } else {
            Glide.with(this)
                .load(resourceID)
                .into(binding.imgVoiceProfie)
        }

        binding.btnMoveVideo.setOnClickListener {
            val intent = Intent(this@VoiceCallScreen,VideoCallScreen::class.java)
            intent.putExtra("profile",profile)
            intent.putExtra("name",name)
            startActivity(intent)
            finish()
        }

        binding.btnVoiceUnCall.setOnClickListener {
            finish()
        }

    }
}