package com.example.angkorchatproto.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide

import com.example.angkorchatproto.databinding.ActivityImgViewBinding

class ImgViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityImgViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityImgViewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val imgName = "파일이름"
        val imgPath = intent.getStringExtra("imgPath")

        //뒤로가기 호출 시 이미지 창 닫기
        binding.ivCloseImgView.setOnClickListener {
            finish()
        }

        //이름 설정
        binding.tvImgViewName.text = imgName


        //이미지 적용
        Glide.with(this@ImgViewActivity)
            .load(imgPath)
            .into(binding.ivImgViewImg)


    }
}