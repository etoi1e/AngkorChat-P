package com.example.angkorchatproto.auth

import android.Manifest
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.angkorchatproto.R
import com.example.angkorchatproto.base.BaseActivity
import com.example.angkorchatproto.databinding.ActivityJoinBinding
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission


class JoinActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        permissionCheck()


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

    fun permissionCheck (){
        TedPermission.create()
            .setPermissionListener(object : PermissionListener{
                override fun onPermissionGranted() {
                   Toast.makeText(this@JoinActivity,"Permission Granted",Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(this@JoinActivity,"Permission Denied",Toast.LENGTH_SHORT).show()
                }

            })
            .setDeniedMessage("If you reject permission, you can not use")
            .setPermissions(
                Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.INTERNET)
            .check()
    }

}