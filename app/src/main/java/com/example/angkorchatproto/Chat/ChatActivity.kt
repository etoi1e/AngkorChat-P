package com.example.angkorchatproto.Chat

import android.graphics.Point
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.databinding.ActivityChatBinding
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import okhttp3.OkHttpClient
import java.time.LocalDateTime

class ChatActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding
    lateinit var myNumber: String
    lateinit var adapter: ChatAdapter
    var receiver: String = ""
    private var chatRoomKey: String? = null



    var width = 0
    var client = OkHttpClient()
    var chatRef = FBdataBase.getChatRef()
    var commentList = ArrayList<ChatModel.Comment>()


    @RequiresApi(Build.VERSION_CODES.O)
    var nowTime = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {


        binding = ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //상대방 번호 저장
        val receiver_data = intent.getStringExtra("number").toString()
        val receiver_data2 = receiver_data.replace("-", "")

        receiver = receiver_data2.replace(" ", "")

        //상대방 이름 출력
        val receiver_name = intent.getStringExtra("name").toString()
        binding.tvNameChat.text = receiver_name

        //채팅방 가져오기
        getMessageList()

        //뒤로가기
        binding.imgMoveBackChat.setOnClickListener {
            finish()
        }

        //객체 초기화
        client = OkHttpClient()

        //현재 사용자 번호 불러오기
        val shared = getSharedPreferences("loginNumber", 0)
        myNumber = shared.getString("userNumber", "").toString()


        //디스플레이 가로값 구해 넘겨주기(말풍선 크기 제한)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)
        width = size.x


        //전송 버튼 클릭 시
        binding.imgSendMessageChat.setOnClickListener {
            if (binding.etMessageChat.text.toString() != "") {
                val chatModel = ChatModel()
                chatModel.users.put(myNumber, true)
                chatModel.users.put(receiver!!, true)

                val comment =
                    ChatModel.Comment(myNumber, binding.etMessageChat.text.toString(), nowTime)
                if (chatRoomKey == null) {
                    binding.imgSendMessageChat.isEnabled = false
                    chatRef.push().setValue(chatModel).addOnSuccessListener {
                        //채팅방 생성
                        checkChatRoom()
                        //메세지 보내기
                        Handler().postDelayed({
                            chatRef.child(chatRoomKey.toString())
                                .child("comments").push().setValue(comment)
                        }, 1000L)
                        binding.etMessageChat.text = null
                    }
                } else {
                    chatRef.child(chatRoomKey.toString()).child("comments")
                        .push().setValue(comment)
                    binding.etMessageChat.text = null
                }
            }
        }

        checkChatRoom()

        Log.d("TAG-commentList", commentList.toString())


        //EditText에서 Enter 입력 시 전송
        binding.etMessageChat.setOnKeyListener() { v, keyCode, event ->
            var handled = false

            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {

                //전송 시 시간 초기화
                nowTime = LocalDateTime.now().toString()

                //전송 버튼 클릭효과
                binding.imgSendMessageChat.performClick()
                handled = true
            }
            handled
        }


        //EditText Focus 감지
        binding.etMessageChat.onFocusChangeListener = View.OnFocusChangeListener { v, gainFocus ->
            //포커스가 주어졌을 때
            if (gainFocus) {
                binding.viewMessageBox1Chat.visibility = View.INVISIBLE
                binding.imgRecordChat.visibility = View.GONE

                binding.imgSendMessageChat.visibility = View.VISIBLE
                binding.viewMessageBox2Chat.visibility = View.VISIBLE

            }

        }


    }
    //onCreate 바깥


    //보낸 사용자, 받은 사용자 확인 메소드
    private fun checkChatRoom() {

        chatRef.orderByChild("users/$myNumber").equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (item in snapshot.children) {
                        val chatModel = item.getValue<ChatModel>()
                        if (chatModel?.users!!.containsKey(receiver)) {
                            chatRoomKey = item.key.toString()

                            binding.rvChatListChat?.layoutManager =
                                GridLayoutManager(this@ChatActivity, 1)
                            binding.rvChatListChat?.adapter = ChatAdapter(this@ChatActivity,commentList,width,myNumber)
                        }
                    }
                }
            })

    }






    fun getMessageList() {
        chatRef.child(chatRoomKey.toString()).child("comments")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    commentList.clear()
                    for (data in snapshot.children) {
                        val item = data.getValue<ChatModel.Comment>()
                        commentList.add(item!!)
                        Log.d("TAG-commentList",commentList.toString())
                        Log.d("TAG-snapshot",data.toString())
                    }
                    var adapter = ChatAdapter(this@ChatActivity,commentList,width,myNumber)
                    adapter.notifyDataSetChanged()
                    //메세지를 보낼 시 화면을 맨 밑으로 내림
                    binding.rvChatListChat?.scrollToPosition(commentList.size - 1)
                }
            })
    }










}






