package com.example.angkorchatproto.Profile


import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.angkorchatproto.Chat.ChatActivity
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val userName = intent.getStringExtra("name")
        val userMsg = intent.getStringExtra("email")
        val number = intent.getStringExtra("number")
        val userProfile = intent.getStringExtra("profile")

        //기본 정보 삽입
        binding.tvNameProfile.text = userName
        binding.tvMsgProfile.text = userMsg
        binding.imgProfileProfile.setImageResource(R.drawable.img_flag_south_korea)
        binding.imgChatProfile.setImageResource(R.drawable.ic_baseline_chat_24)
        binding.imgCallProfile.setImageResource(R.drawable.ic_baseline_call_24)

        //프로필 사진 uri 가져오기
        val profile = intent.getStringExtra("profile")

        if(profile == "union"){
            Glide.with(this)
                .load(R.drawable.top_logo)
                .into(binding.imgProfileProfile)
        }else if(profile == ""){
            Glide.with(this)
                .load(R.drawable.profile)
                .into(binding.imgProfileProfile)
        }else{
            Glide.with(this)
                .load(profile)
                .into(binding.imgProfileProfile)
        }




        //채팅방으로 이동
        binding.imgChatProfile.setOnClickListener{
            val intent = Intent(this@ProfileActivity,ChatActivity::class.java)
            startActivity(intent)
            intent.putExtra("name",userName)
        }

        //전화걸기
        binding.imgCallProfile.setOnClickListener{
            val intent = Intent(ACTION_CALL, Uri.parse("tel:$number"))
            startActivity(intent)
        }



    }




}