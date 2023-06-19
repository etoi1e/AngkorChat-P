package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentIdCardBinding
import com.example.angkorchatproto.databinding.FragmentIdCardBottomBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class IdCardFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentIdCardBinding
    lateinit var idCardBottomBinding: FragmentIdCardBottomBinding
    lateinit var idCardBottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIdCardBinding.inflate(inflater, container, false)
        idCardBottomBinding = FragmentIdCardBottomBinding.inflate(layoutInflater)
        idCardBottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        binding.cardGuide.setOnClickListener {
            val dialogView = idCardBottomBinding.root
            idCardBottomSheetDialog.setContentView(dialogView)
            setupRatio(idCardBottomSheetDialog)
            idCardBottomSheetDialog.show()
        }
        binding.btnBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.ivClose.setOnClickListener {
            requireActivity().finish()
        }
        idCardBottomBinding.btnRetake.setOnClickListener {
            idCardBottomSheetDialog.cancel()
        }
        idCardBottomBinding.btnYes.setOnClickListener {
            idCardBottomSheetDialog.cancel()
            view?.findNavController()?.navigate(R.id.action_idCardFragment_to_paymentRegistrationFragment)
        }
        startCamera()
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
        return getWindowHeight() * 60 / 100
        // 기기 높이 대비 비율 설정 부분!!
        // 위 수치는 기기 높이 대비 80%로 다이얼로그 높이를 설정
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}