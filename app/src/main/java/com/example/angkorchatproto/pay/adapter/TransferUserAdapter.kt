package com.example.angkorchatproto.pay.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.angkorchatproto.R


class TransferUserAdapter(
    val context: Context,
    val myNumber: String,
    val friendList: ArrayList<String>,
    val profileList: ArrayList<String>
) :
    RecyclerView.Adapter<TransferUserAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgProfileFriendsList: ImageView
        val tvNameFriendsList: TextView
        val tbSelectUser: ToggleButton

        init {
            imgProfileFriendsList = itemView.findViewById(R.id.imgProfileFriendsList)
            tvNameFriendsList = itemView.findViewById(R.id.tvNameFriendsList)
            tbSelectUser = itemView.findViewById(R.id.tbSelectUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.select_user_list, null)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = friendList[position]
        val profile = profileList[position]

        if (profile != "") {
            Glide.with(context)
                .load(profile)
                .into(holder.imgProfileFriendsList)
        } else {
            Glide.with(context)
                .load(R.drawable.ic_profile_default_72)
                .into(holder.imgProfileFriendsList)
        }

        holder.tvNameFriendsList.text = name


    }


    override fun getItemCount(): Int {
        return friendList.size
    }
}