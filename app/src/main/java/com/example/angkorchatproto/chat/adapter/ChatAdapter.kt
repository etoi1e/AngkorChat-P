package com.example.angkorchatproto.chat.adapter

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.angkorchatproto.chat.ChatModel
import com.example.angkorchatproto.chat.ReactionActivity
import com.example.angkorchatproto.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.lang.Exception
import java.util.zip.Inflater


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
        var ivMyImoge: ImageView
        var ivOtherImoge: ImageView
        var divChatList: ConstraintLayout
        var tvTimeRight: TextView
        var tvTimeLeft: TextView

        init {
            tvMyMessageChat = itemView.findViewById(R.id.tvMyMessageChat)
            tvOtherMessageChat = itemView.findViewById(R.id.tvOtherMessageChat)
            ivMyImoge = itemView.findViewById(R.id.ivMyImoge)
            ivOtherImoge = itemView.findViewById(R.id.ivOtherImoge)
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
            if (message.message == "") {
                holder.tvMyMessageChat.visibility = View.GONE
            } else {
                holder.tvMyMessageChat.setText(message.message)
            }
            holder.tvTimeRight.setText(setTime)

            if (message.emo != null &&
                message.emo != ""
            ) {
                holder.ivMyImoge.visibility = View.VISIBLE
                Glide.with(context)
                    .load(parseImogeString(message.emo))
                    .into(holder.ivMyImoge)
            }

            if (message.url != "" && message.url != null) {

                val storage = FirebaseStorage.getInstance("gs://angkor-ae0c0.appspot.com")

                val storageRef = storage.getReference()
                val imgRef = storageRef.child("/${message.url}.png")

                Log.d("TAG-imgRef", imgRef.toString())

                imgRef.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                    override fun onSuccess(p0: Uri?) {

                        holder.ivMyImoge.visibility = View.VISIBLE

                        Glide.with(context)
                            .load(p0)
                            .into(holder.ivMyImoge)

                        Log.d("TAG-Uri", p0.toString())
                    }

                }).addOnFailureListener(object : OnFailureListener {
                    override fun onFailure(p0: Exception) {
                        Log.d("TAG-onFailure", p0.toString())
                    }

                })

            }



        } else {//타인이 보낸 메세지인 경우

            holder.tvMyMessageChat.visibility = View.GONE
            holder.tvTimeRight.visibility = View.GONE

            if (message.message == "") {
                holder.tvOtherMessageChat.visibility = View.GONE
            } else {
                holder.tvOtherMessageChat.setText(message.message)
            }

            holder.tvTimeLeft.setText(setTime)

            holder.tvOtherMessageChat.setOnClickListener(object : OnClickListener {
                override fun onClick(p0: View?) {

                    val intent = Intent(context, ReactionActivity::class.java)

                    context.startActivity(intent)

                }

            })

            if (message.emo != null &&
                message.emo != ""
            ) {
                holder.ivOtherImoge.visibility = View.VISIBLE
                Glide.with(context)
                    .load(parseImogeString(message.emo))
                    .into(holder.ivOtherImoge)
            }

            if (message.url != "" && message.url != null) {

                val storage = FirebaseStorage.getInstance("gs://angkor-ae0c0.appspot.com")

                val storageRef = storage.getReference()
                val imgRef = storageRef.child("${message.url}.png")

                Log.d("TAG-imgRef", imgRef.toString())

                imgRef.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                    override fun onSuccess(p0: Uri?) {

                        holder.ivOtherImoge.visibility = View.VISIBLE

                        Glide.with(context)
                            .load(p0)
                            .into(holder.ivOtherImoge)

                        Log.d("TAG-Uri", p0.toString())
                    }

                }).addOnFailureListener(object : OnFailureListener {
                    override fun onFailure(p0: Exception) {
                        Log.d("TAG-onFailure", p0.toString())
                    }

                })

            }
        }

        //뷰 재활용 막기(데이터꼬임방지)
        holder.setIsRecyclable(false)


    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    private fun parseImogeString(str: String?): Int {
        //haha$$3
        val imogeStrArray = str?.split("$$")
        val array: TypedArray =
            when (imogeStrArray?.get(0)) {
                "gana" -> {
                    context.resources.obtainTypedArray(R.array.ganaImoge)
                }
                "haha" -> {
                    context.resources.obtainTypedArray(R.array.hahaImoge)
                }
                "nunu" -> {
                    context.resources.obtainTypedArray(R.array.nunuImoge)
                }
                else -> {
                    null
                }
            } ?: return -1

        return array.getResourceId(imogeStrArray?.get(1)?.toInt()?.minus(1)!!, -1)
    }
}