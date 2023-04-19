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
    var time: String

    init {
        this.chatList = chatList
        this.context = context
        this.width = width
        this.time = time

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

        //디바이스 가로 길이에 맞춰 말풍선 고정
        holder.tvMyMessageChat.maxWidth = width - 250
        holder.tvOtherMessageChat.maxWidth = width - 250



        if (message.sentBy.equals(ChatVO.SENT_BY_ME)) { //내가 보낸 메세지인 경우
            holder.tvOtherMessageChat.visibility = View.GONE
            holder.tvTimeLeft.visibility = View.GONE

            holder.tvMyMessageChat.setText(message.message)
            holder.tvTimeRight.setText(message.time)



        } else { //타인이 보낸 메세지인 경우
            holder.tvMyMessageChat.visibility = View.GONE
            holder.tvTimeRight.visibility = View.GONE

            holder.tvOtherMessageChat.setText(message.message)
            holder.tvTimeLeft.setText(message.time)

        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }


}