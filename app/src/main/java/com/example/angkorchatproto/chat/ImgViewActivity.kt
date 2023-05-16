package com.example.angkorchatproto.chat

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.example.angkorchatproto.databinding.ActivityImgViewBinding
import java.util.*

class ImgViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityImgViewBinding
    var imgPath = ""


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityImgViewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val imgName = "파일이름"
        imgPath = intent.getStringExtra("imgPath").toString()

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

        //하단 버튼 클릭
        binding.btnSaveImg.setOnClickListener {
            // 이미지뷰에서 Glide로 이미지를 비트맵으로 가져옴
            Glide.with(this)
                .asBitmap()
                .load(imgPath)
                .into(object : SimpleTarget<Bitmap>() {
                    @RequiresApi(Build.VERSION_CODES.Q)
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                    ) {
                        // 이미지 파일 이름
                        val filename = "${System.currentTimeMillis()}.png"
                        // 저장할 위치와 파일명 지정
                        val contentValues = ContentValues().apply {
                            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures")
                        }
                        // 미디어 스토어(ContentResolver)를 사용하여 이미지 저장
                        val contentResolver = applicationContext.contentResolver
                        val imageUri = contentResolver.insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            contentValues
                        ) as Uri
                        val outputStream = contentResolver.openOutputStream(imageUri)
                        resource.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        outputStream?.close()

                        // 저장 완료 토스트 메시지 표시
                        Toast.makeText(
                            this@ImgViewActivity,
                            "이미지가 저장되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                })

        }


            //이미지 삭제
            binding.btnDeleteImg.setOnClickListener {
                Toast.makeText(this@ImgViewActivity, "삭제", Toast.LENGTH_SHORT).show()
            }


            //이미지 정보
            binding.btnInfoImg.setOnClickListener {
                Toast.makeText(this@ImgViewActivity, "정보", Toast.LENGTH_SHORT).show()
            }

        }

    }
