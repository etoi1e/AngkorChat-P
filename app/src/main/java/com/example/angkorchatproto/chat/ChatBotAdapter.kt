package com.example.angkorchatproto.chat


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


class ChatBotAdapter(context: Context, chatList: ArrayList<ChatBotVO>, width: Int, time: String) :
    RecyclerView.Adapter<ChatBotAdapter.ViewHolder>() {

    var chatList: ArrayList<ChatBotVO>
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
        var tvTimeRight: TextView
        var tvTimeLeft: TextView


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

        val message: ChatBotVO = chatList[position]



        //시간 커스텀
        var setTime = ""
        var setDate = message.time?.substring(0, 10)
        val setAm = message.time?.substring(11, 12)?.toInt()
        if (setAm!! >= 12) {
            setTime = "PM" + message.time?.substring(11, 16)
        } else {
            setTime = "AM" + message.time?.substring(11, 16)
        }


        //디바이스 가로 길이에 맞춰 말풍선 고정
        holder.tvMyMessageChat.maxWidth = width - 250
        holder.tvOtherMessageChat.maxWidth = width - 250



        if (message.sentBy.equals(ChatBotVO.SENT_BY_ME)) { //내가 보낸 메세지인 경우
            holder.tvOtherMessageChat.visibility = View.GONE
            holder.tvTimeLeft.visibility = View.GONE

            holder.tvMyMessageChat.setText(message.message)
            holder.tvTimeRight.setText(setTime)


        } else { //타인이 보낸 메세지인 경우
            holder.tvMyMessageChat.visibility = View.GONE
            holder.tvTimeRight.visibility = View.GONE

            holder.tvOtherMessageChat.setText(message.message)
            holder.tvTimeLeft.setText(setTime)

        }

        //뷰 재활용 막기(데이터꼬임방지)
        holder.setIsRecyclable(false)

    }

    override fun getItemCount(): Int {
        return chatList.size
    }


}