package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentJoinBinding
import com.example.angkorchatproto.databinding.FragmentPayMainBinding
import com.example.angkorchatproto.databinding.FragmentPayMainCodeBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class PayMainCodeFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPayMainCodeBinding
//    private var mNowTime = LocalDateTime.now() // 현재 시간할 문자열
//    private var mCountDownTimer: CountDownTimer? = null
//    private var mConvertTime = LocalDateTime.of(mNowTime?.plusDays(1)?.toLocalDate(), LocalTime.of(0,0,0))
//    private var mIsCountDownTimer: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayMainCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun countDownTimer() {
//        if (!mIsCountDownTimer) {
//            Log.d("jongchan.won","남은 초 : ${ChronoUnit.MILLIS.between(mNowTime, mConvertTime)}")
//            mCountDownTimer =
//                object : CountDownTimer(ChronoUnit.MILLIS.between(mNowTime, mConvertTime), 1000) {
//                    override fun onTick(millisUntilFinished: Long) {
//
//                    }
//                    override fun onFinish() {
//
//                    }
//                }
//            mCountDownTimer?.start()
//            mIsCountDownTimer = true
//        }
    }
}