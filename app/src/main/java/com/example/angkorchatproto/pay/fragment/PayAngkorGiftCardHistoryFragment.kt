package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.R
import com.example.angkorchatproto.databinding.FragmentPayGiftcardCodeBinding
import com.example.angkorchatproto.databinding.FragmentPayGiftcardHistoryBinding
import com.example.angkorchatproto.databinding.FragmentPayMainCodeBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel
import com.example.angkorchatproto.pay.adapter.PointHistoryAdapter
import com.example.angkorchatproto.pay.room.AccountInfo
import com.example.angkorchatproto.pay.room.AppDatabase
import java.text.DecimalFormat

class PayAngkorGiftCardHistoryFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPayGiftcardHistoryBinding
    private var mSelectTab = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayGiftcardHistoryBinding.inflate(inflater, container, false)

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()

        val db = AppDatabase.getInstance(requireContext())

        binding.ivClose.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        //Spinner
        val items = ArrayList<String>()
        items.add("Recent")
        items.add("High to Low")
        items.add("Low to High")


        //Recycler View

        val spAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            items
        )
        binding.spinnerGiftCard.adapter = spAdapter



        if (db != null) {
            getHistory(db, myNumber)

            val accountNumber = db.paymentDao().getAccountNumber(myNumber)
            //setPoint
            val numberFormatter = DecimalFormat("###,###")
            binding.tvPoint.text = numberFormatter.format(db.paymentDao().getPoint(accountNumber))

            //탭 옮기기
            if (binding.btnUsedGiftCard.isChecked) {
                binding.btnAcquiredGiftCard.isChecked = false
            }
            binding.btnUsedGiftCard.setOnClickListener {
                if (mSelectTab == 1) {
                    mSelectTab = 0
                    binding.btnUsedGiftCard.isChecked = true
                    binding.btnAcquiredGiftCard.isChecked = false

                } else {
                    binding.btnUsedGiftCard.isChecked = true
                    binding.btnAcquiredGiftCard.isChecked = false
                }
                getHistory(db, myNumber)
            }
            binding.btnAcquiredGiftCard.setOnClickListener {
                if (mSelectTab == 0) {
                    mSelectTab = 1
                    binding.btnUsedGiftCard.isChecked = false
                    binding.btnAcquiredGiftCard.isChecked = true
                } else {
                    binding.btnUsedGiftCard.isChecked = false
                    binding.btnAcquiredGiftCard.isChecked = true
                }
                getHistory(db, myNumber)
            }


        }



        return binding.root
    }

    fun getHistory(db: AppDatabase, myNumber: String) {

        val accountNumber = db.paymentDao().getAccountNumber(myNumber)

        if (mSelectTab == 1) {
            //사용내역
            val giftHistoryList =
                db.paymentDao().getPointHistoryByType(accountNumber, "top_up", "gift_card")
            val adapter = PointHistoryAdapter(requireContext(), giftHistoryList, "giftCard")

            binding.rvTopUpHistory.adapter = adapter
            binding.rvTopUpHistory.layoutManager = GridLayoutManager(requireContext(), 1)
        }

        if (mSelectTab == 0) {
            //충전내역
            val accountNumber = db.paymentDao().getAccountNumber(myNumber)
            val giftHistoryList =
                db.paymentDao().getPointHistoryByType(accountNumber, "transfer", "gift_card")
            val adapter = PointHistoryAdapter(requireContext(), giftHistoryList, "giftCard")

            binding.rvTopUpHistory.adapter = adapter
            binding.rvTopUpHistory.layoutManager = GridLayoutManager(requireContext(), 1)

        }
    }

}