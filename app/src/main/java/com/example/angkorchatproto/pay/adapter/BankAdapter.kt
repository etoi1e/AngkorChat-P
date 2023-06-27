package com.example.angkorchatproto.pay.adapter

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.R
import java.time.LocalDate


class BankAdapter(
    val context: Context,
    val bankList: ArrayList<String>,
    val amount: Int,
    val accountNumber: String?
) :
    RecyclerView.Adapter<BankAdapter.ViewHolder>() {

    // 리스너 커스텀
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    // 객체 저장 변수 선언
    lateinit var mOnItemClickListener: OnItemClickListener

    //객체 전달 메서드
    fun setOnItemClickListener(OnItemClickListener: OnItemClickListener) {
        mOnItemClickListener = OnItemClickListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvBankListName: TextView
        val bankListLayout: ConstraintLayout

        init {
            tvBankListName = itemView.findViewById(R.id.tvBankListName)
            bankListLayout = itemView.findViewById(R.id.bankListLayout)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.bank_list, null)

        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvBankListName.text = bankList[position]

        val bundle = bundleOf(
            "bankName" to bankList[position],
            "topUpAmount" to amount.toString(),
            "topUpTime" to LocalDate.now().toString(),
            "accountNumber" to accountNumber
        )
        holder.itemView.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_topUpSelectBankFragment_to_checkBankFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return bankList.size
    }
}