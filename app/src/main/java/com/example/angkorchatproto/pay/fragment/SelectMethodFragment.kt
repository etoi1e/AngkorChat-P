package com.example.angkorchatproto.pay.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.angkorchatproto.R

import com.example.angkorchatproto.databinding.FragmentSelectMethodBinding
import com.example.angkorchatproto.pay.room.AccountInfo
import com.example.angkorchatproto.pay.room.AppDatabase
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


class SelectMethodFragment : Fragment() {
    lateinit var binding: FragmentSelectMethodBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectMethodBinding.inflate(inflater, container, false)

        //db호출
        val db = AppDatabase.getInstance(requireContext())

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()

        //충전금액 불러오기
        var topUpAmount = requireArguments().getString("topUpAmount")

        //충전수단 저장 변수
        /** 0=debit 1=account */
        var selectMethod = 0


        val transferNumber = randomNumber(10)

        //뒤로가기
        binding.ivCloseSelectMethod.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        //Debit 카드로 충전 선택 시
        binding.debitLayout.setOnClickListener {
            binding.debitLayout.setBackgroundResource(R.drawable.border_round12_yellow)
            binding.accountLayout.setBackgroundResource(R.drawable.border_round12_gray)
            selectMethod = 0
        }

        //Account 로 충전 선택 시
        binding.accountLayout.setOnClickListener {
            binding.accountLayout.setBackgroundResource(R.drawable.border_round12_yellow)
            binding.debitLayout.setBackgroundResource(R.drawable.border_round12_gray)
            selectMethod = 1
        }

        //Next 클릭 시
        binding.btnNextSelectMethod.setOnClickListener {
            if (selectMethod == 0) {
                //debit/credit 으로 충전 시
                if (db != null) {
                    val accountNumber = db.paymentDao().getAccountNumber(myNumber)
                    val amount = topUpAmount!!.toInt()
                    val time = LocalDateTime.now().toString()

                    val topUpPoint = AccountInfo(
                        0,
                        transferNumber,
                        accountNumber,
                        myNumber,
                        0,
                        amount,
                        "top_up",
                        "debit",
                        "",
                        accountNumber,
                        "",
                        time
                    )

                    db.paymentDao().insertAccount(topUpPoint)


                    val bundle = bundleOf(
                        "transferNumber" to transferNumber
                    )
                    Log.d("TAG-bundle", bundle.toString())
                    view?.findNavController()
                        ?.navigate(
                            R.id.action_selectMethodFragment_to_topUpCompleteFragment,
                            bundle
                        )
                }

            }

            if (selectMethod == 1) {
                if (db != null) {

                    val accountNumber = db.paymentDao().getAccountNumber(myNumber)
                    val amount = topUpAmount!!.toInt()
                    val bundle = bundleOf(
                        "topUpAmount" to amount.toString(),
                        "topUpTime" to LocalDate.now().toString(),
                        "accountNumber" to accountNumber
                    )
                    view?.findNavController()
                        ?.navigate(
                            R.id.action_selectMethodFragment_to_topUpSelectBankFragment,
                            bundle
                        )
                }

            }
        }


        return binding.root
    }

    fun randomNumber(length:Int): String {
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
