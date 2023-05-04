package com.example.angkorchatproto.Chat

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.angkorchatproto.Chat.adapter.ChatAdapter
import com.example.angkorchatproto.Chat.adapter.ChatImogeAdapter
import com.example.angkorchatproto.Chat.adapter.ChatImogeShortcutAdapter
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityChatBinding
import com.example.angkorchatproto.utils.FBdataBase
import com.example.angkorchatproto.utils.Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import java.time.LocalDateTime


class ChatActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding
    lateinit var myNumber: String
    var receiver: String = ""
    private var chatRoomKey: String? = null
    var chatRoomKeyList = ArrayList<String>()


    var width = 0
    var client = OkHttpClient()
    var chatRef = FBdataBase.getChatRef()
    var commentList = ArrayList<ChatModel.Comment>()
    var selectCharacterName: String? = null
    var selectCharacterIdx: String? = null


    @RequiresApi(Build.VERSION_CODES.O)
    var nowTime = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //현재 사용자 번호 불러오기
        val shared = getSharedPreferences("loginNumber", 0)
        myNumber = shared.getString("userNumber", "").toString()

        //키보드 상태 캐치하는 리스너
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
                if (keyboardHeight == 0 && rootHeight - heightExceptKeyboard - Utils.getStatusBarHeight(
                        this
                    ) - Utils.getNavigationBarHeight(this) != 0) {
                    keyboardHeight = rootHeight - heightExceptKeyboard - Utils.getStatusBarHeight(
                        this
                    ) - Utils.getNavigationBarHeight(this)
                    setImogeLayoutHeight(keyboardHeight)
                }
                Log.d("키보드 높이", "$keyboardHeight")
            }
        }


        //포커스 컨트롤
        binding.layout.setOnClickListener {
            binding.etMessageChat.clearFocus()
            hideKeyboard()

            val etMessageText = binding.etMessageChat.text.toString()
            val textCheck = etMessageText.replace(" ", "")

            if (textCheck == "") {
                binding.viewMessageBox1Chat.visibility = View.VISIBLE
                binding.imgRecordChat.visibility = View.VISIBLE

                binding.imgSendMessageChat.visibility = View.INVISIBLE
                binding.viewMessageBox2Chat.visibility = View.INVISIBLE
            }

        }

        //파일, 이모지, 녹음, 메모 클릭 시 임의 Toast 출력
        binding.imgMenuChat.setOnClickListener {
            Toast.makeText(this@ChatActivity, "메뉴 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.imgMediaChat.setOnClickListener {
            Toast.makeText(this@ChatActivity, "미디어 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.imgImogeChat.setOnClickListener {
            lifecycleScope.launch {
                setImogeRecyclerView()
                if (binding.viewImogeLayout.visibility == View.GONE) { // 키보드가 올라와있는 상황에서 이모티콘 버튼 클릭
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
                    binding.etMessageChat.requestFocus()
                    showKeyboard()
                    delay(10)
                    binding.etMessageChat.clearFocus()
                    hideKeyboard()
                    binding.viewImogeLayout.visibility = View.VISIBLE
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                } else { // 이모티콘 컨테이너가 띄워져 있는 상태에서 사용자가 다시 이모티콘 아이콘을 클릭
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
                    binding.etMessageChat.requestFocus()
                    showKeyboard()
                    delay(10)
                    binding.viewImogeLayout.visibility = View.GONE
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                }
            }
        }

        binding.imgRecordChat.setOnClickListener {
            Toast.makeText(this@ChatActivity, "음성녹음 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.imgImogePreviewClose.setOnClickListener {
            initImogePreview()
            binding.imgImogePreview.setImageDrawable(null)
        }
        val chatName= intent.getStringExtra("chatName")

        if(chatName!="" && chatName!=null){
            receiver = chatName.toString()
            Log.d("TAG-가져온 번호",receiver)
        }else {
            //상대방 번호 저장
            val receiverData = intent.getStringExtra("number").toString()
            val receiverData2 = receiverData.replace("-", "")

            receiver = receiverData2.replace(" ", "")
            Log.d("TAG-친구창에서 이동 번호",receiver)

            //상대방 이름 출력
            val receiverName = intent.getStringExtra("name").toString()
            binding.tvNameChat.text = receiverName
        }




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
                //전송 시 시간 초기화
            nowTime = LocalDateTime.now().toString()
            if (nowTime == "") {
                nowTime = System.currentTimeMillis().toString()
            }


            if (textCheck != "" ||
                binding.imogePreview.visibility == View.VISIBLE && binding.imgImogePreview.drawable != null) {
                val chatModel = ChatModel()
                chatModel.users.put(myNumber, true)
                chatModel.users.put(receiver, true)

                val comment = ChatModel.Comment(
                    profileImg,
                    myNumber,
                    binding.etMessageChat.text.toString(),
                    nowTime,
                    false,
                    "",
                    "",
                    if (binding.imogePreview.visibility == View.VISIBLE && binding.imgImogePreview.drawable != null) {
                        "$selectCharacterName$$$selectCharacterIdx"
                    } else {
                        ""
                    }
                )

                initImogePreview()

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
                    if (chatRoomKey == null) { //기채팅방 없는 경우
                        binding.imgSendMessageChat.isEnabled = false
                        chatRef.push().setValue(chatModel).addOnSuccessListener {
                            //채팅방 생성
                            checkChatRoom()
                            //메세지 보내기
                            Handler().postDelayed({
                                chatRef.child(chatRoomKey.toString())
                                    .child("comments").push()
                                    .setValue(
                                        ChatModel.Comment(
                                            profileImg,
                                            myNumber,
                                            etMessageText,
                                            nowTime,
                                            false,
                                            "",
                                            chatRoomKey
                                        )
                                    )
                            }, 1000L)




                            binding.etMessageChat.text = null
                        }
                    } else { //기채팅방 있는 경우
                        chatRef.child(chatRoomKey.toString()).child("comments")
                            .push()
                            .setValue(
                                ChatModel.Comment(
                                    profileImg,
                                    myNumber,
                                    etMessageText,
                                    nowTime,
                                    false,
                                    "",
                                    chatRoomKey
                                )
                            )
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
        binding.etMessageChat.onFocusChangeListener = View.OnFocusChangeListener { _, gainFocus ->
            //포커스가 주어졌을 때
            if (gainFocus) {

                binding.viewMessageBox1Chat.visibility = View.INVISIBLE
                binding.imgRecordChat.visibility = View.INVISIBLE

                binding.imgSendMessageChat.visibility = View.VISIBLE
                binding.viewMessageBox2Chat.visibility = View.VISIBLE

                if (binding.viewImogeLayout.visibility == View.VISIBLE) {
                    binding.viewImogeLayout.visibility = View.GONE
                }

            } else {

                binding.etMessageChat.clearFocus()
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
                            chatRoomKeyList.add(item.key.toString())

                            getMessageList()

                            binding.rvChatListChat.layoutManager =
                                GridLayoutManager(this@ChatActivity, 1)
                            binding.rvChatListChat.adapter =
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

                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    commentList.clear()
                    for (data in snapshot.children) {
                        val item = data.getValue<ChatModel.Comment>()
                        commentList.add(item!!)

                    }
                    val adapter = ChatAdapter(this@ChatActivity, commentList, width, myNumber)
                    adapter.notifyDataSetChanged()
                    //메세지를 보낼 시 화면을 맨 밑으로 내림
                    binding.rvChatListChat.scrollToPosition(commentList.size - 1)
                }
            })
    }

    private fun showKeyboard() {
        imm.showSoftInput(binding.etMessageChat, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        imm.hideSoftInputFromWindow(binding.etMessageChat.windowToken, 0)
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
        //이모티콘
        val gridLayoutManager = GridLayoutManager(this@ChatActivity, 4)
        binding.recyclerviewImoge.layoutManager = gridLayoutManager
        val defaultArrayList = if (arrayId == 0) {
            arrayListOf()
        } else {
            Utils.typedArrayToArrayList(resources.obtainTypedArray(arrayId))
        }
        imogeAdapter = ChatImogeAdapter(
            this@ChatActivity,
            characterName,
            defaultArrayList,
            object : ChatImogeAdapter.OnChatImogeAdapterListener {
                override fun onItemClicked(item: Int?, itemIdx: Int?, characterName: String?) {
                    Log.d("이모지 선택", "리소스 아이디 : $item")
                    if (itemIdx != null) {
                        selectCharacterIdx = (itemIdx+1).toString()
                    }
                    selectCharacterName = characterName
                    binding.imogePreview.visibility = View.VISIBLE
                    Glide.with(this@ChatActivity)
                        .load(item)
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                        .into(binding.imgImogePreview)
                }

            })
        binding.recyclerviewImoge.adapter = imogeAdapter
    }

    private fun setImogeShortcutRecyclerView() {
        //이모티콘 숏컷
        imogeShortcutAdapter = ChatImogeShortcutAdapter(this@ChatActivity,
            arrayListOf(R.drawable.ic_star_fill_prime_24,R.drawable.ic_clock_line_gray_24,R.drawable.img_emoticon_shortcut_nunu_selected,R.drawable.img_emoticon_shortcut_gana_selected,R.drawable.img_emoticon_shortcut_haha_selected),
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

    private fun initImogePreview() {
        selectCharacterName = null
        selectCharacterIdx = null
        binding.imogePreview.visibility = View.GONE
    }
}







