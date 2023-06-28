package com.example.angkorchatproto.pay.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentTopUpCompleteByAccountBinding
import com.example.angkorchatproto.pay.room.AppDatabase

class TopUpCompleteByAccountFragment : Fragment() {
    lateinit var binding: FragmentTopUpCompleteByAccountBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopUpCompleteByAccountBinding.inflate(inflater,container,false)

        //Close 클릭 시 메인으로 이동
        binding.btnCloseTopUp.setOnClickListener {
            val checkTopUp = requireContext().getSharedPreferences("checkPrePage", 0)
            checkTopUp.edit().clear().apply()

            view?.findNavController()?.navigate(R.id.topUpCompleteByAccountFragment_to_payMainFragment)
        }


        //bundle 호출
        val transferNumber = requireArguments().getString("transferNumber")

        val db = AppDatabase.getInstance(requireContext())
        if(db != null){
            val transferInfo = db.paymentDao().getTransfer(transferNumber.toString())
            binding.tvTransferNumberComplete.text = transferInfo.transferNumber
            binding.tvBankNameComplete.text = transferInfo.bankName
            binding.tvAmountCompleteAccount.text = "${transferInfo.point}P"
            binding.tvDepositorComplete.text = transferInfo.depositor
            binding.tvAccountNumberComplete.text = transferInfo.payTo
            binding.tvDeadlineComplete.text = transferInfo.time
        }

        return binding.root
    }

}