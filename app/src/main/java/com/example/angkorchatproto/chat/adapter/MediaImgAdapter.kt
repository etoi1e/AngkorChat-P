package com.example.angkorchatproto.chat.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.angkorchatproto.R

class MediaImgAdapter(private val context: Context, private val imgList: ArrayList<Uri?>) :
    RecyclerView.Adapter<MediaImgAdapter.ViewHolder>() {

    val selectImg = ArrayList<Uri?>()

    //Activity 데이터 전달 interface
    interface OnImageSelectListener {
        fun onImageSelect(selectImg: ArrayList<Uri?>)
    }

    // 객체 저장 변수 선언
    lateinit var mOnImageSelectListener: OnImageSelectListener

    //객체 전달 메서드
    fun setOnImageSelectListener(OnImageSelectListener: OnImageSelectListener) {
        mOnImageSelectListener = OnImageSelectListener
    }

    @SuppressLint("ResourceType")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMediaImgList: ImageView = itemView.findViewById(R.id.imgMediaImgList)

        init {
            itemView.setOnClickListener {

                if (imgMediaImgList.tag == "false") {//사진 선택

                    if (selectImg.size < 1) {
                        selectImg.add(imgList[adapterPosition])
                        imgMediaImgList.setBackgroundResource(R.drawable.select_photo)
                        imgMediaImgList.tag = "true"
                    } else {
                        Toast.makeText(context, "사진은 1장만 선택 가능합니다", Toast.LENGTH_SHORT).show()
                    }
                } else {//사진 선택 해제
                    imgMediaImgList.setBackgroundResource(Color.TRANSPARENT)
                    imgMediaImgList.tag = "false"
                    selectImg.remove(imgList[adapterPosition])
                }

                // selectImg 변경 시 OnImageSelectListener 호출
                mOnImageSelectListener.onImageSelect(selectImg)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.img_list, parent, false)

        //키보드 높이에 맞춰 높이값 변경
        val layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.layoutParams = layoutParams

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val uri = imgList[position].toString()

//        holder.imgMediaImgList.setImageBitmap(BitmapFactory.decodeFile(uri))
        Log.d("TAG-Glide 내 uri", uri.toString())

        if (imgList.size != 0) {
            Glide.with(context)
                .load(uri)
                .into(holder.imgMediaImgList)
            Log.d("TAG-Glide 내 uri", uri.toString())
        }
    }

    override fun getItemCount(): Int {
        return imgList.size
    }
}