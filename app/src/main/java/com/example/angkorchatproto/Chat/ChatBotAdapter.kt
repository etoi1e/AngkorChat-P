package com.example.angkorchatproto.Chat


import android.content.Context
import android.os.Build

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.R


class ChatBotAdapter(context: Context, chatList: ArrayList<ChatVO>, width: Int,time :String) :
    RecyclerView.Adapter<ChatBotAdapter.ViewHolder>() {

    var chatList: ArrayList<ChatVO>
    var context: Context
    var width: Int

    init {
        this.chatList = chatList
        this.context = context
        this.width = width

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvMyMessageChat: TextView
        var tvOtherMessageChat: TextView
        var divChatList: ConstraintLayout
        var tvTimeRight : TextView
        var tvTimeLeft : TextView

        init {

            tvMyMessageChat = itemView.findViewById(R.id.tvMyMessageChat)
            tvOtherMessageChat = itemView.findViewById(R.id.tvOtherMessageChat)
            divChatList = itemView.findViewById(R.id.divChatList)
            tvTimeRight = itemView.findViewById(R.id.tvTimeRight)
            tvTimeLeft = itemView.findViewById(R.id.tvTimeLeft)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.chat_list, null)
        return ViewHolder(view)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val message: ChatVO = chatList[position]

        //디바이스 가로 길이 구하기
        holder.tvMyMessageChat.maxWidth = width - 200
        holder.tvOtherMessageChat.maxWidth = width - 200

//        val current = LocalDateTime.now()
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//        val time = current.format(formatter)
//        val database = Firebase.database.reference


        if (message.sentBy.equals(ChatVO.SENT_BY_ME)) {
            holder.tvOtherMessageChat.visibility = View.GONE
            holder.tvTimeLeft.visibility = View.GONE

            holder.tvMyMessageChat.setText(message.message)
            holder.tvTimeRight.setText(message.time)


//            database.child("chatBot").child("+15555215554").child(time+message.sentBy)
//                .setValue(ChatVO(message.message, message.sentBy))


        } else {
            holder.tvMyMessageChat.visibility = View.GONE
            holder.tvTimeRight.visibility = View.GONE

            holder.tvOtherMessageChat.setText(message.message)
            holder.tvTimeLeft.setText(message.time)

//            database.child("chatBot").child("+15555215554").child(time+message.sentBy)
//                .setValue(ChatVO(message.message, message.sentBy))
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }


}