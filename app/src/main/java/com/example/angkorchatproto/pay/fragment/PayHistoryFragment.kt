package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import android.util.Log
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
import com.example.angkorchatproto.databinding.FragmentPayHistoryBinding
import com.example.angkorchatproto.databinding.FragmentPayMainCodeBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel
import com.example.angkorchatproto.pay.adapter.PointHistoryAdapter
import com.example.angkorchatproto.pay.room.AppDatabase

class PayHistoryFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPayHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayHistoryBinding.inflate(inflater, container, false)
        val title = arguments?.getString("title")
        binding.tvTitle.text = title
        binding.ivClose.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()

        //Spinner
        val items = ArrayList<String>()
        items.add("Recent")
        items.add("High to Low")
        items.add("Low to High")



        val spAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            items
        )
        binding.spinner2.adapter = spAdapter

        val db = AppDatabase.getInstance(requireContext())

        if(db != null){

            val accountNumber = db.paymentDao().getAccountNumber(myNumber)

            //PayMent History
            if (title == "Payment Details") {

            }

            //Transfer details
            if (title == "Transfer details") {

                val transferHistoryList = db.paymentDao().getAllAmountHistory(accountNumber, "transfer",)

                val adapter = PointHistoryAdapter(requireContext(), transferHistoryList, "transfer")
                binding.rvTopUpHistory.adapter = adapter
                binding.rvTopUpHistory.layoutManager = GridLayoutManager(requireContext(), 1)


            }

        }




        return binding.root
    }
}