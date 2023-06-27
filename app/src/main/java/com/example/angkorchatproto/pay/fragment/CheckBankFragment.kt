package com.example.angkorchatproto.pay.fragment

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentCheckBankBinding
import com.example.angkorchatproto.pay.room.AccountInfo
import com.example.angkorchatproto.pay.room.AppDatabase
import java.time.LocalDateTime
import java.util.*

class CheckBankFragment : Fragment() {

    lateinit var binding: FragmentCheckBankBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckBankBinding.inflate(inflater, container, false)

        //DB 연결
        val db = AppDatabase.getInstance(requireContext())
        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()
        var topUpAmount = requireArguments().getString("topUpAmount")
        var accountNumber = requireArguments().getString("accountNumber")

        //선택한 은행 출력
        val getBankName = requireArguments().getString("bankName")
        binding.tvBankListName2.text = getBankName.toString()

        //뒤로가기
        binding.ivCloseCheckBank.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        //Depositor Null 확인
        binding.etEnterDepositor.addTextChangedListener(textChecker)

        val transferNumber = randomNumber(10)


        //다음 클릭 시
        binding.btnNext4.setOnClickListener {
            val depositor = binding.etEnterDepositor.text.toString()
            val time = LocalDateTime.now().toString()
            if (db != null) {
                val amount = topUpAmount!!.toInt()
                val topUpPoint = AccountInfo(
                    0,
                    transferNumber,
                    accountNumber.toString(),
                    myNumber,
                    0,
                    amount,
                    "top_up",
                    depositor,
                    accountNumber.toString(),
                    getBankName.toString(),
                    time
                )

                db.paymentDao().insertAccount(topUpPoint)

                val bundle = bundleOf(
                    "transferNumber" to transferNumber
                )

                view?.findNavController()?.navigate(
                    R.id.action_checkBankFragment_to_topUpCompleteByAccountFragment,
                    bundle
                )


            }
        }











        return binding.root
    }

    val textChecker = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            binding.etEnterDepositor.requestFocus()
            binding.etEnterDepositor.setSelection(binding.etEnterDepositor.text.lastIndex + 1)

            if (p0.toString().length > 0 && p0 != null) {
                binding.btnNext4.isEnabled = true
                binding.btnNext4.setBackgroundResource(R.drawable.style_login_btn)
            } else {
                binding.btnNext4.isEnabled = false
                binding.btnNext4.setBackgroundResource(R.drawable.style_disable_btn)
            }

        }

        override fun afterTextChanged(p0: Editable?) {}

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

    fun formatString(length: Int): String {

        val input = randomNumber(length)
        val formattedString = StringBuilder()
        val chunkSize = 4 // 변경된 문자열의 청크 크기

        for (i in input.indices) {
            formattedString.append(input[i])

            // 청크 크기에 도달할 때마다 "-" 추가
            if ((i + 1) % chunkSize == 0 && i != input.length - 1) {
                formattedString.append("-")
            }
        }

        return formattedString.toString()
    }


}