package com.example.angkorchatproto.Auth

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.angkorchatproto.MainActivity
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //SharedPreferences
        val shared = getSharedPreferences("loginNumber", 0)
        val editor = shared.edit()

        //비밀번호 입력 확인
        binding.etPasswordPassword.addTextChangedListener(textChecker)

        binding.btnLoginPassword.isEnabled = false

        //Login 버튼 클릭시 동작

        binding.btnLoginPassword.setOnClickListener {
            if(binding.btnLoginPassword.isEnabled){
                val userNumber = intent.getStringExtra("userNumber").toString()
                editor.putString("loginNumber", userNumber)
                editor.commit()

                val intent = Intent(this@PasswordActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this@PasswordActivity, "Password is required", Toast.LENGTH_SHORT).show()
            }
        }




    }

    //edtiText 값 확인 버튼 활성화
    val textChecker = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val number = p0.toString().trim()

            if (p0.toString().length > 0 && p0 != null) {
                binding.btnLoginPassword.isEnabled = true
//                binding.btnLoginPassword.setClickable(true)
                binding.viewUnderLinePassword.setBackgroundColor(getColor(R.color.mainYellow))
                binding.btnLoginPassword.setBackgroundResource(R.drawable.style_login_btn)
                Log.d("TAG-번호입력", number)

            } else {
                binding.btnLoginPassword.isEnabled = false
//                binding.btnLoginPassword.setClickable(false)
                binding.viewUnderLinePassword.setBackgroundColor(getColor(R.color.gray))
                binding.btnLoginPassword.setBackgroundResource(R.drawable.style_disable_btn)
                Log.d("TAG-번호미입력", p0.toString())
            }

        }

        override fun afterTextChanged(p0: Editable?) {}

    }


}