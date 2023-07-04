package com.example.angkorchatproto.pay.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityAddByContactBinding
import com.example.angkorchatproto.friends.viewmodel.AddByContactViewModel
import com.example.angkorchatproto.pay.room.AccountInfo
import java.text.DecimalFormat

class PointHistoryAdapter(
    val context: Context,
    val historyList: List<AccountInfo>,
    val type: String
) :
    RecyclerView.Adapter<PointHistoryAdapter.ViewHolder>() {

    // 리스너 커스텀
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    // 객체 저장 변수 선언
    var mOnItemClickListener: OnItemClickListener? = null

    //객체 전달 메서드
    fun setOnItemClickListener(OnItemClickListener: OnItemClickListener) {
        mOnItemClickListener = OnItemClickListener
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPointHistory: ImageView
        val tvPointHistoryName: TextView
        val tvPointHistoryTime: TextView
        val tvPointHistoryPoint: TextView

        init {
            ivPointHistory = itemView.findViewById(R.id.ivPointHistory)
            tvPointHistoryName = itemView.findViewById(R.id.tvPointHistoryName)
            tvPointHistoryTime = itemView.findViewById(R.id.tvPointHistoryTime)
            tvPointHistoryPoint = itemView.findViewById(R.id.tvPointHistoryPoint)

            itemView.setOnClickListener {
                mOnItemClickListener?.onItemClick(itemView, adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.point_history_list, null)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val transfer = historyList[position]
        val numberFormatter = DecimalFormat("###,###")

        if (type == "used") {
            //Used 접근경우
            if (transfer.bankName == "") {
                holder.tvPointHistoryName.text = transfer.depositor
            } else {
                holder.tvPointHistoryName.text = transfer.bankName
            }
            holder.tvPointHistoryTime.text = transfer.time

            holder.tvPointHistoryPoint.text = "${numberFormatter.format(transfer.point)} P"

        }
        if (type == "topUp") {
            //TopUp접근경우
            if (transfer.depositor == "") {
                holder.tvPointHistoryName.text = transfer.accountNumber
            } else {
                holder.tvPointHistoryName.text = transfer.depositor
            }

            holder.tvPointHistoryTime.text = transfer.time

            holder.tvPointHistoryPoint.text = "+ ${numberFormatter.format(transfer.point)}P"

        }
        if (type == "giftCard") {
            //GiftCard접근경우

            holder.ivPointHistory.visibility = View.GONE

            holder.tvPointHistoryName.text = "Angkor Giftcard"

            holder.tvPointHistoryTime.text = transfer.time

            if (transfer.content == "top_up") {
                holder.tvPointHistoryPoint.text = "+ ${numberFormatter.format(transfer.point)}P"
            }
            if (transfer.content == "transfer") {
                holder.tvPointHistoryPoint.text = "${numberFormatter.format(transfer.point)}P"
            }
        }

        if (type == "transfer" || (type == "received" && historyList[position].content == "used_point")) {
            holder.tvPointHistoryName.text = transfer.payTo

            holder.tvPointHistoryTime.text = transfer.time


            holder.tvPointHistoryPoint.text = "+ ${numberFormatter.format(transfer.point)}P"
            holder.tvPointHistoryPoint.setTextColor(context.getColor(R.color.blue))

            if (transfer.point.toString().contains("-")) {
                holder.tvPointHistoryPoint.text = "${numberFormatter.format(transfer.point)}P"
                holder.tvPointHistoryPoint.setTextColor(context.getColor(R.color.red))
            }


        }


    }

    override fun getItemCount(): Int {
        return historyList.size
    }


}