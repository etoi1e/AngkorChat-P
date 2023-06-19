package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
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
import java.util.Locale

class PayMainCodeFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPayMainCodeBinding
    private var mCountDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        mCountDownTimer?.cancel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayMainCodeBinding.inflate(inflater, container, false)
        countDownTime()

        binding.ivRefresh.setOnClickListener {
            mCountDownTimer?.cancel()
            binding.tvCounter.text= "03:00"
            countDownTime()
        }
        return binding.root
    }

    private fun countDownTime() {
        mCountDownTimer = object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000) % 60
                val minutes = (millisUntilFinished / (1000 * 60)) % 60

                val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
                Log.d("Countdown", timeLeftFormatted) // 출력 형식: 02:30 (시:분:초)
                binding.tvCounter.text = timeLeftFormatted
            }

            override fun onFinish() {
                Log.d("Countdown", "Countdown finished")
            }
        }

        mCountDownTimer?.start()
    }
}