package com.example.angkorchatproto.Chat


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.angkorchatproto.R



class ChatRoomAdapter(
    val context: Context,
    val chatInfoList: ArrayList<ChatModel.Comment>,
    val chatRoomKey: ArrayList<String>,
    var chatRoomName : String,
    val chatCount : ArrayList<String>
) :
    RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {

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

        val imgProfileChatList: ImageView
        val tvNameChatList: TextView
        val tvMessageChatList: TextView
        val tvTimeChatList: TextView
        val tvCountChatChatList: TextView

        init {

            imgProfileChatList = itemView.findViewById(R.id.imgProfileChatList)
            tvNameChatList = itemView.findViewById(R.id.tvNameChatList)
            tvMessageChatList = itemView.findViewById(R.id.tvMessageChatList)
            tvTimeChatList = itemView.findViewById(R.id.tvTimeChatList)
            tvCountChatChatList = itemView.findViewById(R.id.tvCountChatChatList)


            itemView.setOnClickListener {
//                val position = adapterPosition
//
//                if (position != RecyclerView.NO_POSITION){
//                 //버그로 인해 -1이 아닐경우에
//                   mOnItemClickListener.onItemClick(itemView,position)
//               }


//                //클릭 시 채팅창으로 넘겨주는 부분
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("Key",chatInfoList[position].key)
                intent.putExtra("chatName",chatInfoList[position].sender)

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

        //시간 포맷팅
        var setTime = ""

        val setAm = chatRoom.time?.substring(11, 12)?.toInt()
        if (setAm!! >= 12) {
            setTime = "PM " + chatRoom.time?.substring(11, 16)
        } else {
            setTime = "AM " + chatRoom.time?.substring(11, 16)
        }

        //프로필 사진 셋팅
        if (chatRoom.profile == "") {
            Glide.with(context)
                .load(R.drawable.ic_profile_default_72)
                .into(holder.imgProfileChatList)
        } else {
            Glide.with(context)
                .load(chatRoom.profile)
                .into(holder.imgProfileChatList)
        }
        
        //채팅방 내용 출력
        holder.tvNameChatList.text = chatRoom.sender
        chatRoomName = chatRoom.sender.toString()
        holder.tvMessageChatList.text = chatRoom.message
        holder.tvCountChatChatList.text = chatCount[position]
        holder.tvTimeChatList.text = setTime


    }

    override fun getItemCount(): Int {
        return chatInfoList.size
    }


}
