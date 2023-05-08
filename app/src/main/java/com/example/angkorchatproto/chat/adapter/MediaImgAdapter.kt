package com.example.angkorchatproto.chat.adapter

import android.content.Context
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

    // 리스너 커스텀
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    // 객체 저장 변수 선언
    lateinit var mOnItemClickListener: OnItemClickListener

    //객체 전달 메서드
    fun setOnItemClickListener(OnItemClickListener: OnItemClickListener) {
        mOnItemClickListener = OnItemClickListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMediaImgList: ImageView = itemView.findViewById(R.id.imgMediaImgList)

        init {
            itemView.setOnClickListener {
                Toast.makeText(context,imgList[adapterPosition].toString(),Toast.LENGTH_SHORT).show()
                val selectImg=ArrayList<Uri?>()
                selectImg.add(imgList[adapterPosition])

                Log.d("TAG-선택 사진 목록",selectImg.toString())

            }


        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.img_list, parent, false)

        val layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.layoutParams = layoutParams

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (imgList.size != 0) {
            Glide.with(context)
                .load(imgList[position])
                .into(holder.imgMediaImgList)
        }
    }

    override fun getItemCount(): Int {
        return imgList.size
    }
}