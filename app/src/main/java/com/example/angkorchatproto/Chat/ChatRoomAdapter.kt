package com.example.angkorchatproto.Chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.R

class ChatRoomAdapter(val context: Context, val chatRoomList : ArrayList<ChatVO>):
    RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgProfileChatList : ImageView
        val tvNameChatList : TextView
        val tvMessageChatList : TextView
        val tvTimeChatList : TextView
        val tvCountChatChatList : TextView


        init {

            imgProfileChatList = itemView.findViewById(R.id.imgProfileChatList)
            tvNameChatList = itemView.findViewById(R.id.tvNameChatList)
            tvMessageChatList = itemView.findViewById(R.id.tvMessageChatList)
            tvTimeChatList = itemView.findViewById(R.id.tvTimeChatList)
            tvCountChatChatList = itemView.findViewById(R.id.tvCountChatChatList)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.chat_room_list, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chatRoomInfo = chatRoomList[position]

        holder.imgProfileChatList.setImageResource(R.drawable.profile)
        holder.tvNameChatList.text = chatRoomInfo.user
        holder.tvMessageChatList.text = chatRoomInfo.message
        holder.tvCountChatChatList.text = chatRoomList.size.toString()
        holder.tvTimeChatList.text = chatRoomInfo.time



    }

    override fun getItemCount(): Int {
        return chatRoomList.size
    }


}