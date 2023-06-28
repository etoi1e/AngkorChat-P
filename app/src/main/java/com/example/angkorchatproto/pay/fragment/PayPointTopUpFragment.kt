package com.example.angkorchatproto.pay.fragment

import android.content.Intent
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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentPayPointTopUpBinding
import com.example.angkorchatproto.pay.room.AccountInfo
import com.example.angkorchatproto.pay.room.AppDatabase
import java.time.LocalDateTime
import java.util.*

class PayPointTopUpFragment : Fragment() {
    lateinit var binding: FragmentPayPointTopUpBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPayPointTopUpBinding.inflate(inflater, container, false)

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()

        //이전 페이지 확인
        val checkTopUp = requireContext().getSharedPreferences("checkPrePage", 0)
        var prePage = checkTopUp.getBoolean("checkPrePage", false)


        //뒤로가기
        binding.ivClosePointTopUp.setOnClickListener {
            view?.findNavController()?.popBackStack()
            checkTopUp.edit().clear().apply()



        }

        //Amount Null 확인
        binding.etTopUpAmount.addTextChangedListener(textChecker)


        binding.btnTopUp10000.setOnClickListener {
            topUpBtn(10000)
        }

        binding.btnTopUp50000.setOnClickListener {
            topUpBtn(50000)
        }

        binding.btnTopUp100000.setOnClickListener {
            topUpBtn(100000)
        }





        if (prePage) {
            val getName = requireArguments().getString("name", null)
            val getNumber = requireArguments().getString("number", null)
            //Transfer 화면으로 이동
            binding.textView60.text = "Transfer"

            binding.btnTopUpNext.setOnClickListener {

                val transferNumber = randomNumber(10)

                val db = AppDatabase.getInstance(requireContext())
                if (db != null) {
                    val accountNumber = db.paymentDao().getAccountNumber(myNumber)
                    val amount = binding.etTopUpAmount.text.toString().toInt()
                    val time = LocalDateTime.now().toString()


                    val transfer = AccountInfo(
                        0,
                        transferNumber,
                        accountNumber,
                        myNumber,
                        0,
                        -amount,
                        "transfer",
                        "point",
                        myNumber,
                        getNumber,
                        getName,
                        time
                    )

                    db.paymentDao().insertAccount(transfer)

                    val bundle = bundleOf("transferNumber" to transferNumber)
                    view?.findNavController()
                        ?.navigate(
                            R.id.action_payPointTopUpFragment_to_topUpCompleteByAccountFragment,
                            bundle
                        )
                }

            }


        } else {
            binding.btnTopUpNext.setOnClickListener {

                val checkMinimum = binding.etTopUpAmount.text.length
                if (checkMinimum < 5) {
                    binding.imMinimum.visibility = View.VISIBLE
                    binding.tvMinimum.visibility = View.VISIBLE
                } else {
                    val bundle = bundleOf("topUpAmount" to binding.etTopUpAmount.text.toString())
                    view?.findNavController()
                        ?.navigate(
                            R.id.action_payPointTopUpFragment_to_selectMethodFragment,
                            bundle
                        )
                }

            }
        }








        return binding.root
    }

    fun topUpBtn(topUp: Int) {
        val putAmount = binding.etTopUpAmount.text.toString()
        var amount = 0

        if (putAmount != "") {
            amount = putAmount.toInt()
        }

        if (amount <= 1000000) {
            val setAmount = amount + topUp
            binding.etTopUpAmount.setText(setAmount.toString())


        }
    }

    val textChecker = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            binding.etTopUpAmount.requestFocus()
            binding.etTopUpAmount.setSelection(binding.etTopUpAmount.text.lastIndex + 1)

            if (p0.toString().length > 0 && p0 != null) {
                binding.btnTopUpNext.isEnabled = true
                binding.btnTopUpNext.setBackgroundResource(R.drawable.style_login_btn)
            } else {
                binding.btnTopUpNext.isEnabled = false
                binding.btnTopUpNext.setBackgroundResource(R.drawable.style_disable_btn)
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


}