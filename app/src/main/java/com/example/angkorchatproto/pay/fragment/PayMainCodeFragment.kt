package com.example.angkorchatproto.pay.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.angkorchatproto.databinding.FragmentPayMainCodeBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel
import com.example.angkorchatproto.pay.room.AppDatabase
import java.util.*

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

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()

        countDownTime()

        setAccountInfo(myNumber)

        binding.ivRefresh.setOnClickListener {
            mCountDownTimer?.cancel()
            binding.tvCounter.text = "03:00"
            countDownTime()
        }


        return binding.root
    }

    private fun countDownTime() {
        mCountDownTimer = object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000) % 60
                val minutes = (millisUntilFinished / (1000 * 60)) % 60

                val timeLeftFormatted =
                    String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
                Log.d("Countdown", timeLeftFormatted) // 출력 형식: 02:30 (시:분:초)
                binding.tvCounter.text = timeLeftFormatted
            }

            override fun onFinish() {
                Log.d("Countdown", "Countdown finished")
            }
        }

        mCountDownTimer?.start()
    }

    @SuppressLint("SetTextI18n")
    private fun setAccountInfo(userId: String) {
        val db = AppDatabase.getInstance(requireContext().applicationContext)
        if (db != null) {
            /**계좌번호*/
            val accountNumber = db.paymentDao().getAccountNumber(userId)
            //계좌번호 적용
            binding.tvPayMainAccount.text = accountNumber

            /**포인트*/
            val point = db.paymentDao().getPoint(accountNumber)
            //잔여포인트
            binding.tvPayMainPoint.text = point.toString() + "Point"

        }
    }



}