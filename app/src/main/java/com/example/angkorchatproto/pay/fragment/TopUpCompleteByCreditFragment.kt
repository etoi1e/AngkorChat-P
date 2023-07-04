package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentTopUpCompleteByCreditBinding
import com.example.angkorchatproto.pay.room.AppDatabase


class TopUpCompleteByCreditFragment : Fragment() {
    lateinit var binding: FragmentTopUpCompleteByCreditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopUpCompleteByCreditBinding.inflate(inflater, container, false)

        var transferNumber = requireArguments().getString("transferNumber")

        val db = AppDatabase.getInstance(requireContext())
        if (db != null) {
            val transferInfo = db.paymentDao().getTransfer(transferNumber.toString())
            val totalPoint = db.paymentDao().getPoint(transferInfo.accountNumber)

            binding.tvAmountCompleteCredit.text = transferInfo.point.toString()
            binding.tvTimeCompleteCredit.text = transferInfo.time

            binding.tvTotalCompleteCredit.text = totalPoint.toString()

        }

        binding.btnDoneTopUpCredit.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_topUpCompleteFragment_to_payMainFragment)
        }



        return binding.root
    }

}