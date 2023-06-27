package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentTopUpHistoryBinding
import com.example.angkorchatproto.pay.adapter.PointHistoryAdapter
import com.example.angkorchatproto.pay.room.AppDatabase

class TopUpHistoryFragment : Fragment() {
    lateinit var binding: FragmentTopUpHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopUpHistoryBinding.inflate(inflater, container, false)

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()


        val db = AppDatabase.getInstance(requireContext())

        if (db != null) {

            val accountNumber = db.paymentDao().getAccountNumber(myNumber)

            db.paymentDao().getAllByContent(accountNumber, "top_up")

            val transferList = db.paymentDao().getAllByContent(accountNumber, "top_up")
            if (transferList != null) {
                val adapter = PointHistoryAdapter(requireContext(),transferList)
                binding.rvTopUpHistory.adapter = adapter
                binding.rvTopUpHistory.layoutManager = GridLayoutManager(requireContext(),1)
            }
        }


        return binding.root


    }

}