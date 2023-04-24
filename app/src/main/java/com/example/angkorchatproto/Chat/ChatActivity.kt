package com.example.angkorchatproto.Chat

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.databinding.ActivityChatBinding
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue
import okhttp3.OkHttpClient
import java.time.LocalDateTime

class ChatActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding
    var chatListSend = ArrayList<ChatVO>()
    var chatListRec = ArrayList<ChatVO>()
    var client = OkHttpClient()
    lateinit var adapterSend: ChatAdapter
//    lateinit var adapterRec: ChatAdapter
    var chatRef = FBdataBase.getChatRef()
    lateinit var myNumber:String
    lateinit var receiver:String

    @RequiresApi(Build.VERSION_CODES.O)
    var nowTime = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //상대방 이름 설정
        val receiver_data = intent.getStringExtra("number").toString()
        val receiver_name = intent.getStringExtra("name").toString()

        val receiver_data2  = receiver_data.replace("-","")
        receiver  = receiver_data2.replace(" ","")

        binding.tvNameChat.text = receiver_name


        //뒤로가기
        binding.imgMoveBackChat.setOnClickListener {
            finish()
        }

        //객체 초기화
        client = OkHttpClient()


        //현재 사용자 번호 불러오기
        val shared = getSharedPreferences("loginNumber", 0)
        myNumber = shared.getString("userNumber","").toString()


        //디스플레이 가로값 구해 넘겨주기(말풍선 크기 제한)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)
        val width = size.x


        //전송 버튼 클릭 시
        binding.imgSendMessageChat.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(view: View?) {


                //전송 시 시간 초기화
                nowTime = LocalDateTime.now().toString()

                //채팅 Firebase에 전달
                val question = binding.etMessageChat.getText().toString().trim { it <= ' ' }
                addToChat(question, myNumber,  receiver, nowTime,chatListSend.size)

                //EditEext창 초기화
                this@ChatActivity.binding.etMessageChat.setText("")


            }
        })

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

        //Chat Adapter 적용 보낸 메일
        adapterSend = ChatAdapter(this@ChatActivity, chatListSend, width, nowTime,myNumber)
        binding.rvChatListChat.setAdapter(adapterSend)

//        //Chat Adapter 적용 받은 메일
//        adapterRec = ChatAdapter(this@ChatActivity, chatListRec, width, nowTime,receiver)
//        binding.rvChatListChat.setAdapter(adapterRec)

        //Recycler View 레이아웃 매니저 적용
        binding.rvChatListChat.setHasFixedSize(true)
        val manager = GridLayoutManager(this, 1)
        binding.rvChatListChat.setLayoutManager(manager)


        //FireBase 데이터 불러와서 chatList에 저장하기(받은 문자)
        chatRef.child(myNumber).child(receiver).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue<ChatVO>() as ChatVO


                chatListSend.add(chatItem)

                adapterSend.notifyDataSetChanged()

                binding.rvChatListChat.scrollToPosition(chatListSend.size - 1)

                Log.d("TAG-채팅기록",chatListSend.toString())



            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //FireBase 데이터 불러와서 chatList에 저장하기
        chatRef.child(receiver).child(myNumber).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val chatItem = snapshot.getValue<ChatVO>() as ChatVO


                chatListSend.add(chatItem)

                adapterSend.notifyDataSetChanged()

                binding.rvChatListChat.scrollToPosition(chatListSend.size - 1)



            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




    }

    //onCreate 바깥
    //메세지 전송 시 chatList에 쌓기
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged")
    fun addToChat(message: String?, sender: String?,receiver:String?, time: String, listSize: Int) {
        runOnUiThread {
            adapterSend.notifyDataSetChanged()
            //adapterRec.notifyDataSetChanged()
            binding.rvChatListChat.smoothScrollToPosition(listSize)
//            Log.d("TAG-저장path receiver",receiver.toString())
//            Log.d("TAG-저장path sender",sender.toString())

            //채팅 FB에 저장
            chatRef.child(sender.toString()).child(receiver.toString()).push().setValue(ChatVO(message,sender,receiver,time))


        }

    }
}