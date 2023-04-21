package com.example.angkorchatproto.Chat

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.Chat.ChatVO.Companion.SENT_BY_ME
import com.example.angkorchatproto.databinding.ActivityChatbotBinding
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDateTime


class ChatBotActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatbotBinding
    var chatList = ArrayList<ChatBotVO>()
    var client = OkHttpClient()
    var arr = JSONArray()
    var baseAi = JSONObject()
    var userMsg = JSONObject()
    lateinit var adapter: ChatBotAdapter
    var chatBotRef = FBdataBase.getChatBotRef()
    lateinit var myNumber:String


    @RequiresApi(Build.VERSION_CODES.O)
    var nowTime = ""


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityChatbotBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvNameChatBot.text = intent.getStringExtra("name")

        binding.imgMoveBackChatBot.setOnClickListener {
            finish()
        }

        //객체 초기화
        client = OkHttpClient()
        binding.tvNameChatBot.text="유니온 모바일"

        //현재 사용자 번호 불러오기
        val shared = getSharedPreferences("loginNumber", 0)
        myNumber = shared.getString("userNumber","").toString()

        Log.d("TAG-사용자 번호 출력", myNumber.toString())


        //디스플레이 가로값 구해 넘겨주기(말풍선 크기 제한)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)
        val width = size.x


        //전송 버튼 클릭 시
        binding.imgSendMessageChatBot.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(view: View?) {


                //전송 시 시간 초기화
                nowTime = LocalDateTime.now().toString()

                //API에 질문 전달
                val question = binding.etMessageChatBot.getText().toString().trim { it <= ' ' }
                addToChat(question, SENT_BY_ME, nowTime, chatList.size)

                //EditEext창 초기화
                this@ChatBotActivity.binding.etMessageChatBot.setText("")
                callAPI(question)

            }
        })

        //EditText에서 Enter 입력 시 전송
        binding.etMessageChatBot.setOnKeyListener() { v, keyCode, event ->
            var handled = false

            if (event.action == KeyEvent.ACTION_UP && keyCode == KEYCODE_ENTER) {

                //전송 시 시간 초기화
                nowTime = LocalDateTime.now().toString()

                //전송 버튼 클릭효과
                binding.imgSendMessageChatBot.performClick()
                handled = true
            }
            handled
        }


        //EditText Focus 감지
        binding.etMessageChatBot.onFocusChangeListener = OnFocusChangeListener { v, gainFocus ->
            //포커스가 주어졌을 때
            if (gainFocus) {
                binding.viewMessageBox1ChatBot.visibility = View.INVISIBLE
                binding.imgRecordChatBot.visibility = View.GONE

                binding.imgSendMessageChatBot.visibility = View.VISIBLE
                binding.viewMessageBox2ChatBot.visibility = View.VISIBLE

            }

        }


        //ChatBot Adapter 적용
        adapter = ChatBotAdapter(this@ChatBotActivity, chatList, width, nowTime)
        binding.rvChatListChatBot.setAdapter(adapter)

        //Recycler View 레이아웃 매니저 적용
        binding.rvChatListChatBot.setHasFixedSize(true)
        val manager = GridLayoutManager(this, 1)
        binding.rvChatListChatBot.setLayoutManager(manager)


        //FireBase 데이터 불러와서 chatList에 저장하기
        chatBotRef.child(myNumber).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue<ChatBotVO>() as ChatBotVO


                chatList.add(chatItem)

                adapter.notifyDataSetChanged()

                binding.rvChatListChatBot.scrollToPosition(chatList.size - 1)



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
    //OnCreate 바깥


    //메세지 전송 시 chatList에 쌓기
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged")
    fun addToChat(message: String?, sentBy: String?, time: String, listSize: Int) {
        runOnUiThread {
            adapter.notifyDataSetChanged()
            binding.rvChatListChatBot.smoothScrollToPosition(listSize)

            //GPT로 보낸 채팅 FB에 저장
            chatBotRef.child(myNumber).push().setValue(ChatBotVO(message, sentBy, time))


        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun addResponse(response: String?) {
        nowTime = LocalDateTime.now().toString()
        addToChat(response, ChatBotVO.SENT_BY_BOT, nowTime, chatList.size)


    }

    fun callAPI(question: String?) {
        //okhttp
        val `object` = JSONObject()
        try {
            //AI 속성설정
            baseAi.put("role", "user")
            baseAi.put("content", "You are a helpful and kind AI Assistant.")
            //유저 메세지
            userMsg.put("role", "user")
            userMsg.put("content", question)
            //array로 담아서 한번에 보낸다
            arr.put(baseAi)
            arr.put(userMsg)

        } catch (e: JSONException) {
            throw RuntimeException(e)
        }
        try {
            //모델 지정
            `object`.put("model", "gpt-3.5-turbo")
            `object`.put("messages", arr)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val body: RequestBody = RequestBody.Companion.create(JSON, `object`.toString())
        val request: Request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")  //url 경로 수정됨
            .header("Authorization", "Bearer " + MY_SECRET_KEY) //API Key 입력
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onFailure(call: Call, e: IOException) {
                //error 메세지 출력
                addResponse("Failed to load response due to " + e.message)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    var jsonObject: JSONObject? = null
                    try {
                        jsonObject = JSONObject(response.body!!.string())
                        val jsonArray = jsonObject.getJSONArray("choices")
                        val result = jsonArray.getJSONObject(0).getJSONObject("message")
                            .getString("content");
                        addResponse(result.trim { it <= ' ' })
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else {
                    addResponse("Failed to load response due to " + response.body!!.string())
                }
            }
        })
    }

    companion object {
        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()

        //API Key
        private const val MY_SECRET_KEY = "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"
    }


}
