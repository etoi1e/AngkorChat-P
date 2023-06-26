package com.example.angkorchatproto.pay.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentPayPointTopUpBinding
import com.example.angkorchatproto.databinding.FragmentPayTransferPasswordBinding
import com.example.angkorchatproto.databinding.FragmentSelectMethodBinding
import com.example.angkorchatproto.pay.room.AccountInfo
import com.example.angkorchatproto.pay.room.AppDatabase
import java.time.LocalDate
import java.time.LocalDateTime


class SelectMethodFragment : Fragment() {
    lateinit var binding: FragmentSelectMethodBinding
    private var mNavController: NavController? = null
    private var mNavHostFragment: NavHostFragment? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectMethodBinding.inflate(inflater, container, false)

        //db호출
        val db = AppDatabase.getInstance(requireContext())

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()

        //충전금액 불러오기
        var topUpAmount = requireArguments().getString("topUpAmount")

        //충전수단 저장 변수
        /** 0=debit 1=account */
        var selectMethod = 0

        //뒤로가기
        binding.ivCloseSelectMethod.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        //Debit 카드로 충전 선택 시
        binding.debitLayout.setOnClickListener {
            binding.debitLayout.setBackgroundResource(R.drawable.border_round12_yellow)
            binding.accountLayout.setBackgroundResource(R.drawable.border_round12_gray)
            selectMethod = 0
        }

        //Account 로 충전 선택 시
        binding.accountLayout.setOnClickListener {
            binding.accountLayout.setBackgroundResource(R.drawable.border_round12_yellow)
            binding.debitLayout.setBackgroundResource(R.drawable.border_round12_gray)
            selectMethod = 1
        }

        //Next 클릭 시
        binding.btnNextSelectMethod.setOnClickListener {
            if (selectMethod == 0) {
                if (db != null) {
                    val accountNumber = db.paymentDao().getAccountNumber(myNumber)
                    val amount = topUpAmount!!.toInt()
                    val topUpAmount =
                        AccountInfo(0, accountNumber, myNumber, "0000", 0, amount, "top_up")
                    db.paymentDao().insertAccount(topUpAmount)

                    Log.d("TAG-topup",topUpAmount.toString())

                    val bundle = bundleOf("topUpAmount" to amount.toString(),"topUpTime" to LocalDate.now().toString(),"accountNumber" to accountNumber)
                    Log.d("TAG-bundle",bundle.toString())
                    view?.findNavController()
                        ?.navigate(R.id.action_selectMethodFragment_to_topUpCompleteFragment,bundle)
                }

            }

            if (selectMethod == 1) {

            }
        }


        return binding.root
    }
}
