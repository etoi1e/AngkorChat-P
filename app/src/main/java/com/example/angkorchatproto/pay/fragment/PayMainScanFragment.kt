package com.example.angkorchatproto.pay.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentPayMainScanBinding
import com.example.angkorchatproto.databinding.FragmentPayMainScanBottomBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class PayMainScanFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPayMainScanBinding
    private lateinit var payScanBottomBinding: FragmentPayMainScanBottomBinding
    private lateinit var payScanBottomSheetDialog: BottomSheetDialog

    override fun onResume() {
        super.onResume()
        // 카메라 권한 확인
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), 0)
        }
        startCamera()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayMainScanBinding.inflate(inflater, container, false)
        payScanBottomBinding = FragmentPayMainScanBottomBinding.inflate(layoutInflater)
        payScanBottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        return binding.root
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
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
        }, ContextCompat.getMainExecutor(requireActivity()))
    }
}