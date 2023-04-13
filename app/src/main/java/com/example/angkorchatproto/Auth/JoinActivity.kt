package com.example.angkorchatproto.Auth

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.angkorchatproto.Auth.LoginActivity
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val binding = ActivityJoinBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Language 밑줄
        val underLine = findViewById<TextView>(R.id.tvJoinLanguage)
        underLine.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        
        //Login 클릭시 페이지 이동
        binding.btnJoinLogin.setOnClickListener {
            val intent = Intent(this@JoinActivity, LoginActivity::class.java)
            startActivity(intent)
        }






    }
}