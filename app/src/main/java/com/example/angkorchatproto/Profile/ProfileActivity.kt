package com.example.angkorchatproto.Profile


import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.angkorchatproto.Auth.ProfileAdapter
import com.example.angkorchatproto.Chat.ChatActivity
import com.example.angkorchatproto.Chat.ChatBotActivity
import com.example.angkorchatproto.Chat.ChatVO
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityProfileBinding
import java.util.*
import kotlin.collections.ArrayList


class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding
    var imgList = ArrayList<Uri?>()


    @SuppressLint("ResourceAsColor")
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




        //프로필 사진 uri 가져오기
        val profile = intent.getStringExtra("profile")

        if (profile == "union") {
            Glide.with(this)
                .load(R.drawable.top_logo)
                .into(binding.imgProfileProfile)
            Log.d("TAG-프로필","유니온으로 출력구간")
        } else if (profile == "") {
            Glide.with(this)
                .load(R.drawable.ic_profile_default_72)
                .into(binding.imgProfileProfile)
        } else {
            Glide.with(this)
                .load(profile)
                .into(binding.imgProfileProfile)
        }


        //채팅방으로 이동
        binding.btnChatProfile.setOnClickListener {
            if(profile == "union"){ //유니온 공식 계정 구분하기
                val intent = Intent(this@ProfileActivity, ChatBotActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this@ProfileActivity, ChatActivity::class.java)
                intent.putExtra("name", userName)
                intent.putExtra("number", number)
                intent.putExtra("profile", userProfile)
                startActivity(intent)
                finish()
            }

        }

        //Add/Unfriend 버튼(실제 친구 목록 아니기 때문에 기능X)
        binding.btnAddProfile.setOnClickListener {

            if (binding.btnAddProfile.tag == "true") {
                binding.btnAddProfile.text = "Add"
                binding.btnAddProfile.setBackgroundResource(R.drawable.style_login_btn)
                binding.btnAddProfile.setTextColor(Color.WHITE)
                binding.btnAddProfile.tag = "false"
            } else if (binding.btnAddProfile.tag == "false") {
                binding.btnAddProfile.text = "Unfriend"
                binding.btnAddProfile.setBackgroundResource(R.drawable.style_yellow_line_btn)
                binding.btnAddProfile.setTextColor(getColor(R.color.mainYellow))
                binding.btnAddProfile.tag = "true"
            }

        }


//        //전화걸기 1차 프로토에서는 X
//        binding.imgCallProfile.setOnClickListener {
//            val intent = Intent(ACTION_CALL, Uri.parse("tel:$number"))
//            startActivity(intent)
//            finish()
//        }

        binding.rvPhotoListProfile.adapter = ProfileAdapter(this@ProfileActivity,imgList)
        binding.rvPhotoListProfile.layoutManager = GridLayoutManager(this@ProfileActivity,3)



        //갤러리 저장 사진 불러오기
        val REQUEST_STORAGE_PERMISSION = 100

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_STORAGE_PERMISSION
                )
                return
            }
        }
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN
        )
        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
        val query = applicationContext.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )
        var imageUri: Uri? = null
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext() && imgList.size < 5) { // 5개 이하까지 불러옴
                val id = cursor.getLong(idColumn)
                val imageUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                imgList.add(imageUri)
            }
            //사진 count
            binding.tvCountPhotoProfile.text = imgList.size.toString()
        }


        // 이미지 뷰에 이미지 표시하기
        if (imageUri != null) {
            //binding.imgProfileProfile.setImageURI(imageUri)
            Log.d("TAG-이미지 로그 불러옴", imageUri.toString())
        } else {
            Log.d("TAG-이미지 로그 못불러옴", imageUri.toString())
        }
    }



}





