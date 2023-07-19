package com.example.angkorchatproto.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.Toast
import com.example.angkorchatproto.JoinVO
import com.example.angkorchatproto.MainActivity
import com.example.angkorchatproto.R
import com.example.angkorchatproto.base.BaseActivity
import com.example.angkorchatproto.databinding.ActivityPasswordBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PasswordActivity : BaseActivity() {

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

        //Enter 클릭 시 다음 페이지로 이동
        binding.etPasswordPassword.setOnKeyListener() { v, keyCode, event ->
            var handled = false

            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                //전송 버튼 클릭효과
                binding.btnLoginPassword.performClick()
                handled = true
            }
            handled
        }


        //Login 버튼 클릭시 동작

        binding.btnLoginPassword.setOnClickListener {
            if(binding.btnLoginPassword.isEnabled){
                val userNumber = intent.getStringExtra("userNumber").toString()

                val joinNumber = userNumber
                val password = binding.etPasswordPassword.text.toString()

                val intent = Intent(this@PasswordActivity, IdActivity::class.java)
                intent.putExtra("userNumber",userNumber)
                intent.putExtra("password",password)
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
//                Log.d("TAG-번호입력", number)

            } else {
                binding.btnLoginPassword.isEnabled = false
//                binding.btnLoginPassword.setClickable(false)
                binding.viewUnderLinePassword.setBackgroundColor(getColor(R.color.gray))
                binding.btnLoginPassword.setBackgroundResource(R.drawable.style_disable_btn)
//                Log.d("TAG-번호미입력", p0.toString())
            }

        }

        override fun afterTextChanged(p0: Editable?) {}

    }


}