package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentPayDetailBinding
import com.example.angkorchatproto.databinding.FragmentTransferCompleteBinding
import com.example.angkorchatproto.pay.room.AppDatabase
import java.text.DecimalFormat


class PayDetailFragment : Fragment() {

    lateinit var binding: FragmentPayDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPayDetailBinding.inflate(inflater, container, false)

        //이전 페이지로 가기
        binding.ivClosePointTopUp2.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        val type = requireArguments()?.getString("type")
        val transferNumber = requireArguments()?.getString("transferNumber").toString()

        binding.textView106.text = type

        //현재 사용자 번호 불러오기


        val db = AppDatabase.getInstance(requireContext())

        val numberFormatter = DecimalFormat("###,###")

        if(db != null){
            val transferInfo = db.paymentDao().getTransfer(transferNumber)
            if(transferInfo.type == "debit"){
                binding.creditLayout.visibility = View.VISIBLE
                binding.textView107.text = numberFormatter.format(transferInfo.point)
                binding.textView108.text = transferInfo.time
                binding.tvTimeCompleteCredit.text = transferInfo.userId
            }

            if(transferInfo.type =="account"){
                binding.accountLayout.visibility = View.VISIBLE
                binding.textView107.text = numberFormatter.format(transferInfo.point)
                binding.textView108.text = transferInfo.time
                binding.tvDepositorComplete.text = transferInfo.depositor
                binding.tvBankNameComplete.text = transferInfo.bankName
                binding.tvAccountNumberComplete.text = transferInfo.accountNumber

            }

            if(transferInfo.type == "gift_card"){

            }

            if(type == "Used"){
                binding.usedLayout.visibility = View.VISIBLE
                binding.textView107.text = numberFormatter.format(transferInfo.point)
                binding.textView108.text = transferInfo.time
                binding.tvAmountCompleteCredit.text = transferInfo.payTo

            }
        }





        return binding.root
    }


}