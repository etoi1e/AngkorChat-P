package com.example.angkorchatproto.Chat

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.R
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue


class ChatAdapter(
    context: Context,
    chatList: ArrayList<ChatModel.Comment>,
    width: Int,
    myNumber: String
) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    var chatList: ArrayList<ChatModel.Comment>
    var context: Context
    var width: Int
    var myNumber: String

    init {
        this.chatList = chatList
        this.context = context
        this.width = width
        this.myNumber = myNumber

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.chat_list, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val message: ChatModel.Comment = chatList[position]


        //시간 커스텀
        var setTime = ""
        //var setDate = message.time?.substring(0, 10)
        val setAm = message.time?.substring(11, 12)?.toInt()
        if (setAm!! >= 12) {
            setTime = "PM" + message.time?.substring(11, 16)
        } else {
            setTime = "AM" + message.time?.substring(11, 16)
        }


        //디바이스 가로 길이에 맞춰 말풍선 고정
        holder.tvMyMessageChat.maxWidth = width - 250
        holder.tvOtherMessageChat.maxWidth = width - 250


        if (message.sender.equals(myNumber)) { //내가 보낸 메세지인 경우
            holder.tvOtherMessageChat.visibility = View.GONE
            holder.tvTimeLeft.visibility = View.GONE

            holder.tvMyMessageChat.setText(message.message)
            holder.tvTimeRight.setText(setTime)


            //롱탭 팝업 활성화
            holder.tvMyMessageChat.setOnLongClickListener(object : OnLongClickListener {
                override fun onLongClick(p0: View?): Boolean {
                    fun showPopup(v: View) {
                        val popup = PopupMenu(context, v) // PopupMenu 객체 선언
                        popup.menuInflater.inflate(
                            R.menu.reaction_menu,
                            popup.menu
                        ) // 메뉴 레이아웃 inflate
                        popup.show() // 팝업 보여주기
                    }
                    showPopup(holder.tvMyMessageChat)

                    Log.d("TAG-chatRoomKey", "활성화")
                    return true
                }

            })


        } else {//타인이 보낸 메세지인 경우

            holder.tvMyMessageChat.visibility = View.GONE
            holder.tvTimeRight.visibility = View.GONE

            holder.tvOtherMessageChat.setText(message.message)
            holder.tvTimeLeft.setText(setTime)

            holder.tvOtherMessageChat

            holder.tvOtherMessageChat.setOnClickListener(object : OnClickListener {
                override fun onClick(p0: View?) {

                    val intent = Intent(context, ReactionActivity::class.java)

                    context.startActivity(intent)

                }

            })


        }


        //뷰 재활용 막기(데이터꼬임방지)
        holder.setIsRecyclable(false)


    }

    override fun getItemCount(): Int {
        return chatList.size
    }

}