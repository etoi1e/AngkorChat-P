package com.example.angkorchatproto.pay.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.angkorchatproto.databinding.FragmentPayMainCodeBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel
import com.example.angkorchatproto.pay.room.AccountInfo
import com.example.angkorchatproto.pay.room.AppDatabase
import java.text.DecimalFormat
import java.time.LocalDateTime
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

    @RequiresApi(Build.VERSION_CODES.O)
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

        val db = AppDatabase.getInstance(requireContext())

        if (db != null) {

            val accountNumber = db.paymentDao().getAccountNumber(myNumber)
            binding.imageView17.setOnClickListener {
                val transferNumber = randomNumber(10)
                val time = LocalDateTime.now().toString()

                val newAccount =
                    AccountInfo(
                        0,
                        transferNumber,
                        accountNumber,
                        myNumber,
                        1000,
                        0,
                        "transfer",
                        "received",
                        "",
                        "Angkor",
                        "",
                        time
                    )

                db.paymentDao().insertAccount(newAccount)
            }

        }


        return binding.root
    }

    fun randomNumber(length: Int): String {
        val randomNumberLength = length
        val allowedChars = "0123456789"
        val random = Random(System.currentTimeMillis())

        return buildString {
            repeat(randomNumberLength) {
                val randomIndex = random.nextInt(allowedChars.length)
                append(allowedChars[randomIndex])
            }
        }
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

            val numberFormatter = DecimalFormat("###,###")

            /**포인트*/
            val point = numberFormatter.format(db.paymentDao().getPoint(accountNumber))
            //잔여포인트
            binding.tvPayMainPoint.text = point.toString() + " Point"

        }
    }


}