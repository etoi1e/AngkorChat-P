package com.example.angkorchatproto.chat

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
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue


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

        //시간 포맷팅
        var setTime = ""

        val setAm = chatRoom.time?.substring(11, 12)?.toInt()
        if (setAm!! >= 12) {
            setTime = "PM " + chatRoom.time?.substring(11, 16)
        } else {
            setTime = "AM " + chatRoom.time?.substring(11, 16)
        }


        if (chatRoom.profile == "") {
            Glide.with(context)
                .load(R.drawable.ic_profile_default_72)
                .into(holder.imgProfileChatList)
        } else {
            Glide.with(context)
                .load(chatRoom.profile)
                .into(holder.imgProfileChatList)
        }

        //로그인한 계정 번호 불러오기
        //SharedPreferences
        val shared = context.getSharedPreferences("loginNumber", 0)
        val userNumber = shared.getString("userNumber", "").toString()

        val friendRef = FBdataBase.getFriendRef()

        //채팅방 목록 불러오기
        friendRef.child(userNumber).orderByChild("phone").equalTo(chatRoom.sender)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (item in snapshot.children) {
                        val key = item.key

                        friendRef.orderByChild("sender").equalTo(key.toString())




                        val chatModel = item.getValue<ChatModel>()

                    Log.d("TAG-스냅샷 key",key.toString())
                    Log.d("TAG-스냅샷",snapshot.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        holder.tvMessageChatList.text = chatRoom.message
        holder.tvCountChatChatList.text = chatInfoList.size.toString()
        holder.tvTimeChatList.text = setTime


    }

    override fun getItemCount(): Int {
        return chatInfoList.size
    }


}