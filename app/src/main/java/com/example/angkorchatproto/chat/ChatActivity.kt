package com.example.angkorchatproto.chat

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.Intent.ACTION_OPEN_DOCUMENT
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.angkorchatproto.JoinVO
import com.example.angkorchatproto.R
import com.example.angkorchatproto.UserVO
import com.example.angkorchatproto.base.BaseActivity
import com.example.angkorchatproto.chat.adapter.ChatAdapter
import com.example.angkorchatproto.chat.adapter.ChatImogeAdapter
import com.example.angkorchatproto.chat.adapter.ChatImogeShortcutAdapter
import com.example.angkorchatproto.chat.adapter.MediaImgAdapter
import com.example.angkorchatproto.databinding.ActivityChatBinding
import com.example.angkorchatproto.utils.FBdataBase
import com.example.angkorchatproto.utils.Utils
import com.example.angkorchatproto.video.VideoActivity
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import java.io.File
import java.time.LocalDateTime
import java.util.*


class ChatActivity : BaseActivity() {

    lateinit var binding: ActivityChatBinding
    lateinit var myNumber: String
    var receiver: String = ""
    var receiverNumber: String = ""
    var receiverToken: String = ""
    private var chatRoomKey: String? = null
    var selectedDirectory = ""
    var sendFileDirectory = ""
    var chatRoomKeyList = ArrayList<String>()
    var imgList = ArrayList<Uri?>()
    var selectImgList = ArrayList<Uri?>()
    var commentKeyList = ArrayList<String>()
    var commentList = ArrayList<ChatModel.Comment>()

    private lateinit var imogeAdapter: ChatImogeAdapter
    private lateinit var imogeShortcutAdapter: ChatImogeShortcutAdapter
    lateinit var mediaImgAdapter: MediaImgAdapter
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var imm: InputMethodManager
    private var keyboardHeight: Int = 0
    private var rootHeight = -1

    var width = 0
    private var client = OkHttpClient()
    private var chatRef = FBdataBase.getChatRef()
    var selectCharacterName: String? = null
    var selectCharacterIdx: String? = null


    //Manifest 에서 설정한 권한을 가지고 온다.
    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val STORAGE_PERMISSION = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_VIDEO

    )

    //권한 플래그값 정의
    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99

    //카메라와 갤러리를 호출하는 플래그
    val FLAG_REQ_CAMERA = 1001
    val FLAG_REQ_OPEN_DIRECTORY = 1002

    var photoUri = ""

    var replyKey = ""

    var sendProfile: UserVO? = null

    var location: String? = ""


    @RequiresApi(Build.VERSION_CODES.O)
    var nowTime = ""


    //프로필 전송
    //쌍방향 데이터 전달
    val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {

            sendProfile = UserVO(
                result.data?.getStringExtra("name")!!,
                result.data?.getStringExtra("email")!!,
                result.data?.getStringExtra("profile")!!,
                result.data?.getStringExtra("phone")!!
            )


            binding.imgSendMessageChat.performClick()


        }

        if(result.resultCode == 1994){
            //location 정보 불러오기
            val lat = result.data?.getDoubleExtra("latitude",0.0)!!
            val lng = result.data?.getDoubleExtra("longitude",0.0)!!
            val mapScreenshot = result.data?.getStringExtra("mapScreenshot")!!
            location = "$lat,$lng&$mapScreenshot"

            Log.d("TAG-위치정보",location.toString())

            binding.imgSendMessageChat.performClick()

            location=""
        }
    }


    @SuppressLint("CommitPrefEdits")
    override fun onResume() {
        super.onResume()

        checkChatRoom()

//        binding.rvChatListChat.scrollToPosition(commentList.lastIndex)

        binding.rvChatListChat.scrollToPosition(commentList.size-1)

        val sharedReply = getSharedPreferences("sharedReply", 0)
        val editorReply = sharedReply.edit()

        replyKey = sharedReply.getString("replyKey", "").toString()

        binding.replyLayout.visibility = View.GONE


        //답장 정보
        if (replyKey != "") {

            binding.replyLayout.visibility = View.VISIBLE


            chatRef.child("$chatRoomKey/comments").child(replyKey)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val message = snapshot.child("message").value.toString()
                        val location = snapshot.child("location").value.toString()
                        val file = snapshot.child("file").value.toString()
                        val url = snapshot.child("url").value.toString()
                        val userName = snapshot.child("sender").value.toString()



                        binding.tvReplySelectUser.text = userName

                        //주소에 답장
                        if (location != "") binding.tvReplyText.text = "location"

                        //메세지에 답장
                        if (message != "") binding.tvReplyText.text = message

                        //파일에 답장
                        if (file != "") {
                            binding.tvReplyText.text = "file"
                            binding.ivReplyImage.visibility = View.VISIBLE
                            binding.ivReplyImage.setImageResource(R.drawable.file_line_black)
                        }

                        //이미지에 답장
                        if (url != "") {

                            if (url.contains("content://media/")) {//카메라로 촬영한 사진
                                val storage = Firebase.storage
                                val storageRef = storage.getReference()
                                val imgRef = storageRef.child("/$url.png")


                                imgRef.downloadUrl.addOnSuccessListener { p0 ->
                                    binding.ivReplyImage.visibility = View.VISIBLE
                                    binding.tvReplyText.text = "Photo"
                                    Glide.with(this@ChatActivity)
                                        .load(p0)
                                        .into(binding.ivReplyImage)
                                }


                            } else {//갤러리에서 전송한 사진

                                val storage =
                                    FirebaseStorage.getInstance("gs://angkor-ae0c0.appspot.com")
                                val storageRef = storage.getReference()
                                val imgRef = storageRef.child("/$url")

                                imgRef.listAll()
                                    .addOnSuccessListener { p0 ->
                                        val selectedPhotoList = p0!!.items

                                        if (selectedPhotoList.size > 0) {
                                            selectedPhotoList.get(0).downloadUrl
                                                .addOnCompleteListener(
                                                    object : OnCompleteListener<Uri> {
                                                        override fun onComplete(p0: Task<Uri>) {
                                                            if (p0.isSuccessful) {
                                                                Glide.with(this@ChatActivity)
                                                                    .load(p0.getResult())
                                                                    .into(binding.ivReplyImage)
                                                            }
                                                        }

                                                    }
                                                )
                                        }
                                    }


                            }


                        }


                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

            editorReply.clear()
            editorReply.apply()
        }


        //Reply 닫기 버튼 클릭 시
        binding.ivReplyClose.setOnClickListener {
            binding.replyLayout.visibility = View.GONE

            replyKey = ""
        }


    }

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //현재 사용자 번호 불러오기
        val shared = getSharedPreferences("loginNumber", 0)
        myNumber = shared.getString("userNumber", "").toString()

        //상대방 번호 저장
        val receiverData = intent.getStringExtra("number").toString()
        val receiverData2 = receiverData.replace("-", "")

        receiver = receiverData2.replace(" ", "")

        val usersRef = FBdataBase.getUserRef().child(receiver)


        //상대방 이름 출력
        val receiverName = intent.getStringExtra("name").toString()
        binding.tvNameChat.text = receiverName

        var profileImg = intent.getStringExtra("profile")

        if (profileImg == null || profileImg == "") {
            Glide.with(this@ChatActivity)
                .load(R.drawable.ic_profile_default_72)
                .into(binding.imgProfileChat)
        } else {
            Glide.with(this@ChatActivity)
                .load(profileImg)
                .into(binding.imgProfileChat)
        }


        //상대방 번호
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value
                if (value is Map<*, *>) {
                    val number = value["number"] as? String
                    val password = value["password"] as? String
                    val token = value["token"] as? String

                    if (number != null && password != null && token != null) {
                        val user = JoinVO(number, password, token)
                        receiverNumber = user.number!!
                        receiverToken = user.token!!
                    }
                }


                //상대방 번호로 프로필 사진 출력
                if (profileImg == null) {

                    val friendRef = FBdataBase.getFriendRef()

                    friendRef.child(myNumber).child(receiverNumber).child("profile")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                profileImg = snapshot.getValue().toString()

                                if (profileImg != "null") {
                                    Glide.with(this@ChatActivity)
                                        .load(profileImg)
                                        .into(binding.imgProfileChat)
                                } else {
                                    Glide.with(this@ChatActivity)
                                        .load(R.drawable.ic_profile_default_72)
                                        .into(binding.imgProfileChat)
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })


                }


            }

            override fun onCancelled(error: DatabaseError) {
                // 에러 처리
            }
        })


        //키보드 상태 캐치하는 리스너
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
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
                    ) - Utils.getNavigationBarHeight(this) != 0
                ) {
                    keyboardHeight =
                        rootHeight - heightExceptKeyboard - Utils.getStatusBarHeight(
                            this
                        ) - Utils.getNavigationBarHeight(this)
                    setImogeLayoutHeight(keyboardHeight)
                    setMediaLayoutHeight(keyboardHeight)
                }
                Log.d("키보드 높이", "$keyboardHeight")
            }
        }

        //포커스 컨트롤
        binding.layout.setOnClickListener {

            //배경 클릭 시 포커스 삭제
            binding.etMessageChat.clearFocus()

            hideKeyboard()

            binding.mediaLayout.visibility = View.GONE
            binding.mediaMenuLayout.visibility = View.GONE
            binding.viewImogeLayout.visibility = View.GONE
            binding.replyLayout.visibility = View.GONE

            binding.imgMediaChat.setImageResource(R.drawable.ic_clip_line_gray_24)

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


        //미디어 클립 클릭 시 메뉴 펼치기
        binding.imgMediaChat.setOnClickListener {
            lifecycleScope.launch {

                if (binding.mediaLayout.visibility == View.GONE) { //미디어 메뉴 열기

                    binding.imgMediaChat.setImageResource(R.drawable.ic_clip_line_gray_24)

                    binding.etMessageChat.requestFocus()

                    if (keyboardHeight == 0) {
                        showKeyboard()
                    }

                    delay(10)
                    binding.etMessageChat.clearFocus()
                    hideKeyboard()

                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

                    binding.mediaLayout.visibility = View.VISIBLE
                    binding.mediaMenuLayout.visibility = View.VISIBLE

                    binding.mediaMediaLayout.visibility = View.GONE
                    binding.viewImogeLayout.visibility = View.GONE

                    binding.imgMediaChat.setImageResource(R.drawable.ic_close_line_gray_24)

                    binding.viewBottomBoxChat
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

                    //미디어 메뉴 내 Media 클릭 시
                    binding.imgMediaChatMedia.setOnClickListener {

                        binding.mediaMenuLayout.visibility = View.INVISIBLE
                        binding.mediaMediaLayout.visibility = View.VISIBLE

                        getImage()

                        binding.viewMessageBox2Chat.visibility = View.GONE
                        binding.etMessageChat.visibility = View.GONE
                        binding.imgRecordChat.visibility = View.GONE
                        binding.imgImogeChat.visibility = View.GONE
                        binding.imgSendMessageChat.visibility = View.GONE

                        binding.btnChatSendMedia.visibility = View.VISIBLE

                    }

                    //미디어 메뉴 내 Camera 클릭 시
                    binding.imgCameraChatMedia.setOnClickListener {

                        openCamera()

                        binding.btnSendPhotoPreview.setOnClickListener {
                            //전송 버튼 클릭효과
                            binding.imgSendMessageChat.performClick()
                            binding.photoPreview.visibility = View.GONE

                        }


                    }

                    //미디어 메뉴 내 Profile 클릭 시
                    with(binding) {
                        binding.imgProfileChatMedia.setOnClickListener {
                            val intent = Intent(this@ChatActivity, SelectUserActivity::class.java)
                            intent.putExtra("sendProfile", "true")
                            startForResult.launch(intent)
                        }


                    }


                    //프로토 타입 내 지원하지 않는 기능 Toast만 출력
                    //미디어 메뉴 내 File 클릭 시
                    binding.imgFileChatMedia.setOnClickListener {

                        val intent = Intent(ACTION_OPEN_DOCUMENT)

                        intent.addCategory(Intent.CATEGORY_OPENABLE)
                        intent.type = "*/*"

                        startActivityForResult(intent, FLAG_REQ_OPEN_DIRECTORY)


                    }


                    //미디어 메뉴 내 Location 클릭 시
                    binding.imgLocationChatMedia.setOnClickListener {

                        val intent = Intent(this@ChatActivity,MapsActivity::class.java)

                        startForResult.launch(intent)




                    }

                    //미디어 메뉴 내 Capture 클릭 시
                    binding.imgCaptureChatMedia.setOnClickListener {
                        Toast.makeText(
                            this@ChatActivity,
                            "This feature is not supported by Prototype",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                } else { //미디어 메뉴 닫기
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
                    binding.etMessageChat.requestFocus()

                    binding.imgMediaChat.setImageResource(R.drawable.ic_clip_line_gray_24)

                    binding.viewMessageBox2Chat.visibility = View.VISIBLE
                    binding.etMessageChat.visibility = View.VISIBLE
                    binding.imgImogeChat.visibility = View.VISIBLE
                    binding.imgSendMessageChat.visibility = View.VISIBLE

                    binding.btnChatSendMedia.visibility = View.GONE
                    binding.mediaLayout.visibility = View.GONE
                    binding.mediaMenuLayout.visibility = View.GONE
                    binding.viewImogeLayout.visibility = View.GONE




                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                }

            }
        }


        binding.imgImogeChat.setOnClickListener {
            lifecycleScope.launch {
                setImogeRecyclerView()
                if (binding.viewImogeLayout.visibility == View.GONE) { // 키보드가 올라와있는 상황에서 이모티콘 버튼 클릭
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
                    binding.etMessageChat.requestFocus()
                    if (keyboardHeight == 0) {
                        showKeyboard()
                    }
                    delay(10)
                    binding.etMessageChat.clearFocus()
                    hideKeyboard()
                    binding.viewImogeLayout.visibility = View.VISIBLE

                    //미디어 창이 꺼지면 X->클립으로 변경
                    binding.mediaLayout.visibility = View.GONE
                    binding.imgMediaChat.setImageResource(R.drawable.ic_clip_line_gray_24)

                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                } else { // 이모티콘 컨테이너가 띄워져 있는 상태에서 사용자가 다시 이모티콘 아이콘을 클릭
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
                    binding.etMessageChat.requestFocus()
                    if (keyboardHeight == 0) {
                        showKeyboard()
                    }
                    delay(10)
                    binding.viewImogeLayout.visibility = View.GONE

                    //미디어 창이 꺼지면 X->클립으로 변경
                    binding.mediaLayout.visibility = View.GONE
                    binding.imgMediaChat.setImageResource(R.drawable.ic_clip_line_gray_24)

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

            //입력한 text가 공백이 아닌 경우 전송
            if (textCheck != "" ||
                binding.imogePreview.visibility == View.VISIBLE && binding.imgImogePreview.drawable != null
                || photoUri != "" || selectImgList.size != 0 || sendFileDirectory != "" || sendProfile != null || location != ""
            ) {
                val chatModel = ChatModel()
                chatModel.users.put(myNumber, true)
                chatModel.users.put(receiver, true)

                var photos = ""

                if (selectImgList.size != 0) {
                    photos = selectedDirectory
                }

                if (selectedDirectory == "") {
                    photos = photoUri
                }


                val comment = ChatModel.Comment(
                    profile = profileImg,
                    sender = myNumber,
                    message = binding.etMessageChat.text.toString(),
                    time = nowTime,
                    state = false,
                    url = photos,
                    key = chatRoomKey,
                    emo = if (binding.imogePreview.visibility == View.VISIBLE && binding.imgImogePreview.drawable != null) {
                        "$selectCharacterName$$$selectCharacterIdx"
                    } else {
                        ""
                    },
                    file = if (sendFileDirectory != "") {
                        sendFileDirectory
                    } else {
                        ""
                    }, reaction = null,
                    reply = if (binding.replyLayout.visibility == View.VISIBLE) {
                        replyKey
                    } else {
                        ""
                    },
                    sendProfile = sendProfile,
                    location = location
                )

                initImogePreview()

                if (chatRoomKey == null) {
                    binding.imgSendMessageChat.isEnabled = false
                    val newChat = chatRef.push()
                    if (comment.key == "" || comment.key == null) {
                        comment.key = newChat.key
                    }
                    newChat.setValue(chatModel).addOnSuccessListener {
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

                    chatRef.child(chatRoomKey.toString())
                        .child("comments").push().setValue(comment)


                    binding.etMessageChat.text = null


                }

            }
            replyKey = ""
            photoUri = ""
            binding.replyLayout.visibility = View.GONE
        }

        checkChatRoom()


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
        binding.etMessageChat.onFocusChangeListener =
            View.OnFocusChangeListener { _, gainFocus ->
                //포커스가 주어졌을 때
                if (gainFocus) {

                    binding.viewMessageBox1Chat.visibility = View.INVISIBLE
                    binding.imgRecordChat.visibility = View.INVISIBLE

                    binding.imgSendMessageChat.visibility = View.VISIBLE
                    binding.viewMessageBox2Chat.visibility = View.VISIBLE

                    if (binding.viewImogeLayout.visibility == View.VISIBLE) {
                        binding.viewImogeLayout.visibility = View.GONE
                    }

                    if (binding.mediaLayout.visibility == View.VISIBLE) {
                        binding.mediaLayout.visibility = View.GONE
                        binding.imgMediaChat.setImageResource(R.drawable.ic_clip_line_gray_24)
                    }

                } else {
                    binding.etMessageChat.clearFocus()
                }

            }

        //촬영 후 이미지 전송 버튼 클릭효과
        binding.btnSendPhotoPreview.setOnClickListener {

            binding.imgSendMessageChat.performClick()
            binding.photoPreview.visibility = View.GONE
            photoUri = ""

        }

        //미디어 내 send 버튼 클릭 시
        binding.btnChatSendMedia.setOnClickListener {

            selectedDirectory = "$myNumber${LocalDateTime.now()}"

            val storage = Firebase.storage
            val storageRef = storage.reference
            val imagesRef = storageRef.child(selectedDirectory)
            var fileName = ""

            if (selectImgList.size != 0) {
                for (item in selectImgList) {
                    for (i in 0 until selectImgList.size) {
                        // 업로드할 파일의 이름 설정
                        fileName = "$myNumber$i"

                        // 파일 업로드
                        val imageRef = imagesRef.child(fileName)
                        val file = Uri.fromFile(File(item.toString()))
                        val uploadTask = imageRef.putFile(file)

                        // 파일 업로드 상태 모니터링
                        uploadTask.addOnSuccessListener { taskSnapshot ->
                            // 업로드 성공 후 처리할 내용

                            //전송 버튼 클릭효과
                            binding.imgSendMessageChat.performClick()
                            selectedDirectory = ""
                            Log.d(
                                "TAG- FirebaseStorage",
                                "Upload Success: ${taskSnapshot.metadata?.path}"
                            )

                        }.addOnFailureListener { exception ->
                            // 업로드 실패 시 처리할 내용
                            selectedDirectory = ""
                            Log.e(" TAG- FirebaseStorage", "Upload Failed: $exception")
                        }
                    }

                }
            }

            //갤러리 파일 전송 후 레이아웃 복원

            binding.mediaMenuLayout.visibility = View.VISIBLE

            binding.viewMessageBox2Chat.visibility = View.VISIBLE
            binding.etMessageChat.visibility = View.VISIBLE
            binding.imgImogeChat.visibility = View.VISIBLE
            binding.imgSendMessageChat.visibility = View.VISIBLE

            binding.btnChatSendMedia.visibility = View.GONE
            binding.mediaMediaLayout.visibility = View.GONE

        }

        binding.imgVideoChat.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("mode", "send")
            intent.putExtra("phoneNumber", receiverNumber)
            intent.putExtra("token", receiverToken)
            startActivity(intent)
        }

        //전화 걸기
        binding.imgVoiceChat.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$receiver"))
            startActivity(intent)
            finish()
        }

        //햄버거 메뉴 클릭 시

        binding.imgMenuChat.setOnClickListener {
            val intent = Intent(this@ChatActivity, ChatMoreActivity::class.java)

            intent.putExtra("userName", receiverName)
            intent.putExtra("userNumber", receiver)
            intent.putExtra("profile", profileImg)

            startActivity(intent)
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

                    val chatModel = ChatModel()
                    chatModel.users.put(myNumber, true)
                    chatModel.users.put(receiver, true)

                    for (item in snapshot.children) {

                        val chatModel = item.getValue<ChatModel>()
                        if (chatModel?.users!!.containsKey(receiver)) {

                            chatRoomKeyList.clear()

                            chatRoomKey = item.key.toString()
                            chatRoomKeyList.add(item.key.toString())



                            getMessageList(chatRoomKey!!)

                            chatAdapter =
                                ChatAdapter(
                                    this@ChatActivity,
                                    commentList,
                                    commentKeyList,
                                    width,
                                    myNumber
                                )


                            binding.rvChatListChat.layoutManager =
                                GridLayoutManager(this@ChatActivity, 1)
                            binding.rvChatListChat.adapter = chatAdapter

                        }
                    }
                }
            })
    }

    fun getMessageList(chatKey: String) {
        chatRef.child(chatKey).child("comments")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG-error", error.toString())
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    commentList.clear()
                    commentKeyList.clear()
                    for (data in snapshot.children) {
                        val item = data.getValue<ChatModel.Comment>()
                        commentList.add(item!!)
                        commentKeyList.add(data.key.toString())
                    }
                    chatAdapter =
                        ChatAdapter(
                            this@ChatActivity,
                            commentList,
                            commentKeyList,
                            width,
                            myNumber
                        )
                    chatAdapter.notifyDataSetChanged()
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

    private fun setMediaLayoutHeight(keyboardHeight: Int) {
        val params = binding.mediaLayout.layoutParams
        params.height = keyboardHeight // 변경할 높이
        binding.mediaLayout.layoutParams = params
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
                        selectCharacterIdx = (itemIdx + 1).toString()
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
            arrayListOf(
                R.drawable.ic_star_fill_prime_24,
                R.drawable.ic_clock_line_gray_24,
                R.drawable.img_emoticon_shortcut_nunu_selected,
                R.drawable.img_emoticon_shortcut_gana_selected,
                R.drawable.img_emoticon_shortcut_haha_selected
            ),
            object : ChatImogeShortcutAdapter.OnChatImogeShortcutAdapterListener {
                override fun onItemClicked(item: Int) {
                    Log.d("이모지 쇼컷", "$item 번째 입니다.")
                    var arrayId = 0
                    var characterName = ""
                    when (item) {
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

    //카메라 권한 확인
    private fun openCamera() {
        //카메라 권한이 있는지 확인
        if (checkPermission(CAMERA_PERMISSION, FLAG_PERM_CAMERA)) {
            //권한이 있으면 카메라를 실행시킵니다.
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            startActivityForResult(intent, FLAG_REQ_CAMERA)
        } else {
            //권한이 없으면 권한 요청을 합니다.
            ActivityCompat.requestPermissions(this, CAMERA_PERMISSION, FLAG_PERM_CAMERA)
        }
    }

    //카메라 권한이 있는지 체크하는 메소드
    fun checkPermission(permissions: Array<out String>, flag: Int): Boolean {
        for (permission in permissions) {
            //만약 권한이 승인되어 있지 않다면 권한승인 요청을 사용에 화면에 호출합니다.
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, permissions, flag)
                return false
            }
        }
        return true
    }

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                FLAG_REQ_CAMERA -> {
                    if (data?.extras?.get("data") != null) {
                        binding.photoPreview.visibility = View.VISIBLE

                        val bitmap = data?.extras?.get("data") as Bitmap

                        // 갤러리에 이미지 저장
                        val filename = "${System.currentTimeMillis()}.png"
                        val contentResolver = applicationContext.contentResolver
                        val imageUri1 =
                            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                        val contentValues = ContentValues().apply {
                            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures")
                        }
                        val imageUri = contentResolver.insert(imageUri1, contentValues) as Uri
                        val outputStream = contentResolver.openOutputStream(imageUri)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        outputStream?.close()

                        binding.imgPhotoPhotoPreview.setImageBitmap(bitmap)

                        // 이미지 파일 경로 저장
                        photoUri = imageUri.toString() + myNumber + LocalDateTime.now()


                        // 카메라로 방금 촬영한 이미지를 파이어 베이스 스토리지에 전달
                        val storage = Firebase.storage
                        val storageRef = storage.reference
                        val imgRef = storageRef.child(photoUri + ".png")



                        binding.imgPhotoPhotoPreview.isDrawingCacheEnabled = true
                        binding.imgPhotoPhotoPreview.buildDrawingCache()

                        val baos = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                        val data = baos.toByteArray()

                        //imgRef : 스토리지 경로 지정하는 키워드.
                        var uploadTask = imgRef.putBytes(data)
                        uploadTask.addOnFailureListener {
                            // Handle unsuccessful uploads
                        }.addOnSuccessListener { taskSnapshot ->
                            // Upload succeeded, get the download URL
                            imgRef.downloadUrl.addOnSuccessListener { uri ->

                            }
                        }


                    }
                }
                FLAG_REQ_OPEN_DIRECTORY -> {
                    //작업중

                    val fileUri = data!!.getData();

                    selectedDirectory = "$myNumber${LocalDateTime.now()}"

                    // 파일 이름 디바이스 저장명으로 바꾸기
                    var result: String? = null
                    if (fileUri != null) {
                        if (fileUri.getScheme().equals("content")) {
                            val cursor = contentResolver.query(fileUri, null, null, null, null)
                            try {
                                if (cursor != null && cursor.moveToFirst()) {
                                    result =
                                        cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                                }
                            } finally {
                                cursor?.close()
                            }
                        }
                    }
                    if (result == null) {
                        if (fileUri != null) {
                            result = fileUri.getLastPathSegment()
                        }
                    }


                    // Firebase Storage에 파일 업로드
                    val fileRef =
                        Firebase.storage.getReference().child("$selectedDirectory/$result")
                    if (fileUri != null) {
                        fileRef.putFile(fileUri).addOnSuccessListener { //성공시
                            sendFileDirectory = "$fileRef"
                            selectedDirectory = ""
                            photoUri = ""
                            selectedDirectory = ""

                            binding.imgSendMessageChat.performClick()
                            sendFileDirectory = ""
                        }


                    }


                }
            }
        }


    }

    //갤러리에서 사진 호출하여 리스트로 출력
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("NotifyDataSetChanged")
    fun getImage() {

        //권한 확인

        if (checkPermission(STORAGE_PERMISSION, FLAG_PERM_STORAGE)) {

        } else {
            //권한이 없으면 권한 요청을 합니다.
            ActivityCompat.requestPermissions(this, STORAGE_PERMISSION, FLAG_PERM_STORAGE)
        }


        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_TAKEN,

            )
        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

        val query = applicationContext.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )
        query?.use { cursor ->
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            imgList.clear()
            while (cursor.moveToNext()) {
                val data = Uri.parse(cursor.getString(dataColumn))

                // 이미지 파일의 Uri 생성
                imgList.add(data)
                Log.d("TAG-imgList크기 확인1", imgList.size.toString())
                Log.d("TAG-data 확인1", data.toString())

            }

        }

        mediaImgAdapter = MediaImgAdapter(this@ChatActivity, imgList)
        Log.d("TAG-imgList크기 확인2", imgList.size.toString())
        binding.rvMediaImgList.adapter = mediaImgAdapter
        binding.rvMediaImgList.layoutManager =
            LinearLayoutManager(this@ChatActivity, RecyclerView.HORIZONTAL, false)


        mediaImgAdapter.setOnImageSelectListener(object :
            MediaImgAdapter.OnImageSelectListener {
            override fun onImageSelect(selectImg: ArrayList<Uri?>) {
                //선택한사진가져오기
                selectImgList = selectImg

            }

        })

        mediaImgAdapter.notifyDataSetChanged()

    }
}







