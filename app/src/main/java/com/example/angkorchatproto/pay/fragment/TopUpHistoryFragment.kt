package com.example.angkorchatproto.pay.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentTopUpHistoryBinding
import com.example.angkorchatproto.pay.adapter.PointHistoryAdapter
import com.example.angkorchatproto.pay.room.AppDatabase


class TopUpHistoryFragment : Fragment() {
    lateinit var binding: FragmentTopUpHistoryBinding

    @SuppressLint("ResourceAsColor")
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

            val items = ArrayList<String>()
            items.add("Recent")
            items.add("High to Low")
            items.add("Low to High")

            binding.ivSearchTopUp.setOnClickListener {



            }



            val spAdapter = ArrayAdapter(
                requireContext(),
                androidx.transition.R.layout.support_simple_spinner_dropdown_item,
                items
            )
            binding.spHistory.adapter = spAdapter

            //전체 불러오기의 경우
            var transferList = db.paymentDao().getAllByContent(accountNumber, "top_up")
            if (transferList != null) {
                val adapter = PointHistoryAdapter(requireContext(), transferList, "topUp")
                binding.rvTopUpHistory.adapter = adapter
                binding.rvTopUpHistory.layoutManager = GridLayoutManager(requireContext(), 1)
            }

            binding.btnAllTopUpHistory.setBackgroundResource(R.drawable.round8_gray_box)
            binding.btnAllTopUpHistory.setTextColor(requireContext().getColor(R.color.white))

            binding.btnDebitTopUpHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
            binding.btnTransferTopUpHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
            binding.btnGiftCard.setBackgroundResource(R.drawable.round8_gray_stroke_box)





            //ALL 클릭 시
            binding.btnAllTopUpHistory.setOnClickListener {
                transferList = db.paymentDao().getAllByContent(accountNumber, "top_up")

                val adapter = PointHistoryAdapter(requireContext(), transferList, "topUp")
                binding.rvTopUpHistory.adapter = adapter
                binding.rvTopUpHistory.layoutManager = GridLayoutManager(requireContext(), 1)

                binding.btnAllTopUpHistory.setBackgroundResource(R.drawable.round8_gray_box)
                binding.btnAllTopUpHistory.setTextColor(requireContext().getColor(R.color.white))

                binding.btnDebitTopUpHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                binding.btnDebitTopUpHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                binding.btnTransferTopUpHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                binding.btnTransferTopUpHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                binding.btnGiftCard.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                binding.btnGiftCard.setTextColor(requireContext().getColor(R.color.darkGray))
            }

            //Debit 클릭 시
            binding.btnDebitTopUpHistory.setOnClickListener {
                transferList = db.paymentDao().getTopUpByDebit(accountNumber, "top_up")

                val adapter = PointHistoryAdapter(requireContext(), transferList, "topUp")
                binding.rvTopUpHistory.adapter = adapter
                binding.rvTopUpHistory.layoutManager = GridLayoutManager(requireContext(), 1)

                binding.btnDebitTopUpHistory.setBackgroundResource(R.drawable.round8_gray_box)
                binding.btnDebitTopUpHistory.setTextColor(requireContext().getColor(R.color.white))

                binding.btnAllTopUpHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                binding.btnAllTopUpHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                binding.btnTransferTopUpHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                binding.btnTransferTopUpHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                binding.btnGiftCard.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                binding.btnGiftCard.setTextColor(requireContext().getColor(R.color.darkGray))
            }

            //Account 클릭 시
            binding.btnTransferTopUpHistory.setOnClickListener {
                transferList = db.paymentDao().getTopUpByAccount(accountNumber, "top_up")

                val adapter = PointHistoryAdapter(requireContext(), transferList, "topUp")
                binding.rvTopUpHistory.adapter = adapter
                binding.rvTopUpHistory.layoutManager = GridLayoutManager(requireContext(), 1)

                binding.btnTransferTopUpHistory.setBackgroundResource(R.drawable.round8_gray_box)
                binding.btnTransferTopUpHistory.setTextColor(requireContext().getColor(R.color.white))

                binding.btnAllTopUpHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                binding.btnAllTopUpHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                binding.btnDebitTopUpHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                binding.btnDebitTopUpHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                binding.btnGiftCard.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                binding.btnGiftCard.setTextColor(requireContext().getColor(R.color.darkGray))
            }

            //GiftCard 클릭 시
            binding.btnGiftCard.setOnClickListener {
                transferList = db.paymentDao().getTopUpByAccount(accountNumber, "giftcard")

                val adapter = PointHistoryAdapter(requireContext(), transferList, "topUp")
                binding.rvTopUpHistory.adapter = adapter
                binding.rvTopUpHistory.layoutManager = GridLayoutManager(requireContext(), 1)

                binding.btnGiftCard.setBackgroundResource(R.drawable.round8_gray_box)
                binding.btnGiftCard.setTextColor(requireContext().getColor(R.color.white))

                binding.btnAllTopUpHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                binding.btnAllTopUpHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                binding.btnDebitTopUpHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                binding.btnDebitTopUpHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                binding.btnTransferTopUpHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                binding.btnTransferTopUpHistory.setTextColor(requireContext().getColor(R.color.darkGray))

            }


        }

        return binding.root

    }


}