package com.example.angkorchatproto.pay.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.R
import com.example.angkorchatproto.pay.room.AccountInfo

class PointHistoryAdapter(val context: Context, val historyList: List<AccountInfo>,val type:String) :
    RecyclerView.Adapter<PointHistoryAdapter.ViewHolder>() {

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
        val ivPointHistory: ImageView
        val tvPointHistoryName: TextView
        val tvPointHistoryTime: TextView
        val tvPointHistoryPoint: TextView

        init {
            ivPointHistory = itemView.findViewById(R.id.ivPointHistory)
            tvPointHistoryName = itemView.findViewById(R.id.tvPointHistoryName)
            tvPointHistoryTime = itemView.findViewById(R.id.tvPointHistoryTime)
            tvPointHistoryPoint = itemView.findViewById(R.id.tvPointHistoryPoint)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.point_history_list, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val transfer = historyList[position]

        if(type == "used"){
            //Used 접근경우
            if (transfer.bankName == "") {
                holder.tvPointHistoryName.text = transfer.depositor
            }else{
                holder.tvPointHistoryName.text = transfer.bankName
            }
            holder.tvPointHistoryTime.text = transfer.time
            holder.tvPointHistoryPoint.text = "${transfer.point}P"
        }else{
            //TopUp접근경우
            if (transfer.depositor == "") {
                holder.tvPointHistoryName.text = transfer.accountNumber
            }else{
                holder.tvPointHistoryName.text = transfer.depositor
            }
            holder.tvPointHistoryTime.text = transfer.time
            holder.tvPointHistoryPoint.text = "${transfer.point}P"
        }



    }

    override fun getItemCount(): Int {
        return historyList.size
    }


}