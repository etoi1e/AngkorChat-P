package com.example.angkorchatproto.friends

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Size
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.angkorchatproto.R
import com.example.angkorchatproto.base.BaseActivity
import com.example.angkorchatproto.databinding.ActivityQrCameraBinding
import com.example.angkorchatproto.databinding.FragmentMyQrBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * Package Name : com.example.angkorchatproto.friends
 * Class Name : QrCameraActivity
 * Description :
 * Created by de5ember on 2023/06/07.
 */
class QrCameraActivity: BaseActivity() {
    lateinit var binding: ActivityQrCameraBinding
    lateinit var myQrBinding: FragmentMyQrBinding
    lateinit var myQrBottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityQrCameraBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        myQrBinding = FragmentMyQrBinding.inflate(layoutInflater)
        myQrBottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
        // 카메라 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
        }

        binding.btnMyQr.setOnClickListener {
            val dialogView = myQrBinding.root
            myQrBottomSheetDialog.setContentView(dialogView)
            setupRatio(myQrBottomSheetDialog)
            myQrBottomSheetDialog.show()
        }

        binding.btnClose.setOnClickListener {
            finish()
        }

        myQrBinding.btnMyQr.setOnClickListener {
            myQrBottomSheetDialog.cancel()
        }

        startCamera()
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    /* 바텀시트 다이얼로그의 디폴트 높이를 가져오는 함수 */
    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight()
        // 기기 높이 대비 비율 설정 부분!!
        // 위 수치는 기기 높이 대비 80%로 다이얼로그 높이를 설정
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .setTargetResolution(Size(640, 480))
                .build()
                .also {
                    it.setSurfaceProvider(binding.preview.surfaceProvider)
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }
}