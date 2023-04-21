package com.example.angkorchatproto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.angkorchatproto.Auth.JoinActivity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //SharedPreferences
        val shared = getSharedPreferences("loginNumber",0)

        //로그인 확인
        val autoLogin = shared.getString("userNumber","")
        Log.d("TAG-자동 로그인 확인",autoLogin.toString())

        Handler().postDelayed({
            if(autoLogin != ""){
                val intent = Intent(this@Splash,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this@Splash, JoinActivity::class.java)
                startActivity(intent)
                finish()
            }

        },1000)


    }

}