package com.example.angkorchatproto.chat

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.angkorchatproto.databinding.ActivityReactionBinding
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.childEvents
import com.google.firebase.database.ktx.getValue
import com.google.firebase.database.ktx.values

class ReactionActivity : AppCompatActivity() {

    lateinit var binding: ActivityReactionBinding

    val chatRef = FBdataBase.getChatRef()

    lateinit var getKey :String
    lateinit var getCommkey :String



    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityReactionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //SharedPreferences
        val shared = getSharedPreferences("reply", 0)
        val editor = shared.edit()


        //chatActivity에서 intent로 값 받아오기
        getKey = intent.getStringExtra("key").toString()
        getCommkey = intent.getStringExtra("commkey").toString()


        Log.d("TAG-key", getKey)

        //필요한 정보 -> chat Data Key값


        //답장기능
        binding.viewReplyReactrion.setOnClickListener {
            editor.putString("replyKey",getCommkey)
            editor.commit()

            finish()
        }

        //복사
        binding.viewCopyReactrion.setOnClickListener {

            chatRef.child("$getKey/comments").child(getCommkey)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val content = snapshot.getValue<ChatModel.Comment>()
                        if (content != null) {
                            if (content.message != null && content.message != "") {
                                //메세지가 있는 경우
                                copyToClipboard(content.message)
                            } else {
                                Toast.makeText(
                                    this@ReactionActivity,
                                    "복사는 텍스트만 가능합니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })


        }

        //고정(기능X)
        binding.viewPinReactrion.setOnClickListener {
            Toast.makeText(this@ReactionActivity, "Pin Clicked", Toast.LENGTH_SHORT).show()
        }

        //삭제
        binding.viewDeleteReactrion.setOnClickListener {

            chatRef.child("$getKey/comments").child(getCommkey).removeValue()

            finish()

        }


        //리액션달기

        var reactrion = ""

        binding.imgHeartReactrion.setOnClickListener {
            reactrion = "❤"
            setReaction(reactrion)

            Toast.makeText(this@ReactionActivity, "❤", Toast.LENGTH_SHORT).show()
        }

        binding.imgThumbReactrion.setOnClickListener {
            reactrion = "👍"
            setReaction(reactrion)
            Toast.makeText(this@ReactionActivity, "👍", Toast.LENGTH_SHORT).show()
        }

        binding.imgSmileReactrion.setOnClickListener {
            reactrion = "😊"
            setReaction(reactrion)
            Toast.makeText(this@ReactionActivity, "😊", Toast.LENGTH_SHORT).show()
        }

        binding.imgHappyReactrion.setOnClickListener {
            reactrion = "😆"
            setReaction(reactrion)
            Toast.makeText(this@ReactionActivity, "😆", Toast.LENGTH_SHORT).show()
        }

        binding.imgAngryReactrion.setOnClickListener {
            reactrion = "😠"
            setReaction(reactrion)
            Toast.makeText(this@ReactionActivity, "😠", Toast.LENGTH_SHORT).show()
        }

        binding.imgCryReactrion.setOnClickListener {
            reactrion = "😭"
            setReaction(reactrion)
            Toast.makeText(this@ReactionActivity, "😭", Toast.LENGTH_SHORT).show()
        }




    }
    //onCreate 바깥

    //텍스트 클립보드에 저장
    fun copyToClipboard(text: String) {
        val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("label", text)
        clipboardManager.setPrimaryClip(clipData)
    }
    fun setReaction(reaction:String){
        chatRef.child("$getKey/comments").child(getCommkey).child("reaction").setValue(reaction)
        finish()
    }

    fun setReply(reply:String){
        chatRef.child("$getKey/comments").child(getCommkey).child("reply").setValue(reply)
        finish()
    }


}