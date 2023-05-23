package com.example.angkorchatproto.chat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import com.bumptech.glide.Glide
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityChatBinding
import com.example.angkorchatproto.databinding.ActivityChatMoreBinding
import com.example.angkorchatproto.dialog.CustomDialog
import kr.co.kdnavien.naviensmart.presentation.custom.DialogNegativeBtnListener
import kr.co.kdnavien.naviensmart.presentation.custom.DialogPositiveBtnListener

class ChatMoreActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatMoreBinding


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatMoreBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.ivChatMoreMoveBack.setOnClickListener {
            finish()
        }

        val userName = intent.getStringExtra("userName")
        val userNumber = intent.getStringExtra("userNumber")
        val profile = intent.getStringExtra("profile")

        binding.tvChatMoreName.text = userName
        binding.tvChatMoreUserName.text = userName
        binding.tvChatMoreChatName.text = "$userNumber's Chat room"

        Log.d("TAG-프로필",profile.toString())

        if(profile != "null"){
            Glide.with(this@ChatMoreActivity)
                .load(profile)
                .into(binding.ivChatMoreProfile)
        }

        if(profile == "null" ||profile == ""){
            Glide.with(this@ChatMoreActivity)
                .load(R.drawable.ic_profile_default_72)
                .into(binding.ivChatMoreProfile)
        }



        binding.ivChatMoreBlock.setOnClickListener {


            CustomDialog.create(this@ChatMoreActivity)
                ?.setDesc(SpannableStringBuilder("Are you sure you want to block\n$userName?"))
                ?.setCancelable(true)
                ?.setPositiveButtonText(SpannableStringBuilder("Block"))
                ?.setNegativeButtonText(SpannableStringBuilder("Cancel"))
                ?.setPositiveBtnListener(object : DialogPositiveBtnListener {
                    override fun confirm(division: Int) {


                    }
                })
                ?.setNegativeBtnListener(object : DialogNegativeBtnListener {
                    override fun cancel(division: Int) {
                    }
                })
                ?.showTwoButton()

        }











    }
}