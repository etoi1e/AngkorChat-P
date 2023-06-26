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

class PayPointTopUpFragment : Fragment() {
    lateinit var binding: FragmentPayPointTopUpBinding
    private var mNavController: NavController? = null
    private var mNavHostFragment: NavHostFragment? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPayPointTopUpBinding.inflate(inflater, container, false)
        mNavHostFragment =
            childFragmentManager.findFragmentById(R.id.pay_container) as? NavHostFragment
        mNavController = mNavHostFragment?.navController

        //뒤로가기
        binding.ivClosePointTopUp.setOnClickListener {
            view?.findNavController()?.popBackStack()
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


        binding.btnTopUpNext.setOnClickListener {

            val checkMinimum = binding.etTopUpAmount.text.length
            if (checkMinimum < 5) {
                binding.imMinimum.visibility = View.VISIBLE
                binding.tvMinimum.visibility = View.VISIBLE
            } else {
                val bundle = bundleOf("topUpAmount" to binding.etTopUpAmount.text.toString())
                view?.findNavController()
                    ?.navigate(R.id.action_payPointTopUpFragment_to_selectMethodFragment,bundle)
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


}