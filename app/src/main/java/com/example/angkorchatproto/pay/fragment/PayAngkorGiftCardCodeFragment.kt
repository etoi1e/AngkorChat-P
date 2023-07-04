package com.example.angkorchatproto.pay.fragment

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentPayGiftcardCodeBinding
import com.example.angkorchatproto.databinding.FragmentPayMainCodeBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel
import com.example.angkorchatproto.pay.room.AccountInfo
import com.example.angkorchatproto.pay.room.AppDatabase
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.util.*

class PayAngkorGiftCardCodeFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPayGiftcardCodeBinding
    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayGiftcardCodeBinding.inflate(inflater, container, false)

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()

        val db = AppDatabase.getInstance(requireContext())

        binding.btnBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        binding.ivClose.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }


        val textChecker = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.etGiftCode.text.isEmpty()) {
                    binding.btnAdd.background =
                        requireActivity().getDrawable(R.drawable.style_disable_btn)
                    binding.btnAdd.isEnabled = false
                    binding.ivQr.visibility = View.VISIBLE
                } else {
                    binding.btnAdd.background =
                        requireActivity().getDrawable(R.drawable.style_login_btn)
                    binding.btnAdd.isEnabled = true
                    binding.ivQr.visibility = View.GONE
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        }

        if (db != null) {

            val accountNumber = db.paymentDao().getAccountNumber(myNumber)

            //Text Check
            binding.etGiftCode.addTextChangedListener(textChecker)


            binding.btnBack.setOnClickListener {
                view?.findNavController()?.popBackStack()
            }

            binding.btnAdd.setOnClickListener {
                hideButtonDelayed()

                val transferNumber = randomNumber(10)
                val time = LocalDateTime.now().toString()


                val giftCardInfo =
                    AccountInfo(
                        0,
                        transferNumber,
                        accountNumber,
                        myNumber,
                        0,
                        1000,
                        "top_up",
                        "gift_card",
                        "",
                        "Gift Card",
                        "",
                        time
                    )

                db.paymentDao().insertAccount(giftCardInfo)

            }

            binding.ivClose.setOnClickListener {
                view?.findNavController()?.popBackStack()
            }

            binding.successCardView.setOnClickListener {
                view?.findNavController()
                    ?.navigate(R.id.action_payAngkorGiftCardCodeFragment_to_payAngkorGiftCardHistoryFragment)
            }

        }


        return binding.root
    }

    private fun hideButtonDelayed() {
        job = CoroutineScope(Dispatchers.Main).launch {
            binding.successCardView.visibility = View.VISIBLE // 버튼을 보이게 함

            delay(2000) // 5초 대기

            binding.successCardView.visibility = View.INVISIBLE // 버튼을 숨김
        }
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

}