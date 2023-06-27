package com.example.angkorchatproto.pay.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentTopUpSelectBankBinding
import com.example.angkorchatproto.pay.adapter.BankAdapter
import java.time.LocalDate

class TopUpSelectBankFragment : Fragment() {
    lateinit var binding: FragmentTopUpSelectBankBinding
    private var mNavController: NavController? = null
    private var mNavHostFragment: NavHostFragment? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopUpSelectBankBinding.inflate(inflater, container, false)

        var topUpAmount = requireArguments().getString("topUpAmount")
        var accountNumber = requireArguments().getString("accountNumber")

        val amount = topUpAmount!!.toInt()

        val bundle = bundleOf(
            "topUpAmount" to amount.toString(),
            "topUpTime" to LocalDate.now().toString(),
            "accountNumber" to accountNumber
        )

        binding.ivCloseSelectBank.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        val bankList = ArrayList<String>()
        bankList.add("ABA")
        bankList.add("PPC")
        bankList.add("WOO-RI")
        bankList.add("BANK1")
        bankList.add("BANK2")
        bankList.add("BANK3")
        bankList.add("BANK4")
        bankList.add("BANK5")
        bankList.add("BANK6")
        bankList.add("BANK7")
        bankList.add("BANK8")
        bankList.add("BANK9")

        val adapter = BankAdapter(requireContext(),bankList,amount,accountNumber)

        binding.rvBankList.adapter = adapter
        binding.rvBankList.layoutManager = GridLayoutManager(requireContext(),3)


        return binding.root
    }


}