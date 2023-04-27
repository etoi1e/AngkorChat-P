package com.example.angkorchatproto.Chat

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.angkorchatproto.R
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
        
        //포커스 컨트롤
        binding.layout.setOnClickListener {
            binding.etMessageChat.clearFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.etMessageChat.windowToken, 0)

            binding.viewMessageBox1Chat.visibility = View.VISIBLE
            binding.imgRecordChat.visibility = View.VISIBLE

            binding.imgSendMessageChat.visibility = View.GONE
            binding.viewMessageBox2Chat.visibility = View.GONE
        }
        

        //상대방 번호 저장
        val receiverData = intent.getStringExtra("number").toString()
        val receiverData2 = receiverData.replace("-", "")

        receiver = receiverData2.replace(" ", "")

        //상대방 이름 출력
        val receiverName = intent.getStringExtra("name").toString()
        binding.tvNameChat.text = receiverName

        //상대방 프로필 출력
        val profileImg = intent.getStringExtra("profile")

        if (profileImg == "") {
            Glide.with(this@ChatActivity)
                .load(R.drawable.ic_profile_default_72)
                .into(binding.imgProfileChat)
        } else {
            Glide.with(this@ChatActivity)
                .load(profileImg)
                .into(binding.imgProfileChat)
        }


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
            //공백확인
            val etMessageText = binding.etMessageChat.text.toString()
            val textCheck = etMessageText.replace(" ", "")
            if (textCheck == "") {

            } else {
                //전송 시 시간 초기화
                nowTime = LocalDateTime.now().toString()
                if (nowTime == "") {
                    nowTime = System.currentTimeMillis().toString()
                }


                if (binding.etMessageChat.text.toString() != "") {
                    val chatModel = ChatModel()
                    chatModel.users.put(myNumber, true)
                    chatModel.users.put(receiver, true)

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


        }

        checkChatRoom()

        Log.d("TAG-commentList", commentList.toString())


        //EditText에서 Enter 입력 시 전송
        binding.etMessageChat.setOnKeyListener() { v, keyCode, event ->
            var handled = false

            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

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
                binding.viewMessageBox1Chat.visibility = View.GONE
                binding.imgRecordChat.visibility = View.GONE

                binding.imgSendMessageChat.visibility = View.VISIBLE
                binding.viewMessageBox2Chat.visibility = View.VISIBLE

            } else {

                binding.etMessageChat.clearFocus()
            }

        }


    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

            val view = currentFocus // 현재 터치 위치
            if (view != null && (ev?.action == MotionEvent.ACTION_UP
                        || ev?.action == MotionEvent.ACTION_MOVE)
                && view is EditText
                && !view.javaClass.name.startsWith("android.webkit.")
            ) {
                // view 의 id 가 명시되어있지 않은 다른 부분을 터치 시
                val scrcoords = IntArray(2)
                view.getLocationOnScreen(scrcoords) // 0 은 x 마지막 터치 위치에서 x 값
                // 1은 y 마지막 터치 위치에서 y 값
                val x = ev.rawX + view.getLeft() - scrcoords[0]
                val y = ev.rawY + view.getTop() - scrcoords[1]
                if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) (this.getSystemService(
                    Context.INPUT_METHOD_SERVICE
                ) as InputMethodManager).hideSoftInputFromWindow(
                    this.window.decorView.applicationWindowToken, 0
                )
            val imm : InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)

        }
        Log.d("TAG-클릭","")
        return super.dispatchTouchEvent(ev)


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

                            getMessageList()

                            binding.rvChatListChat?.layoutManager =
                                GridLayoutManager(this@ChatActivity, 1)
                            binding.rvChatListChat?.adapter =
                                ChatAdapter(this@ChatActivity, commentList, width, myNumber)
                        }
                    }
                }
            })

    }


    fun getMessageList() {
        Log.d("TAG-chatRoomKey", chatRoomKey.toString())
        chatRef.child(chatRoomKey.toString()).child("comments")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    commentList.clear()
                    for (data in snapshot.children) {
                        val item = data.getValue<ChatModel.Comment>()
                        commentList.add(item!!)
                        //Log.d("TAG-commentList",commentList.toString())
                        //Log.d("TAG-snapshot",data.toString())
                    }
                    var adapter = ChatAdapter(this@ChatActivity, commentList, width, myNumber)
                    adapter.notifyDataSetChanged()
                    //메세지를 보낼 시 화면을 맨 밑으로 내림
                    binding.rvChatListChat?.scrollToPosition(commentList.size - 1)
                }
            })
    }



}







