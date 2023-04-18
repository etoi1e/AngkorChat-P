package com.example.angkorchatproto.Chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.R


class ChatAdapter(val context: Context, val chatList: ArrayList<ChatVO>, val time : String) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val tvMyMessageChat:TextView

            init {

                tvMyMessageChat = itemView.findViewById(R.id.tvMyMessageChat)

            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.chat_list, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        if(chatList[position].number == myNumber){
//        holder.tvMyMessageChat.text = chatList[position].message
//        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}