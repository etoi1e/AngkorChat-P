package com.example.angkorchatproto.pay.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentPayMainScanBinding
import com.example.angkorchatproto.databinding.FragmentPayMainScanBottomBinding
import com.example.angkorchatproto.dialog.CustomDialog
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kr.co.kdnavien.naviensmart.presentation.custom.DialogNegativeBtnListener
import kr.co.kdnavien.naviensmart.presentation.custom.DialogPositiveBtnListener

class PayMainScanFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPayMainScanBinding
    private lateinit var payScanBottomBinding: FragmentPayMainScanBottomBinding
    private lateinit var payScanBottomSheetDialog: BottomSheetDialog

    private var mNavController: NavController? = null
    private var mNavHostFragment: NavHostFragment? = null

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

        mNavHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.pay_container) as NavHostFragment
        mNavController = mNavHostFragment?.navController

        binding.cardGuide.setOnClickListener {

            CustomDialog.create(requireContext())

            /**유저이름들어가는 부분*/
            val title = "Angkor"
            val spannable = SpannableStringBuilder(title)
            val color = requireActivity().getColor(R.color.mainYellow)
            spannable.setSpan(
                ForegroundColorSpan(color),
                0,
                title.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            CustomDialog.create(requireContext())
                ?.setTitle(spannable)
                ?.setDesc(SpannableStringBuilder("is that username right?"))
                ?.setCancelable(true)
                ?.setPositiveButtonText(SpannableStringBuilder("Yes"))
                ?.setNegativeButtonText(SpannableStringBuilder("No"))
                ?.setPositiveBtnListener(object : DialogPositiveBtnListener {
                    override fun confirm(division: Int) {
                        mNavController?.navigate(R.id.action_payMainFragment_to_payTransferFragment)
                    }
                })
                ?.setNegativeBtnListener(object : DialogNegativeBtnListener {
                    override fun cancel(division: Int) {
                    }
                })
                ?.showTwoButton()




        }


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