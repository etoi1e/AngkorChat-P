package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentTransferCompleteBinding
import com.example.angkorchatproto.pay.room.AppDatabase


class TransferCompleteFragment : Fragment() {

    lateinit var binding: FragmentTransferCompleteBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTransferCompleteBinding.inflate(inflater,container,false)

        val transferNumber = requireArguments().getString("transferNumber").toString()

        val db = AppDatabase.getInstance(requireContext())
        if(db != null){
            val transferInfo = db.paymentDao().getTransfer(transferNumber)

            val userName = transferInfo.payTo
            val amount = transferInfo.point.toString().replace("-","")

            binding.textView97.text="Transferred ${amount}$\nto ${userName}"

            binding.button3.setOnClickListener {
                view?.findNavController()?.navigate(R.id.action_transferCompleteFragment_to_payMainFragment)
            }



        }

        return binding.root
    }

}