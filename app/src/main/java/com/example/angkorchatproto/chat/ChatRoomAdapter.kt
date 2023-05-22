package com.example.angkorchatproto.chat

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.angkorchatproto.R
import com.example.angkorchatproto.chat.adapter.ChatAdapter
import com.example.angkorchatproto.dialog.CustomDialog
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kr.co.kdnavien.naviensmart.presentation.custom.DialogNegativeBtnListener
import kr.co.kdnavien.naviensmart.presentation.custom.DialogPositiveBtnListener


class ChatRoomAdapter(
    val context: Context,
    val chatInfoList: ArrayList<ChatModel.Comment>,
    val chatRoomKey: ArrayList<String>,
    var myNumber: String,
    val chatCount: ArrayList<String>
) :
    RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {

    var sender = ArrayList<String>()
    val chatRef = FBdataBase.getChatRef()

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
        chatRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("TAG-채팅방 삭제","확인")
                Log.d("TAG-채팅방 삭제 snapshot" ,snapshot.toString())

               notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
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

        if (chatInfoList[position].profile == "") {
            Glide.with(context)
                .load(R.drawable.ic_profile_default_72)
                .into(holder.imgProfileChatList)
        }

        //상대방 번호
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (user in snapshot.children) {
                    if (user.key != myNumber) {

                        sender.add(user.toString())

                        //번호로 유저 이름 찾기
                        val friendRef = FBdataBase.getFriendRef()

                        friendRef.child(myNumber).child(user.key.toString()).child("name")
                            .addListenerForSingleValueEvent(object : ValueEventListener {

                                @SuppressLint("SetTextI18n")
                                override fun onDataChange(snapshot: DataSnapshot) {

                                    var name = snapshot.value.toString()

                                    if (name == "null") {
//                                        name = user.toString()
                                        name = user.key.toString()
                                    }

                                    holder.tvNameChatList.text = name
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }


                            })

                        friendRef.child(myNumber).child(user.key.toString()).child("profile")
                            .addListenerForSingleValueEvent(object : ValueEventListener {

                                @SuppressLint("SetTextI18n")
                                override fun onDataChange(snapshot: DataSnapshot) {

                                    var profile = snapshot.value.toString()

                                    if (profile == "") {
                                        Glide.with(context)
                                            .load(R.drawable.ic_profile_default_72)
                                            .into(holder.imgProfileChatList)
                                    } else {
                                        Glide.with(context)
                                            .load(profile)
                                            .into(holder.imgProfileChatList)
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }


                            })



                        holder.layout.setOnClickListener {
                            val intent = Intent(context, ChatActivity::class.java)
                            intent.putExtra("name", holder.tvNameChatList.text)
                            intent.putExtra("number", user.toString())
                            context.startActivity(intent)
                        }



                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // 에러 처리
            }
        })




        //채팅방 내용 출력

        if (chatRoom.file != "") {
            holder.tvMessageChatList.text = "File"
        }

        if (chatRoom.url != "") {
            holder.tvMessageChatList.text = "Photo"
        }

        if (chatRoom.emo != "") {
            holder.tvMessageChatList.text = "Emoticon"
        }

        if (chatRoom.message != "") {
            holder.tvMessageChatList.text = chatRoom.message
        }


        holder.tvCountChatChatList.text = chatCount.size.toString()
        holder.tvTimeChatList.text = setTime

        //롱클릭 삭제
        holder.layout.setOnLongClickListener(object : OnLongClickListener {
            @SuppressLint("ResourceAsColor")
            override fun onLongClick(p0: View?): Boolean {

                val title = "Leave Chatroom"
                val spannable = SpannableStringBuilder(title)
//                val color = getColor(R.color.mainYellow)
                spannable.setSpan(
                    ForegroundColorSpan(R.color.mainYellow),
                    0,
                    title.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                CustomDialog.create(context)
                    ?.setTitle(spannable)
                    ?.setDesc(SpannableStringBuilder("When you leave the chatroom\nall the data delete"))
                    ?.setCancelable(true)
                    ?.setPositiveButtonText(SpannableStringBuilder("Leave"))
                    ?.setNegativeButtonText(SpannableStringBuilder("Cancel"))
                    ?.setPositiveBtnListener(object : DialogPositiveBtnListener {
                        override fun confirm(division: Int) {


                            chatRef.child(chatRoom.key.toString()).removeValue()
                            chatInfoList.removeAt(position)

                        }
                    })
                    ?.setNegativeBtnListener(object : DialogNegativeBtnListener {
                        override fun cancel(division: Int) {
                        }
                    })
                    ?.showTwoButton()




                return false
            }

        })
    }

    override fun getItemCount(): Int {
        return chatInfoList.size
    }


}