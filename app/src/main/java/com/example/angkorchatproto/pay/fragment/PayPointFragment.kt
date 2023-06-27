package com.example.angkorchatproto.pay.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentPayAngkorPointBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel
import com.example.angkorchatproto.pay.room.AppDatabase

class PayPointFragment : Fragment() {
    lateinit var binding: FragmentPayAngkorPointBinding
    private var mNavController: NavController? = null
    private var mNavHostFragment: NavHostFragment? = null
    private var mSelectTab = 0

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayAngkorPointBinding.inflate(inflater, container, false)
        mNavHostFragment =
            childFragmentManager.findFragmentById(R.id.pay_point_container) as? NavHostFragment
        mNavController = mNavHostFragment?.navController

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()

        //페이지 닫기
        binding.ivClosePayPoint.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        if (binding.btnTopUp.isChecked) {
            binding.btnUsed.isChecked = false
        }

        //탭 옮기기
        binding.btnTopUp.setOnClickListener {
            if (mSelectTab == 1) {
                mSelectTab = 0
                binding.btnTopUp.isChecked = true
                binding.btnUsed.isChecked = false
                mNavController?.popBackStack()

            } else {
                binding.btnTopUp.isChecked = true
                binding.btnUsed.isChecked = false
            }
        }
        binding.btnUsed.setOnClickListener {
            if (mSelectTab == 0) {
                mSelectTab = 1
                binding.btnTopUp.isChecked = false
                binding.btnUsed.isChecked = true
                mNavController?.navigate(R.id.action_topUpHistoryFragment_to_usedHistoryFragment)
            } else {
                binding.btnTopUp.isChecked = false
                binding.btnUsed.isChecked = true
            }
        }

        //db정보 불러오기
        val db = AppDatabase.getInstance(requireContext().applicationContext)
        if (db != null) {
            val account = db.paymentDao().getAccountNumber(myNumber)
            val point = db.paymentDao().getPoint(account)
            binding.tvPointPayPoint.text = point.toString()
        }

        //TopUp 이동
        binding.btnTopUpPayPoint.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_payPointFragment_to_payPointTopUpFragment)
        }

        //Transfer 이동
        binding.btnTransferPayPoint.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_payPointFragment_to_payPointTransferFragment)
        }



        return binding.root
    }


}