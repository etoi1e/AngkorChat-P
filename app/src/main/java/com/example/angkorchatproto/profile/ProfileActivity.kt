package com.example.angkorchatproto.profile


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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.angkorchatproto.chat.ChatBotActivity
import com.example.angkorchatproto.auth.ProfileAdapter
import com.example.angkorchatproto.chat.ChatActivity
import com.example.angkorchatproto.R
import com.example.angkorchatproto.UserVO
import com.example.angkorchatproto.base.BaseActivity
import com.example.angkorchatproto.chat.adapter.MediaImgAdapter
import com.example.angkorchatproto.databinding.ActivityProfileBinding
import com.example.angkorchatproto.utils.FBdataBase
import java.util.*
import kotlin.collections.ArrayList


class ProfileActivity : BaseActivity() {

    lateinit var binding: ActivityProfileBinding
    var imgList = ArrayList<Uri?>()

    //Manifest 에서 설정한 권한을 가지고 온다.
    val STORAGE_PERMISSION = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_VIDEO

    )


    //권한 플래그값 정의
    val FLAG_PERM_STORAGE = 99


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //SharedPreferences
        val shared = getSharedPreferences("loginNumber", 0)
        val userNumber = shared.getString("userNumber", "").toString()

        val userName = intent.getStringExtra("name")
        val userMsg = intent.getStringExtra("email")
        val number = intent.getStringExtra("number")
        val userProfile = intent.getStringExtra("profile")
        val id = intent.getStringExtra("id")


        //기본 정보 삽입
        binding.tvNameProfile.text = userName
        binding.tvMsgProfile.text = userMsg

        //뒤로가기 버튼
        binding.imgMoveBackProfile.setOnClickListener {
            finish()
        }


        //프로필 사진 uri 가져오기
        val profile = intent.getStringExtra("profile")

        getImage()

        if (profile == "union") { //챗봇프로필 출력 조건
            Glide.with(this)
                .load(R.drawable.top_logo)
                .into(binding.imgProfileProfile)
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
        binding.imgChatProfile.setOnClickListener {
            if (profile == "union") { //챗봇으로 이동 조건
                val intent = Intent(this@ProfileActivity, ChatBotActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@ProfileActivity, ChatActivity::class.java)
                intent.putExtra("name", userName)
                intent.putExtra("number", number)
                intent.putExtra("profile", userProfile)
                startActivity(intent)
                finish()
            }

        }

        //Add/Unfriend 버튼
        binding.btnAddProfile.setOnClickListener {

            val friendRef = FBdataBase.getFriendRef()

            if (profile == "union") {
                binding.btnAddProfile.isClickable = false
                Toast.makeText(this@ProfileActivity, "Is not removable friend", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (binding.btnAddProfile.tag == "true") {
                    binding.btnAddProfile.text = "Add"
                    binding.btnAddProfile.setBackgroundResource(R.drawable.style_login_btn)
                    binding.btnAddProfile.setTextColor(Color.WHITE)
                    binding.btnAddProfile.tag = "false"

                    //친구 목록에서 제거
                    val removeDash = number.toString().replace("-", "")
                    val removeSpace = removeDash.replace(" ", "")
                    friendRef.child(userNumber).child(removeSpace).removeValue()


                } else if (binding.btnAddProfile.tag == "false") {
                    binding.btnAddProfile.text = "Unfriend"
                    binding.btnAddProfile.setBackgroundResource(R.drawable.style_yellow_line_btn)
                    binding.btnAddProfile.setTextColor(getColor(R.color.mainYellow))
                    binding.btnAddProfile.tag = "true"


                    //친구 추가하기
                    val removeDash = number.toString().replace("-", "")
                    val removeSpace = removeDash.replace(" ", "")
                    friendRef.child(userNumber).child(removeSpace!!)
                        .setValue(UserVO(userName, userMsg, userProfile, removeSpace, id))
                }
            }


        }


        //전화걸기
        binding.imgCallProfile.setOnClickListener {
            if (profile == "union") {
                Toast.makeText(
                    this@ProfileActivity,
                    "Call function is not supported",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(ACTION_CALL, Uri.parse("tel:$number"))
                startActivity(intent)
                finish()
            }
        }

            binding.rvPhotoListProfile.adapter = ProfileAdapter(this@ProfileActivity, imgList)
            binding.rvPhotoListProfile.layoutManager = GridLayoutManager(this@ProfileActivity, 3)


        if (checkPermission(STORAGE_PERMISSION, FLAG_PERM_STORAGE)) {

        } else {
            //권한이 없으면 권한 요청
            ActivityCompat.requestPermissions(this, STORAGE_PERMISSION, FLAG_PERM_STORAGE)
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


    }

    fun getImage() {

        //권한 확인

        if (checkPermission(STORAGE_PERMISSION, FLAG_PERM_STORAGE)) {

            val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
            startActivity(intent)
        } else {
            //권한이 없으면 권한 요청을 합니다.
            ActivityCompat.requestPermissions(
                this@ProfileActivity,
                STORAGE_PERMISSION,
                FLAG_PERM_STORAGE
            )

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
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext()) { // 5개 이하까지 불러옴
                val id = cursor.getLong(idColumn)
                val imageUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                if (imgList.size < 5) {
                    imgList.add(imageUri)
                }
                Log.d("imgList", imgList.toString())
            }
        }
        val adapter = ProfileAdapter(this@ProfileActivity, imgList)
        binding.rvPhotoListProfile.adapter = adapter
        binding.rvPhotoListProfile.layoutManager = GridLayoutManager(this@ProfileActivity, 3)

        adapter.notifyDataSetChanged()


    }


    fun checkPermission(permissions: Array<out String>, flag: Int): Boolean {
        for (permission in permissions) {
            //만약 권한이 승인되어 있지 않다면 권한승인 요청을 사용에 화면에 호출합니다.
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, permissions, flag)
                return false
            }
        }
        return true
    }


}





