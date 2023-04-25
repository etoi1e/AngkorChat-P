package com.example.angkorchatproto.Auth

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    //권한 응답 처리 코드
    private val multiplePermissionsCode = 100
    //권한 리스트
    private val requiredPermissions = arrayOf(
        Manifest.permission.READ_PHONE_NUMBERS,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.CALL_PHONE
    )

    //디바이스 번호
    var phoneNumber: String =""

    //디바이스 국가
    var phoneCountry: String =""

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //뒤로가기
        binding.imgMoveBackLogin.setOnClickListener {
            finish()
        }

        //디바이스 정보 가져오기
        checkPermissions()
        getPhoneNumber()
        GetCountryZipCode()

        //국가 번호 맞춰 국기/번호 출력
        if(GetCountryZipCode() == "855"){
            binding.imgFlagLogin.setImageResource(R.drawable.img_flag_cambodia)
        }else if(GetCountryZipCode() == "82"){
            binding.imgFlagLogin.setImageResource(R.drawable.img_flag_south_korea)
        }else if(GetCountryZipCode() == "1"){
            binding.imgFlagLogin.setImageResource(R.drawable.img_flag_united_states)
        }else{
            binding.imgFlagLogin.setImageResource(R.drawable.ic_delate_circle_fill_lightgray)
        }

        //디바이스 번호 자동 입력
        if (phoneNumber != null) {
            binding.etPhoneNumberLogin.setText(phoneNumber)
            binding.btnSendCodeLogin.setBackgroundResource(R.drawable.style_login_btn)
            binding.viewUnderLineLogin.setBackgroundColor(getColor(R.color.mainYellow))
        }
        
        //전화번호 입력 감지
        binding.etPhoneNumberLogin.addTextChangedListener(textChecker)
        

        //SendCode 클릭 시 동작
        binding.btnSendCodeLogin.setOnClickListener {

            if(phoneNumber == null || phoneNumber == ""){
                phoneNumber = binding.etPhoneNumberLogin.text.toString()
            }

            val intent = Intent(this@LoginActivity,PasswordActivity::class.java)
            intent.putExtra("userNumber",phoneNumber.replace("+${GetCountryZipCode()}","0"))
            startActivity(intent)
            finish()
        }
        
        
        
        

    }




    //권한 승인 확인
    fun checkPermissions() {
        //거절되었거나 아직 수락하지 않은 권한을 저장할 문자열 배열 리스트
        var rejectedPermissionList = ArrayList<String>()

        //필요한 퍼미션들을 하나씩 끄집어내서 현재 권한을 받았는지 체크
        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //만약 권한이 없다면 rejectedPermissionList에 추가
                rejectedPermissionList.add(permission)
            }
        }
        //거절된 퍼미션이 있다면
        if (rejectedPermissionList.isNotEmpty()) {
            //권한 요청
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(
                this,
                rejectedPermissionList.toArray(array),
                multiplePermissionsCode
            )
        }
    }

    //디바이스 정보 불러오기
    @SuppressLint("MissingPermission")
    fun getPhoneNumber() {
        val msg = applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (msg.line1Number != null) {
            phoneNumber = msg.line1Number.toString()
            phoneCountry = msg.simCountryIso.toUpperCase()

            Log.d("TAG-로그인 정보 번호/국가", phoneNumber+"/"+phoneCountry)
        } else {
            Log.d("TAG-번호가져오기", "없음")
        }
    }

    //국가코드 가져오기
    open fun GetCountryZipCode(): String? {
        var CountryID = ""
        var CountryZipCode = ""
        CountryID = phoneCountry
        val rl = this.resources.getStringArray(R.array.countryCode)
        for (i in rl.indices) {
            val g = rl[i].split(",").toTypedArray()
            if (g[1].trim { it <= ' ' } == CountryID.trim { it <= ' ' }) {
                CountryZipCode = g[0]
                break
            }
        }
//        Log.d("TAG-국가코드",CountryZipCode)
        return CountryZipCode
    }

    //edtiText 값 확인 버튼 활성화
    val textChecker = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val number = p0.toString().trim()

            if (p0.toString().length > 0) {
                binding.btnSendCodeLogin.setClickable(true)
                binding.viewUnderLineLogin.setBackgroundColor(getColor(R.color.mainYellow))
                binding.btnSendCodeLogin.setBackgroundResource(R.drawable.style_login_btn)
//                Log.d("TAG-번호입력",number)

            } else {
                binding.btnSendCodeLogin.setClickable(false)
                binding.viewUnderLineLogin.setBackgroundColor(getColor(R.color.gray))
                binding.btnSendCodeLogin.setBackgroundResource(R.drawable.style_disable_btn)
//                Log.d("TAG-번호미입력",p0.toString())
            }

        }

        override fun afterTextChanged(p0: Editable?) {}

    }







}