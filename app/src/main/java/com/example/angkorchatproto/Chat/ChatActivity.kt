package com.example.angkorchatproto.Chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.angkorchatproto.databinding.ActivityChatBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class ChatActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding
    lateinit var chatList : ArrayList<ChatVO>
    lateinit var client: OkHttpClient
    lateinit var arr: JSONArray
    lateinit var baseAi: JSONObject
    lateinit var userMsg: JSONObject
    lateinit var adapter : ChatBotAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvNameChat.text = intent.getStringExtra("name")

        binding.imgMoveBackChat.setOnClickListener {
            finish()
        }

        //객체 초기화
        client = OkHttpClient()
        arr = JSONArray()


        //현재 사용자 번호 불러오기
        val shared = getSharedPreferences("loginNumber", 0)
        val myNumber = shared.contains("loginNumber")

        Log.d("TAG-사용자 번호 출력", myNumber.toString())

        chatList = arrayListOf<ChatVO>()

        binding.chatLayout.isFocusableInTouchMode = true

        adapter = ChatBotAdapter(this@ChatActivity,chatList)

        binding.rvChatListChat.setAdapter(adapter)

        //Recycler View 레이아웃 매니저 적용
        //binding.rvChatListChat.setHasFixedSize(true)
        val manager = GridLayoutManager(this,1)
        //manager.stackFromEnd = true
        binding.rvChatListChat.setLayoutManager(manager)


        binding.imgRecordChat.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                val question = this@ChatActivity.binding.etMessageChat.getText().toString().trim { it <= ' ' }
                addToChat(question, ChatVO.SENT_BY_ME)
                this@ChatActivity.binding.etMessageChat.setText("")
                callAPI(question)
                //this@ChatActivity.tv_welcome.setVisibility(View.GONE)
            }
        })


    }

    @SuppressLint("NotifyDataSetChanged")
    fun addToChat(message: String?, sentBy: String?) {
        runOnUiThread {
            chatList.add(ChatVO(message, sentBy))
            adapter.notifyDataSetChanged()
            binding.rvChatListChat.smoothScrollToPosition(adapter.itemCount)
        }
    }

    fun addResponse(response: String?) {
        chatList.removeAt(chatList.size - 1)
        addToChat(response, ChatVO.SENT_BY_BOT)
    }

    fun callAPI(question: String?) {
        //okhttp
        chatList.add(ChatVO("...", ChatVO.SENT_BY_BOT))
        val `object` = JSONObject()
        arr = JSONArray()
        baseAi = JSONObject()
        userMsg = JSONObject()
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
//            `object`.put("model", "text-davinci-003")
//            `object`.put("prompt", question)
//            `object`.put("max_tokens", 4000)
//            `object`.put("temperature", 0)
            `object`.put("model", "gpt-3.5-turbo")
            `object`.put("messages", arr)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val body: RequestBody = RequestBody.Companion.create(JSON, `object`.toString())
        val request: Request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")  //url 경로 수정됨
            .header("Authorization", "Bearer "+MY_SECRET_KEY)
            .post(body)
            .build();
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                addResponse("Failed to load response due to " + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    var jsonObject: JSONObject? = null
                    try {
                        jsonObject = JSONObject(response.body!!.string())
                        val jsonArray = jsonObject.getJSONArray("choices")
                        val result = jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
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
        private const val MY_SECRET_KEY = "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"
    }



}