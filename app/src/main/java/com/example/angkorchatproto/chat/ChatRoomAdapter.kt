package com.example.angkorchatproto.chat

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.angkorchatproto.R
import com.example.angkorchatproto.chat.adapter.ChatAdapter
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue


class ChatRoomAdapter(
    val context: Context,
    val chatInfoList: ArrayList<ChatModel.Comment>,
    val chatRoomKey: ArrayList<String>,
    var myNumber: String,
    val chatCount: ArrayList<String>
) :
    RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {

    var sender = ""

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
        val layout: ConstraintLayout

        init {

            imgProfileChatList = itemView.findViewById(R.id.imgProfileChatList)
            tvNameChatList = itemView.findViewById(R.id.tvNameChatList)
            tvMessageChatList = itemView.findViewById(R.id.tvMessageChatList)
            tvTimeChatList = itemView.findViewById(R.id.tvTimeChatList)
            tvCountChatChatList = itemView.findViewById(R.id.tvCountChatChatList)
            layout = itemView.findViewById(R.id.layout)


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

        val usersRef = FBdataBase.getChatRef().child(chatRoomKey[position]).child("users")

        //상대방 번호
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.getValue() as Map<*, *>
                for (user in users.keys) {
                    if (user != myNumber) {

                        sender = user.toString()

                        //번호로 유저 이름 찾기
                        val friendRef = FBdataBase.getFriendRef()

                        friendRef.child(myNumber).child(user.toString()).child("name")
                            .addListenerForSingleValueEvent(object : ValueEventListener {

                                @SuppressLint("SetTextI18n")
                                override fun onDataChange(snapshot: DataSnapshot) {

                                    val name = snapshot.value.toString()
                                    holder.tvNameChatList.text = name

                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }

                            })

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // 에러 처리
            }
        })


        if (chatRoom.profile == "") {
            Glide.with(context)
                .load(R.drawable.ic_profile_default_72)
                .into(holder.imgProfileChatList)
        } else {
            Glide.with(context)
                .load(chatRoom.profile)
                .into(holder.imgProfileChatList)
        }



        holder.layout.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name", holder.tvNameChatList.text)
            intent.putExtra("number", sender)
            context.startActivity(intent)
        }

        //채팅방 내용 출력

        holder.tvMessageChatList.text = chatRoom.message
        holder.tvCountChatChatList.text = chatCount.size.toString()
        holder.tvTimeChatList.text = setTime


    }

    override fun getItemCount(): Int {
        return chatInfoList.size
    }


}