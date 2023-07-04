package com.example.angkorchatproto.pay.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentTopUpHistoryBinding
import com.example.angkorchatproto.pay.HistorySearchActivity
import com.example.angkorchatproto.pay.adapter.PointHistoryAdapter
import com.example.angkorchatproto.pay.room.AppDatabase
import com.orhanobut.dialogplus.DialogPlus


class TopUpHistoryFragment : Fragment() {
    lateinit var binding: FragmentTopUpHistoryBinding

    private var mNavController: NavController? = null
    private var mNavHostFragment: NavHostFragment? = null

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


            val spAdapter = ArrayAdapter(
                requireContext(),
                androidx.transition.R.layout.support_simple_spinner_dropdown_item,
                items
            )
            binding.spHistory.adapter = spAdapter

            mNavHostFragment =
                requireActivity().supportFragmentManager.findFragmentById(R.id.pay_container) as NavHostFragment
            mNavController = mNavHostFragment?.navController


            binding.ivSearchTopUp.setOnClickListener {

            }

            //전체 불러오기의 경우
            var transferList = db.paymentDao().getAllPointHistory(accountNumber)
            var adapter = PointHistoryAdapter(requireContext(), transferList, "topUp")
            var type = "Top up"

            if (transferList != null) {
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

                adapter = PointHistoryAdapter(requireContext(), transferList, "topUp")
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

                adapter.setOnItemClickListener(object : PointHistoryAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {

                        var bundle = bundleOf("type" to type, "transferNumber" to transferList[position].transferNumber)

                        mNavController?.navigate(
                            R.id.action_payPointFragment_to_payDetailFragment,
                            bundle
                        )

                    }

                })

            }

            //Debit 클릭 시
            binding.btnDebitTopUpHistory.setOnClickListener {
                transferList =
                    db.paymentDao().getPointHistoryByType(accountNumber, "top_up", "debit")

                adapter = PointHistoryAdapter(requireContext(), transferList, "topUp")
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

                adapter.setOnItemClickListener(object : PointHistoryAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {

                        var bundle = bundleOf("type" to type, "transferNumber" to transferList[position].transferNumber)

                        mNavController?.navigate(
                            R.id.action_payPointFragment_to_payDetailFragment,
                            bundle
                        )

                    }

                })

            }

            //Account 클릭 시
            binding.btnTransferTopUpHistory.setOnClickListener {
                transferList =
                    db.paymentDao().getPointHistoryByType(accountNumber, "top_up", "account")

                adapter = PointHistoryAdapter(requireContext(), transferList, "topUp")
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

                adapter.setOnItemClickListener(object : PointHistoryAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {

                        var bundle = bundleOf("type" to type, "transferNumber" to transferList[position].transferNumber)

                        mNavController?.navigate(
                            R.id.action_payPointFragment_to_payDetailFragment,
                            bundle
                        )

                    }

                })

            }

            //GiftCard 클릭 시
            binding.btnGiftCard.setOnClickListener {
                transferList =
                    db.paymentDao().getPointHistoryByType(accountNumber, "top_up", "gift_card")

                adapter = PointHistoryAdapter(requireContext(), transferList, "topUp")
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

                adapter.setOnItemClickListener(object : PointHistoryAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {

                        var bundle = bundleOf("type" to type, "transferNumber" to transferList[position].transferNumber)

                        mNavController?.navigate(
                            R.id.action_payPointFragment_to_payDetailFragment,
                            bundle
                        )

                    }

                })

            }

            adapter.setOnItemClickListener(object : PointHistoryAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {

                    var bundle = bundleOf("type" to type, "transferNumber" to transferList[position].transferNumber)

                    mNavController?.navigate(
                        R.id.action_payPointFragment_to_payDetailFragment,
                        bundle
                    )

                }

            })


        }

        return binding.root

    }


}