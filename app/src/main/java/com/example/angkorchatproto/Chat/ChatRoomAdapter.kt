package com.example.angkorchatproto.Chat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.R

class ChatRoomAdapter(val context: Context, val chatInfoList : ArrayList<ChatModel.Comment>):
    RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {

    // 리스너 커스텀
    interface  OnItemClickListener{
        fun  onItemClick(view : View, position: Int)
    }

    // 객체 저장 변수 선언
    lateinit var mOnItemClickListener : OnItemClickListener

    //객체 전달 메서드
    fun setOnItemClickListener(OnItemClickListener : OnItemClickListener){
        mOnItemClickListener = OnItemClickListener

    }


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


            itemView.setOnClickListener {
                val position = adapterPosition

//                if (position != RecyclerView.NO_POSITION){
//                    // 버그로 인해 -1이 아닐경우에
//                    mOnItemClickListener.onItemClick(itemView,position)
//                }

                //클릭 시 채팅창으로 넘겨주는 부분
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("chatRoomKey","")

                context.startActivity(intent)



            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.chat_room_list, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chatRoom = chatInfoList[position]

        holder.imgProfileChatList.setImageResource(R.drawable.ic_profile_default_72)
        holder.tvMessageChatList.text = chatRoom.message
        holder.tvCountChatChatList.text = chatInfoList.size.toString()
        holder.tvTimeChatList.text = chatRoom.time
    }

    override fun getItemCount(): Int {
        return chatInfoList.size
    }


}