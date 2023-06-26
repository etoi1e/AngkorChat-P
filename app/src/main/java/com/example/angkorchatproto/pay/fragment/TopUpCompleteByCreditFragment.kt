package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentTopUpCompleteByCreditBinding
import com.example.angkorchatproto.pay.room.AppDatabase


class TopUpCompleteByCreditFragment : Fragment() {
    lateinit var binding: FragmentTopUpCompleteByCreditBinding
    private var mNavController: NavController? = null
    private var mNavHostFragment: NavHostFragment? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopUpCompleteByCreditBinding.inflate(inflater, container, false)

        var topUpAmount = requireArguments().getString("topUpAmount")
        var topUpTime = requireArguments().getString("topUpTime")
        var accountNumber = requireArguments().getString("accountNumber")
        Log.d("TAG-topUpAmount",topUpAmount.toString())
        Log.d("TAG-topUpTime",topUpTime.toString())
        Log.d("TAG-accountNumber",accountNumber.toString())

        binding.tvAmountCompleteCredit.text = topUpAmount
        binding.tvTimeCompleteCredit.text = topUpTime

        val db = AppDatabase.getInstance(requireContext())
        if (db != null) {
            val totalPoint =db.paymentDao().getPoint(accountNumber.toString())
            binding.tvTotalCompleteCredit.text = totalPoint.toString()
        }




        binding.btnDoneTopUpCredit.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_topUpCompleteFragment_to_payMainFragment)
        }



        return binding.root
    }

}