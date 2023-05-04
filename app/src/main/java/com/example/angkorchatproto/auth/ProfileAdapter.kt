package com.example.angkorchatproto.auth

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.angkorchatproto.R

class ProfileAdapter(val context: Context, val imgList: ArrayList<Uri?>) :
    RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgProfileImg: ImageView

        init {
            imgProfileImg = itemView.findViewById(R.id.imgProfileImg)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.profile_img_list, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(imgList[position])
            .into(holder.imgProfileImg)
    }

    override fun getItemCount(): Int {
        return imgList.size
    }
}