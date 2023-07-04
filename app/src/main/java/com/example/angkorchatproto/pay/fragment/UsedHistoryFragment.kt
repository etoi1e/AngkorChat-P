package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentUsedHistoryBinding
import com.example.angkorchatproto.pay.adapter.PointHistoryAdapter
import com.example.angkorchatproto.pay.room.AppDatabase


class UsedHistoryFragment : Fragment() {

    lateinit var binding: FragmentUsedHistoryBinding

    private var mNavController: NavController? = null
    private var mNavHostFragment: NavHostFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUsedHistoryBinding.inflate(inflater, container, false)
        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()


        val db = AppDatabase.getInstance(requireContext())

        if (db != null) {

            val accountNumber = db.paymentDao().getAccountNumber(myNumber)

            val type = "Used"
            val items = ArrayList<String>()
            items.add("Recent")

            val spAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
            binding.spHistory.adapter = spAdapter

            mNavHostFragment =
                requireActivity().supportFragmentManager.findFragmentById(R.id.pay_container) as NavHostFragment
            mNavController = mNavHostFragment?.navController


            var transferList = db.paymentDao().getAllByContent(accountNumber, "used_point")
            if (transferList != null) {
                var adapter = PointHistoryAdapter(requireContext(), transferList, "used")
                binding.rvUsedHistory.adapter = adapter
                binding.rvUsedHistory.layoutManager = GridLayoutManager(requireContext(), 1)


                binding.btnAllUsedHistory.setOnClickListener {
                    transferList = db.paymentDao().getAllByContent(accountNumber, "used_point")

                    adapter = PointHistoryAdapter(requireContext(), transferList, "used")
                    binding.rvUsedHistory.adapter = adapter
                    binding.rvUsedHistory.layoutManager = GridLayoutManager(requireContext(), 1)

                    binding.btnAllUsedHistory.setBackgroundResource(R.drawable.round8_gray_box)
                    binding.btnAllUsedHistory.setTextColor(requireContext().getColor(R.color.white))

                    binding.btnPaymentUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnPaymentUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnTransferUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnTransferUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnWithdrawal.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnWithdrawal.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnEtcUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnEtcUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                }

                binding.btnTransferUsedHistory.setOnClickListener {
                    transferList = db.paymentDao()
                        .getPointHistoryByType(accountNumber, "used_point", "transfer")

                    val adapter = PointHistoryAdapter(requireContext(), transferList, "used")
                    binding.rvUsedHistory.adapter = adapter
                    binding.rvUsedHistory.layoutManager = GridLayoutManager(requireContext(), 1)

                    binding.btnTransferUsedHistory.setBackgroundResource(R.drawable.round8_gray_box)
                    binding.btnTransferUsedHistory.setTextColor(requireContext().getColor(R.color.white))

                    binding.btnPaymentUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnPaymentUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnAllUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnAllUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnWithdrawal.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnWithdrawal.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnEtcUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnEtcUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                }


                binding.btnPaymentUsedHistory.setOnClickListener {
                    transferList =
                        db.paymentDao().getPointHistoryByType(accountNumber, "used_point", "pay")

                    val adapter = PointHistoryAdapter(requireContext(), transferList, "used")
                    binding.rvUsedHistory.adapter = adapter
                    binding.rvUsedHistory.layoutManager = GridLayoutManager(requireContext(), 1)

                    binding.btnPaymentUsedHistory.setBackgroundResource(R.drawable.round8_gray_box)
                    binding.btnPaymentUsedHistory.setTextColor(requireContext().getColor(R.color.white))

                    binding.btnAllUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnAllUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnTransferUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnTransferUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnWithdrawal.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnWithdrawal.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnEtcUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnEtcUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                }

                binding.btnWithdrawal.setOnClickListener {
                    transferList = db.paymentDao()
                        .getPointHistoryByType(accountNumber, "used_point", "withdraw")

                    val adapter = PointHistoryAdapter(requireContext(), transferList, "used")
                    binding.rvUsedHistory.adapter = adapter
                    binding.rvUsedHistory.layoutManager = GridLayoutManager(requireContext(), 1)

                    binding.btnWithdrawal.setBackgroundResource(R.drawable.round8_gray_box)
                    binding.btnWithdrawal.setTextColor(requireContext().getColor(R.color.white))

                    binding.btnAllUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnAllUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnTransferUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnTransferUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnPaymentUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnPaymentUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnEtcUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnEtcUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                }

                binding.btnEtcUsedHistory.setOnClickListener {
                    transferList =
                        db.paymentDao().getPointHistoryByType(accountNumber, "used_point", "etc")

                    val adapter = PointHistoryAdapter(requireContext(), transferList, "used")
                    binding.rvUsedHistory.adapter = adapter
                    binding.rvUsedHistory.layoutManager = GridLayoutManager(requireContext(), 1)

                    binding.btnEtcUsedHistory.setBackgroundResource(R.drawable.round8_gray_box)
                    binding.btnEtcUsedHistory.setTextColor(requireContext().getColor(R.color.white))

                    binding.btnAllUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnAllUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnTransferUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnTransferUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnPaymentUsedHistory.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnPaymentUsedHistory.setTextColor(requireContext().getColor(R.color.darkGray))
                    binding.btnWithdrawal.setBackgroundResource(R.drawable.round8_gray_stroke_box)
                    binding.btnWithdrawal.setTextColor(requireContext().getColor(R.color.darkGray))
                }


                adapter.setOnItemClickListener(object : PointHistoryAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {

                        var bundle = bundleOf(
                            "type" to type,
                            "transferNumber" to transferList[position].transferNumber
                        )

                        mNavController?.navigate(
                            R.id.action_payPointFragment_to_payDetailFragment,
                            bundle
                        )

                    }

                })

            }
        }






        return binding.root
    }

}