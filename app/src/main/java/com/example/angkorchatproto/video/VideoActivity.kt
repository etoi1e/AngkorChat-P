package com.example.angkorchatproto.video

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.angkorchatproto.R
import com.example.angkorchatproto.base.BaseActivity
import com.example.angkorchatproto.databinding.ActivityVideoBinding
import com.example.angkorchatproto.service.Data
import com.example.angkorchatproto.service.HttpRxKotlin
import io.agora.rtc2.*
import io.agora.rtc2.video.VideoCanvas
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class VideoActivity : BaseActivity() {
    lateinit var binding: ActivityVideoBinding
    private var mMode: String? = null
    private var mFcmSendToken:String? = null
    private var mPhoneNumber: String? = null
    // Fill the App ID of your project generated on Agora Console.
    private val appId = "e7b224b1113e4f529188e7c5fbda5a88"

    // Fill the channel name.
    private val channelName = "AngKorChatProto"

    // Fill the temp token generated on Agora Console.
    private val token = "007eJxTYEj67vOHOVTz62XzRQffPDo+s1B22fb4/ESHF6+MVYR/PXuqwJBqnmRkZJJkaGhonGqSZmpkaWhhkWqebJqWlJJommhhIZGbmtIQyMjw0luHgREKQXx+Bse8dO/8IueMxJKAovySfAYGAIPpJU4="
    // An integer that identifies the local user.
    private var uid = 0
    private var isJoined = false

    private var agoraEngine: RtcEngine? = null

    //SurfaceView to render local video in a Container.
    private var localSurfaceView: SurfaceView? = null

    //SurfaceView to render Remote video in a Container.
    private var remoteSurfaceView: SurfaceView? = null
    private val permissionReqId = 22
    private val requestedPermissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA)

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        // Listen for the remote host joining the channel to get the uid of the host.
        override fun onUserJoined(uid: Int, elapsed: Int) {
            Log.d("jongchan.won","onUserJoined")
            showMessage("Remote user joined $uid")

            // Set the remote video view
            runOnUiThread { setupRemoteVideo(uid) }
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            Log.d("jongchan.won","onJoinChannelSuccess")
            isJoined = true
//            showMessage("Joined Channel $channel")
            runOnUiThread {
                binding.ibVideoOff.visibility = View.VISIBLE
                binding.ibVoiceMute.visibility = View.VISIBLE
                binding.JoinButton.visibility = View.GONE
            }
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            Log.d("jongchan.won","onUserOffline")
//            showMessage("Remote user offline $uid $reason")
            runOnUiThread {
                remoteSurfaceView!!.visibility = View.GONE
                mMode = "send"
                leaveChannel()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityVideoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        // If all the permissions are granted, initialize the RtcEngine object and join a channel.
        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, requestedPermissions, permissionReqId)
        }

        mMode = intent.getStringExtra("mode")
        mFcmSendToken = intent.getStringExtra("token")
        mPhoneNumber = intent.getStringExtra("phoneNumber")

//        val shard = getSharedPreferences("loginNumber",0)
//        uid = shard.getString("userNumber","")

        setupVideoSDKEngine()
        setContentView(binding.root)
        if (mMode == "send") {
            joinChannel(null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        agoraEngine!!.stopPreview()
        agoraEngine!!.leaveChannel()

        // Destroy the engine in a sub-thread to avoid congestion
        Thread {
            RtcEngine.destroy()
            agoraEngine = null
        }.start()
    }

    private fun setupRemoteVideo(uid: Int) {
        val container = findViewById<FrameLayout>(R.id.remote_video_view_container)
        remoteSurfaceView = SurfaceView(baseContext)
        remoteSurfaceView?.setZOrderMediaOverlay(true)
        container.addView(remoteSurfaceView)
        agoraEngine!!.setupRemoteVideo(
            VideoCanvas(
                remoteSurfaceView,
                VideoCanvas.RENDER_MODE_FIT,
                uid
            )
        )
        // Display RemoteSurfaceView.
        remoteSurfaceView?.visibility = View.VISIBLE
    }

    private fun checkSelfPermission(): Boolean
    {
        if (ContextCompat.checkSelfPermission(this, requestedPermissions[0]) !=  PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, requestedPermissions[1]) !=  PackageManager.PERMISSION_GRANTED)
        {
            return false
        }
        return true
    }

    fun showMessage(message: String?) {
        runOnUiThread {
            Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupVideoSDKEngine() {
        try {
            val config = RtcEngineConfig()
            config.mContext = baseContext
            config.mAppId = appId
            config.mEventHandler = mRtcEventHandler
            agoraEngine = RtcEngine.create(config)
            // By default, the video module is disabled, call enableVideo to enable it.
            agoraEngine?.enableVideo()
        } catch (e: Exception) {
            showMessage(e.toString())
        }
    }

    private fun setupLocalVideo() {
        val container = findViewById<FrameLayout>(R.id.local_video_view_container)
        // Create a SurfaceView object and add it as a child to the FrameLayout.
        localSurfaceView = SurfaceView(baseContext)
        localSurfaceView?.setZOrderMediaOverlay(true);
        localSurfaceView?.setZOrderOnTop(true);
        container.addView(localSurfaceView)
        // Call setupLocalVideo with a VideoCanvas having uid set to 0.
        agoraEngine!!.setupLocalVideo(
            VideoCanvas(
                localSurfaceView,
                VideoCanvas.RENDER_MODE_HIDDEN,
                0
            )
        )
    }

    fun joinChannel(view: View?) {
        if (checkSelfPermission()) {
            val options = ChannelMediaOptions()

            // For a Video call, set the channel profile as COMMUNICATION.
            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
            // Set the client role as BROADCASTER or AUDIENCE according to the scenario.
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            // Display LocalSurfaceView.
            setupLocalVideo()
            localSurfaceView!!.visibility = View.VISIBLE
            // Start local preview.
            agoraEngine!!.startPreview()
            // Join the channel with a temp token.
            // You need to specify the user ID yourself, and ensure that it is unique in the channel.
            Log.d("jongchan.won", "참여 여부 : ${ agoraEngine!!.joinChannel(token, channelName, uid, options) }")
            if (mFcmSendToken != null && mMode == "send") {
                sendCallFcmMsg()
            }
        } else {
            Toast.makeText(applicationContext, "Permissions was not granted", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun sendCallFcmMsg() {
        Log.d("jongchan.won", "상대방에게 영상통화를 요청합니다")
        val shared = getSharedPreferences("loginNumber", 0)
        val sendData = Data.VideoCallInfo(shared.getString("userNumber",""), shared.getString("token",""))
        HttpRxKotlin.sendFCMNotification(mFcmSendToken!!, sendData, object : Observer<Data.HttpResponseBase> {
            override fun onSubscribe(d: Disposable) {
                Log.d("jongchan.won","onSubscribe")
            }

            override fun onError(e: Throwable) {
                Log.d("jongchan.won","onError $e")
            }

            override fun onComplete() {
                Log.d("jongchan.won","onComplete")
            }

            override fun onNext(t: Data.HttpResponseBase) {
                Log.d("jongchan.won","onNext $t")
            }
        })
    }

    fun leaveExitChannel(view: View?) {
        if (!isJoined) {
//            showMessage("Join a channel first")
        } else {
            agoraEngine!!.leaveChannel()
//            showMessage("You left the channel")
            // Stop remote video rendering.
            if (remoteSurfaceView != null) remoteSurfaceView!!.visibility = View.GONE
            // Stop local video rendering.
            if (localSurfaceView != null) localSurfaceView!!.visibility = View.GONE
            isJoined = false

            binding.ibVideoOff.visibility = View.GONE
            binding.ibVoiceMute.visibility = View.GONE
            binding.JoinButton.visibility = View.VISIBLE
        }
        finish()
    }

    fun leaveChannel() {
        if (!isJoined) {
//            showMessage("Join a channel first")
        } else {
            agoraEngine!!.leaveChannel()
//            showMessage("You left the channel")
            // Stop remote video rendering.
            if (remoteSurfaceView != null) remoteSurfaceView!!.visibility = View.GONE
            // Stop local video rendering.
            if (localSurfaceView != null) localSurfaceView!!.visibility = View.GONE
            isJoined = false

            binding.ibVideoOff.visibility = View.GONE
            binding.ibVoiceMute.visibility = View.GONE
            binding.JoinButton.visibility = View.VISIBLE
        }
    }
}