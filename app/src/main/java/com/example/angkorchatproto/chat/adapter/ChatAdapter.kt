package com.example.angkorchatproto.chat.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.net.Uri
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.angkorchatproto.chat.ChatModel
import com.example.angkorchatproto.chat.ReactionActivity
import com.example.angkorchatproto.R
import com.example.angkorchatproto.chat.ImgViewActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.lang.Exception


class ChatAdapter(
    context: Context,
    chatList: ArrayList<ChatModel.Comment>,
    commentKeyList: ArrayList<String>,
    width: Int,
    myNumber: String
) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    var chatList: ArrayList<ChatModel.Comment>
    var commentKeyList: ArrayList<String>
    var context: Context
    var width: Int
    var myNumber: String

    init {
        this.chatList = chatList
        this.commentKeyList = commentKeyList
        this.context = context
        this.width = width
        this.myNumber = myNumber

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var divChatList: ConstraintLayout

        //내가 보낸 채팅 views
        var myChatLayout: LinearLayout

        //파일부분
        var myFileLayout: ConstraintLayout
        var tvMyFileName: TextView
        var tvMyFileDate: TextView
        var tvMyFileSize: TextView

        //이미지
        var ivMySendImg: ImageView

        //이모티콘
        var ivMyImoge: ImageView

        //텍스트
        var myMessageLayout: ConstraintLayout
        var tvMyMessageChat: TextView
        var tvTimeRight: TextView

        //상대방이 보낸 채팅 views
        var otherChatLayout: LinearLayout

        //파일부분
        var otherFileLayout: ConstraintLayout
        var tvOtherFileName: TextView
        var tvOtherFileDate: TextView
        var tvOtherFileSize: TextView

        //이미지
        var ivOtherSendImg: ImageView

        //이모티콘
        var ivOtherImoge: ImageView

        //텍스트
        var messageLayoutOther: ConstraintLayout
        var tvOtherMessageChat: TextView
        var tvTimeLeft: TextView

        var tvMyCharReaction: TextView
        var tvOtherCharReaction: TextView


        init {
            divChatList = itemView.findViewById(R.id.divChatList)

            myChatLayout = itemView.findViewById(R.id.myChatLayout)

            myFileLayout = itemView.findViewById(R.id.myFileLayout)
            tvMyFileName = itemView.findViewById(R.id.tvMyFileName)
            tvMyFileDate = itemView.findViewById(R.id.tvMyFileDate)
            tvMyFileSize = itemView.findViewById(R.id.tvMyFileSize)

            ivMySendImg = itemView.findViewById(R.id.ivMySendImg)

            ivMyImoge = itemView.findViewById(R.id.ivMyImoge)

            myMessageLayout = itemView.findViewById(R.id.myMessageLayout)
            tvMyMessageChat = itemView.findViewById(R.id.tvMyMessageChat)
            tvTimeRight = itemView.findViewById(R.id.tvTimeRight)
            tvMyCharReaction = itemView.findViewById(R.id.tvMyChatReaction)



            otherChatLayout = itemView.findViewById(R.id.otherChatLayout)

            otherFileLayout = itemView.findViewById(R.id.otherFileLayout)
            tvOtherFileName = itemView.findViewById(R.id.tvOtherFileName)
            tvOtherFileDate = itemView.findViewById(R.id.tvOtherFileDate)
            tvOtherFileSize = itemView.findViewById(R.id.tvOtherFileSize)

            ivOtherSendImg = itemView.findViewById(R.id.ivOtherSendImg)

            ivOtherImoge = itemView.findViewById(R.id.ivOtherImoge)

            messageLayoutOther = itemView.findViewById(R.id.messageLayoutOther)
            tvOtherMessageChat = itemView.findViewById(R.id.tvOtherMessageChat)
            tvTimeLeft = itemView.findViewById(R.id.tvTimeLeft)
            tvOtherCharReaction = itemView.findViewById(R.id.tvOtherChatReaction)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.chat_list, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val message: ChatModel.Comment = chatList[position]
        val commentKey = commentKeyList[position]


        //시간 커스텀
        var setTime = ""
        val setAm = message.time?.substring(11, 13)?.toInt()
        if (setAm!! >= 12) {
            val setHour = message.time?.substring(11, 13)?.toInt()
            val setMin = message.time?.substring(14, 16)
            if (setHour!! - 12 >= 10) {
                setTime = "PM${setHour!! - 12}:$setMin"
            } else {
                setTime = "PM0${setHour!! - 12}:$setMin"
            }
        } else {
            setTime = "AM" + message.time?.substring(11, 16)
        }


        //디바이스 가로 길이에 맞춰 말풍선 고정
        holder.tvMyMessageChat.maxWidth = width - 250
        holder.tvOtherMessageChat.maxWidth = width - 250


        //내가 보낸 메세지인 경우
        if (message.sender.equals(myNumber)) {

            holder.otherChatLayout.visibility = View.GONE
            holder.tvTimeLeft.visibility = View.GONE

            //리액션

            Log.d("TAG-reaction",message.reaction.toString())
            holder.tvMyCharReaction.visibility = View.GONE
            if(message.reaction != "" ){
                holder.tvMyCharReaction.visibility = View.VISIBLE
                holder.tvMyCharReaction.text=message.reaction
            }
            if(message.reaction == null){
                holder.tvMyCharReaction.visibility = View.GONE
            }

            //메세지
            if (message.message == "" ) {

                holder.myMessageLayout.visibility = View.GONE
            } else {
                holder.tvMyMessageChat.setText(message.message)
            }

            // 롱클릭 이벤트
            holder.tvMyMessageChat.setOnLongClickListener(object : OnLongClickListener {
                override fun onLongClick(p0: View?): Boolean {

                    val intent = Intent(context, ReactionActivity::class.java)
                    intent.putExtra("commkey",commentKey)
                    intent.putExtra("key",message.key)
                    context.startActivity(intent)

                    return false

                }

            })


            //시간
            holder.tvTimeRight.setText(setTime)

            //이모티콘
            if (message.emo != null &&
                message.emo != ""
            ) {
                holder.ivMyImoge.visibility = View.VISIBLE
                Glide.with(context)
                    .load(parseImogeString(message.emo))
                    .into(holder.ivMyImoge)
            }

            //이미지
            if (message.url != "" && message.url != null) {

                //카메라 촬영 후 전송할 때
                if (message.url.contains("content://media/")) {
                    val storage = FirebaseStorage.getInstance("gs://angkor-ae0c0.appspot.com")

                    val storageRef = storage.getReference()
                    val imgRef = storageRef.child("/${message.url}.png")

                    imgRef.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                        override fun onSuccess(p0: Uri?) {

                            holder.ivMySendImg.visibility = View.VISIBLE


                            Glide.with(context)
                                .load(p0)
                                .into(holder.ivMySendImg)

                            //이미지 클릭 시 전체 화면으로 보여주기
                            if (holder.ivMySendImg.visibility == View.VISIBLE) {
                                holder.ivMySendImg.setOnClickListener {


                                    val intent = Intent(context, ImgViewActivity::class.java)
                                    intent.putExtra("imgPath", p0.toString())

                                    context.startActivity(intent)

                                }
                                holder.ivMySendImg.setOnLongClickListener(object : OnLongClickListener{
                                    override fun onLongClick(p0: View?): Boolean {

                                        val intent = Intent(context, ReactionActivity::class.java)
                                        intent.putExtra("commkey",commentKey)
                                        intent.putExtra("key",message.key)
                                        context.startActivity(intent)

                                        return false

                                    }
                                })
                            }


                        }

                    }).addOnFailureListener(object : OnFailureListener {
                        override fun onFailure(p0: Exception) {
                            Log.d("TAG-onFailure", p0.toString())
                        }

                    })

                } else { //갤러리에서 선택해서 전송할 때
                    val storage = FirebaseStorage.getInstance("gs://angkor-ae0c0.appspot.com")

                    val storageRef = storage.getReference()
                    val imgRef = storageRef.child("/${message.url}")

                    imgRef.listAll()
                        .addOnSuccessListener(object : OnSuccessListener<ListResult> {
                            override fun onSuccess(p0: ListResult?) {
                                holder.ivMySendImg.visibility = View.VISIBLE

                                val selectedPhotoList = p0!!.items

                                if (selectedPhotoList.size > 0) {
                                    selectedPhotoList.get(0).downloadUrl.addOnCompleteListener(
                                        object : OnCompleteListener<Uri> {
                                            override fun onComplete(p0: Task<Uri>) {
                                                if (p0.isSuccessful) {
                                                    Glide.with(context)
                                                        .load(p0.getResult())
                                                        .into(holder.ivMySendImg)

                                                    //이미지 클릭 시 전체 화면으로 보여주기
                                                    if (holder.ivMySendImg.visibility == View.VISIBLE) {
                                                        holder.ivMySendImg.setOnClickListener {


                                                            val intent = Intent(
                                                                context,
                                                                ImgViewActivity::class.java
                                                            )
                                                            intent.putExtra(
                                                                "imgPath",
                                                                p0.getResult().toString()
                                                            )
                                                            context.startActivity(intent)

                                                        }
                                                        holder.ivMySendImg.setOnLongClickListener(object : OnLongClickListener{
                                                            override fun onLongClick(p0: View?): Boolean {

                                                                val intent = Intent(context, ReactionActivity::class.java)
                                                                intent.putExtra("commkey",commentKey)
                                                                intent.putExtra("key",message.key)
                                                                context.startActivity(intent)

                                                                return false

                                                            }
                                                        })
                                                    }


                                                }

                                            }
                                        })
                                }

                            }

                        })

                        .addOnFailureListener(object : OnFailureListener {
                            override fun onFailure(p0: Exception) {
                                Log.d("TAG-onFailure2", p0.toString())
                            }

                        })

                }


            }


            //파일
            if (message.file != "") {
                holder.myFileLayout.visibility = View.VISIBLE

                holder.myFileLayout.setOnClickListener {

                    val storageReference = Firebase.storage.reference
                    val fileRef = storageReference.child(message.file.toString())

                    fileRef.downloadUrl.addOnSuccessListener{

                    }
                    
                    //다운로드 주소로 변환
                    var downloadReference = Firebase.storage.getReferenceFromUrl(message.file.toString().
                    replace("gs://angkor-ae0c0.appspot.com/","https://firebasestorage.googleapis.com/v0/b/angkor-ae0c0.appspot.com/o/"))

                    //파일의 확장자
                    var fileType = downloadReference.name.substring(downloadReference.name.indexOf("."),downloadReference.name.lastIndex+1)

                    val destinationPath = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)

                    //내부 저장소에 저장되는 이름
                    val localFile = File.createTempFile("AngkorChat",fileType,destinationPath)

                    downloadReference.getFile(localFile).addOnCompleteListener{
                        Log.d("TAG-downloadReference", downloadReference.toString())
                        Toast.makeText(context, "다운로드 완료",Toast.LENGTH_LONG).show()
                        Log.d("TAG-파일이름", "name: ${downloadReference.name}")
                    }.addOnProgressListener {
                        Log.d("TAG-addOnProgressListener", "OnProgressListener")
                    }.addOnFailureListener{
                            Log.e("TAG-저장실패",it.message.toString())
                        }
                }

                holder.myFileLayout.setOnLongClickListener(object : OnLongClickListener{
                    override fun onLongClick(p0: View?): Boolean {

                        val intent = Intent(context, ReactionActivity::class.java)
                        intent.putExtra("commkey",commentKey)
                        intent.putExtra("key",message.key)
                        context.startActivity(intent)

                        return false

                    }
                })
            }


        } else {//타인이 보낸 메세지인 경우

            //리액션
            holder.tvOtherCharReaction.visibility = View.GONE

            if(message.reaction != "" ){
                holder.tvOtherCharReaction.visibility = View.VISIBLE
                holder.tvOtherCharReaction.text=message.reaction
            }

            holder.myMessageLayout.visibility = View.GONE
            holder.tvTimeRight.visibility = View.GONE

            if (message.message == "") {
                holder.tvOtherMessageChat.visibility = View.GONE
            } else {
                holder.tvOtherMessageChat.setText(message.message)
            }

            holder.tvTimeLeft.setText(setTime)

            //롱클릭 이벤트
            holder.tvOtherMessageChat.setOnLongClickListener(object : OnLongClickListener {
                override fun onLongClick(p0: View?): Boolean {

                    val intent = Intent(context, ReactionActivity::class.java)
                    intent.putExtra("commkey",commentKey)
                    intent.putExtra("key",message.key)
                    context.startActivity(intent)

                    return false

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

            //이미지
            if (message.url != "" && message.url != null) {

                //카메라 촬영 후 전송할 때
                if (message.url.contains("content://media/")) {
                    val storage = FirebaseStorage.getInstance("gs://angkor-ae0c0.appspot.com")

                    val storageRef = storage.getReference()
                    val imgRef = storageRef.child("/${message.url}.png")

                    imgRef.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                        override fun onSuccess(p0: Uri?) {

                            holder.ivOtherSendImg.visibility = View.VISIBLE


                            Glide.with(context)
                                .load(p0)
                                .into(holder.ivOtherSendImg)

                            //이미지 클릭 시 전체 화면으로 보여주기
                            if (holder.ivOtherSendImg.visibility == View.VISIBLE) {
                                holder.ivOtherSendImg.setOnClickListener {


                                    val intent = Intent(context, ImgViewActivity::class.java)
                                    intent.putExtra("imgPath", p0.toString())

                                    context.startActivity(intent)

                                }

                                holder.ivOtherSendImg.setOnLongClickListener(object : OnLongClickListener{
                                    override fun onLongClick(p0: View?): Boolean {

                                        val intent = Intent(context, ReactionActivity::class.java)
                                        intent.putExtra("commkey",commentKey)
                                        intent.putExtra("key",message.key)
                                        context.startActivity(intent)

                                        return false

                                    }
                                })
                            }


                        }

                    }).addOnFailureListener(object : OnFailureListener {
                        override fun onFailure(p0: Exception) {
                            Log.d("TAG-onFailure", p0.toString())
                        }

                    })

                } else { //갤러리에서 선택해서 전송할 때
                    val storage = FirebaseStorage.getInstance("gs://angkor-ae0c0.appspot.com")

                    val storageRef = storage.getReference()
                    val imgRef = storageRef.child("/${message.url}")

                    imgRef.listAll()
                        .addOnSuccessListener(object : OnSuccessListener<ListResult> {
                            override fun onSuccess(p0: ListResult?) {
                                holder.ivOtherSendImg.visibility = View.VISIBLE

                                val selectedPhotoList = p0!!.items

                                if (selectedPhotoList.size > 0) {
                                    selectedPhotoList.get(0).downloadUrl.addOnCompleteListener(
                                        object : OnCompleteListener<Uri> {
                                            override fun onComplete(p0: Task<Uri>) {
                                                if (p0.isSuccessful) {
                                                    Glide.with(context)
                                                        .load(p0.getResult())
                                                        .into(holder.ivOtherSendImg)

                                                    //이미지 클릭 시 전체 화면으로 보여주기
                                                    if (holder.ivOtherSendImg.visibility == View.VISIBLE) {
                                                        holder.ivOtherSendImg.setOnClickListener {


                                                            val intent = Intent(
                                                                context,
                                                                ImgViewActivity::class.java
                                                            )
                                                            intent.putExtra(
                                                                "imgPath",
                                                                p0.getResult().toString()
                                                            )
                                                            context.startActivity(intent)

                                                        }
                                                        holder.ivOtherSendImg.setOnLongClickListener(object : OnLongClickListener{
                                                            override fun onLongClick(p0: View?): Boolean {

                                                                val intent = Intent(context, ReactionActivity::class.java)
                                                                intent.putExtra("commkey",commentKey)
                                                                intent.putExtra("key",message.key)
                                                                context.startActivity(intent)

                                                                return false

                                                            }
                                                        })
                                                    }


                                                }

                                            }
                                        })
                                }

                            }

                        })

                        .addOnFailureListener(object : OnFailureListener {
                            override fun onFailure(p0: Exception) {
                                Log.d("TAG-onFailure2", p0.toString())
                            }

                        })

                }


            }

            //파일
            if (message.file != "") {
                holder.otherFileLayout.visibility = View.VISIBLE

                holder.otherFileLayout.setOnClickListener {

                    val storageReference = Firebase.storage.reference
                    val fileRef = storageReference.child(message.file.toString())

                    fileRef.downloadUrl.addOnSuccessListener{

                    }

                    //다운로드 주소로 변환
                    var downloadReference = Firebase.storage.getReferenceFromUrl(message.file.toString().
                    replace("gs://angkor-ae0c0.appspot.com/","https://firebasestorage.googleapis.com/v0/b/angkor-ae0c0.appspot.com/o/"))

                    //파일의 확장자
                    var fileType = downloadReference.name.substring(downloadReference.name.indexOf("."),downloadReference.name.lastIndex+1)

                    val destinationPath = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)

                    //내부 저장소에 저장되는 이름
                    val localFile = File.createTempFile("AngkorChat",fileType,destinationPath)

                    downloadReference.getFile(localFile).addOnCompleteListener{
                        Log.d("TAG-downloadReference", downloadReference.toString())
                        Toast.makeText(context, "다운로드 완료",Toast.LENGTH_LONG).show()
                        Log.d("TAG-파일이름", "name: ${downloadReference.name}")
                    }.addOnProgressListener {
                        Log.d("TAG-addOnProgressListener", "OnProgressListener")
                    }.addOnFailureListener{
                        Log.e("TAG-저장실패",it.message.toString())
                    }
                }

                holder.otherFileLayout.setOnLongClickListener(object : OnLongClickListener{
                    override fun onLongClick(p0: View?): Boolean {

                        val intent = Intent(context, ReactionActivity::class.java)
                        intent.putExtra("commkey",commentKey)
                        intent.putExtra("key",message.key)
                        context.startActivity(intent)

                        return false

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

    fun reply(key:String){

    }


}