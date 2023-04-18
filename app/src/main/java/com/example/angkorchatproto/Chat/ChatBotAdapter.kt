package com.example.angkorchatproto.Chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.R

class ChatBotAdapter(context: Context,chatList : ArrayList<ChatVO>):
    RecyclerView.Adapter<ChatBotAdapter.ViewHolder>() {

    var chatList: ArrayList<ChatVO>
    lateinit var context : Context

    init {
        this.chatList = chatList
        this.context = context

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvMyMessageChat: TextView
        var tvOtherMessageChat: TextView

        init {

            tvMyMessageChat = itemView.findViewById(R.id.tvMyMessageChat)
            tvOtherMessageChat = itemView.findViewById(R.id.tvOtherMessageChat)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.chat_list, null)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message: ChatVO = chatList[position]

        if (message.sentBy.equals(ChatVO.SENT_BY_ME)) {
            holder.tvOtherMessageChat.visibility = View.GONE
            holder.tvMyMessageChat.setText(message.message)
        } else {
            holder.tvMyMessageChat.visibility = View.GONE
            holder.tvOtherMessageChat.setText(message.message)
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }


}