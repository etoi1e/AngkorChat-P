package com.example.angkorchatproto.chat

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.angkorchatproto.chat.adapter.ChatImogeAdapter
import com.example.angkorchatproto.chat.adapter.ChatImogeShortcutAdapter
import com.example.angkorchatproto.R
import com.example.angkorchatproto.chat.ChatBotVO.Companion.SENT_BY_ME
import com.example.angkorchatproto.databinding.ActivityChatbotBinding
import com.example.angkorchatproto.utils.FBdataBase
import com.example.angkorchatproto.utils.Utils.getNavigationBarHeight
import com.example.angkorchatproto.utils.Utils.getStatusBarHeight
import com.example.angkorchatproto.utils.Utils.typedArrayToArrayList
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
    lateinit var adapter: ChatBotAdapter
    lateinit var imogeAdapter: ChatImogeAdapter
    lateinit var imogeShortcutAdapter: ChatImogeShortcutAdapter
    private lateinit var myNumber: String
    private lateinit var imm: InputMethodManager

    var chatList = ArrayList<ChatBotVO>()
    var selectCharacterName: String? = null
    var selectCharacterIdx: Int? = null
    private var client = OkHttpClient()
    private var arr = JSONArray()
    private var baseAi = JSONObject()
    private var userMsg = JSONObject()
    private var chatBotRef = FBdataBase.getChatBotRef()
    private var keyboardHeight: Int = 0
    private var rootHeight = -1


    @RequiresApi(Build.VERSION_CODES.O)
    var nowTime = ""


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
//            Log.d("키보드 높이", "rootHeight : $rootHeight")
            if (rootHeight == -1) {
                rootHeight = window.decorView.height
            }

            val visibleFrameSize = Rect()
            window.decorView.getWindowVisibleDisplayFrame(visibleFrameSize)
            val heightExceptKeyboard = visibleFrameSize.bottom - visibleFrameSize.top
            // 키보드를 제외한 높이가 디바이스 root_view보다 높거나 같다면, 키보드가 올라왔을 때가 아니므로 거른다.
            if (heightExceptKeyboard < rootHeight) {
                if (keyboardHeight == 0 && rootHeight - heightExceptKeyboard - getStatusBarHeight(
                        this
                    ) - getNavigationBarHeight(this) != 0
                ) {
                    keyboardHeight =
                        rootHeight - heightExceptKeyboard - getStatusBarHeight(this) - getNavigationBarHeight(
                            this
                        )
                    setImogeLayoutHeight(keyboardHeight)
                }
                Log.d("키보드 높이", "$keyboardHeight")
            }
        }
        //포커스 컨트롤
        binding.layoutBot.setOnClickListener {
            binding.etMessageChatBot.clearFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.etMessageChatBot.windowToken, 0)

            val etMessageText = binding.etMessageChatBot.text.toString()
            val textCheck = etMessageText.replace(" ", "")

            if (textCheck == "") {
                binding.viewMessageBox1ChatBot.visibility = View.VISIBLE
                binding.imgRecordChatBot.visibility = View.VISIBLE

                binding.imgSendMessageChatBot.visibility = View.INVISIBLE
                binding.viewMessageBox2ChatBot.visibility = View.INVISIBLE
            }

        }

        //파일, 이모지, 녹음, 메모 클릭 시 임의 Toast 출력
        binding.imgMenuChatBot.setOnClickListener {
            Toast.makeText(this@ChatBotActivity, "챗봇에서 지원하지 않는 기능입니다", Toast.LENGTH_SHORT).show()
        }

        binding.imgMediaChatBot.setOnClickListener {
            Toast.makeText(this@ChatBotActivity, "챗봇에서 지원하지 않는 기능입니다", Toast.LENGTH_SHORT).show()
        }

        binding.imgImogeChatBot.setOnClickListener {
            Toast.makeText(this@ChatBotActivity, "챗봇에서 지원하지 않는 기능입니다", Toast.LENGTH_SHORT).show()
//            lifecycleScope.launch {
//                setImogeRecyclerView()
//                if (binding.viewImogeLayout.visibility == View.GONE) { // 키보드가 올라와있는 상황에서 이모티콘 버튼 클릭
//                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
//                    binding.etMessageChatBot.requestFocus()
//                    showKeyboard()
//                    delay(10)
//                    binding.etMessageChatBot.clearFocus()
//                    hideKeyboard()
//                    binding.viewImogeLayout.visibility = View.VISIBLE
//                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
//                } else { // 이모티콘 컨테이너가 띄워져 있는 상태에서 사용자가 다시 이모티콘 아이콘을 클릭
//                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
//                    binding.etMessageChatBot.requestFocus()
//                    showKeyboard()
//                    delay(10)
//                    binding.viewImogeLayout.visibility = View.GONE
//                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
//                }
//            }
        }

        binding.imgImogePreviewClose.setOnClickListener {
            binding.imogePreview.visibility = View.GONE
            binding.imgImogePreview.setImageDrawable(null)
        }

        binding.imgRecordChatBot.setOnClickListener {
            Toast.makeText(this@ChatBotActivity, "챗봇에서 지원하지 않는 기능입니다", Toast.LENGTH_SHORT).show()
        }

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

        //프로필 사진 적용
        binding.imgProfileChatBot.setImageResource(R.drawable.top_logo)


        //전송 버튼 클릭 시
        binding.imgSendMessageChatBot.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(view: View?) {
                //공백확인
                val etMessageText = binding.etMessageChatBot.text.toString()
                val textCheck = etMessageText.replace(" ","")

                if(textCheck == ""){

                }else{
                    //전송 시 시간 초기화
                    nowTime = LocalDateTime.now().toString()

                    //API에 질문 전달
                    val question = binding.etMessageChatBot.getText().toString().trim { it <= ' ' }
                    addToChat(question, SENT_BY_ME, nowTime, chatList.size)

                    //EditEext창 초기화
                    this@ChatBotActivity.binding.etMessageChatBot.setText("")
                    callAPI(question)
                }


            }
        })

        //EditText에서 Enter 입력 시 전송
        binding.etMessageChatBot.setOnKeyListener() { v, keyCode, event ->
            var handled = false

            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {

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

                if (binding.viewImogeLayout.visibility == View.VISIBLE) {
                    binding.viewImogeLayout.visibility = View.GONE
                }
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
    private fun showKeyboard() {
        imm.showSoftInput(binding.etMessageChatBot, InputMethodManager.SHOW_IMPLICIT);
    }

    private fun hideKeyboard() {
        imm.hideSoftInputFromWindow(binding.etMessageChatBot.windowToken, 0);
    }

    private fun setImogeLayoutHeight(keyboardHeight: Int) {
        val params = binding.viewImogeLayout.layoutParams
        params.height = keyboardHeight // 변경할 높이
        binding.viewImogeLayout.layoutParams = params
    }

    private fun setImogeRecyclerView() {
        setImogeGridRecyclerView(0, "")
        setImogeShortcutRecyclerView()
    }

    private fun setImogeGridRecyclerView(arrayId: Int, characterName: String) {
        val gridLayoutManager = GridLayoutManager(this@ChatBotActivity, 4)
        binding.recyclerviewImoge.layoutManager = gridLayoutManager
        val defaultArrayList = if (arrayId == 0) {
            arrayListOf()
        } else {
            typedArrayToArrayList(resources.obtainTypedArray(arrayId))
        }
        imogeAdapter = ChatImogeAdapter(
            this@ChatBotActivity,
            characterName,
            defaultArrayList,
            object : ChatImogeAdapter.OnChatImogeAdapterListener {
                override fun onItemClicked(item: Int?, itemIdx: Int?, characterName: String?) {
                    Log.d("이모지 선택", "리소스 아이디 : $item")

                    selectCharacterName = characterName
                    selectCharacterIdx = itemIdx

                    binding.imogePreview.visibility = View.VISIBLE
                    Glide.with(this@ChatBotActivity)
                        .load(item)
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                        .into(binding.imgImogePreview)
                }

            })
        binding.recyclerviewImoge.adapter = imogeAdapter
    }

    private fun setImogeShortcutRecyclerView() {
        //이모티콘 숏컷
        imogeShortcutAdapter = ChatImogeShortcutAdapter(this@ChatBotActivity,
            arrayListOf(R.drawable.ic_star_fill_prime_24,R.drawable.ic_clock_line_gray_24,R.drawable.ic_clock_line_gray_24,R.drawable.ic_clock_line_gray_24,R.drawable.ic_clock_line_gray_24,R.drawable.ic_clock_line_gray_24),
            object: ChatImogeShortcutAdapter.OnChatImogeShortcutAdapterListener {
                override fun onItemClicked(item: Int) {
                    Log.d("이모지 쇼컷", "$item 번째 입니다.")
                    var arrayId = 0
                    var characterName = ""
                    when(item) {
                        2 -> {
                            arrayId = R.array.nunuImoge
                            characterName = "nunu"
                        }
                        3 -> {
                            arrayId = R.array.ganaImoge
                            characterName = "gana"
                        }
                        4 -> {
                            arrayId = R.array.hahaImoge
                            characterName = "haha"
                        }
                    }
                    setImogeGridRecyclerView(arrayId, characterName)
                }
            }
        )
        binding.recyclerviewImogeShortcut.adapter = imogeShortcutAdapter
    }
}
