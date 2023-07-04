package com.example.angkorchatproto.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.angkorchatproto.JoinVO
import com.example.angkorchatproto.MainActivity
import com.example.angkorchatproto.R
import com.example.angkorchatproto.base.BaseActivity
import com.example.angkorchatproto.databinding.ActivityIdBinding
import com.example.angkorchatproto.databinding.ActivityPasswordBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class IdActivity : BaseActivity() {

    lateinit var binding: ActivityIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityIdBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //SharedPreferences
        val shared = getSharedPreferences("loginNumber", 0)
        val editor = shared.edit()

        //아이디 입력 확인
        binding.etIdId.addTextChangedListener(textChecker)

        binding.btnLoginId.isEnabled = false

        //Login 버튼 클릭시 동작

        binding.btnLoginId.setOnClickListener {
            if(binding.btnLoginId.isEnabled){
                val userNumber = intent.getStringExtra("userNumber").toString()
                val password = intent.getStringExtra("password").toString()


                val joinNumber = userNumber
                val id = binding.etIdId.text.toString()


                //번호로 사용자 정보 firebase 에 저장
                val token = shared.getString("token","")
                val database = Firebase.database.reference
                database.child("user").child(joinNumber).setValue(JoinVO(joinNumber,password,token,id))

                editor.putString("userNumber", joinNumber)
                editor.putString("id", id)
                editor.putString("userName","유저이름")
                editor.apply()
                //Log.d("TAG-password에서 번호 찍기",userNumber)

                val intent = Intent(this@IdActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this@IdActivity, "Id is required", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //edtiText 값 확인 버튼 활성화
    val textChecker = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val number = p0.toString().trim()

            if (p0.toString().length > 0 && p0 != null) {
                binding.btnLoginId.isEnabled = true
//                binding.btnLoginPassword.setClickable(true)
                binding.viewUnderLineId.setBackgroundColor(getColor(R.color.mainYellow))
                binding.btnLoginId.setBackgroundResource(R.drawable.style_login_btn)
//                Log.d("TAG-번호입력", number)

            } else {
                binding.btnLoginId.isEnabled = false
//                binding.btnLoginId.setClickable(false)
                binding.viewUnderLineId.setBackgroundColor(getColor(R.color.gray))
                binding.btnLoginId.setBackgroundResource(R.drawable.style_disable_btn)
//                Log.d("TAG-번호미입력", p0.toString())
            }

        }

        override fun afterTextChanged(p0: Editable?) {}

    }


}