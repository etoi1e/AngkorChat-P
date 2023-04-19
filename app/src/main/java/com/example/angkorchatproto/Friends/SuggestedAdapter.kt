package com.example.angkorchatproto.Friends

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.angkorchatproto.R
import com.example.angkorchatproto.UserVO

class SuggestedAdapter(val context: Context, val suggestList: ArrayList<UserVO>) :
    RecyclerView.Adapter<SuggestedAdapter.ViewHolder>() {

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
        val imgProfile: ImageView
        val tvName: TextView
        val tvEmail: TextView
        val add: TextView

        init {
            imgProfile = itemView.findViewById(R.id.imgProfileFriendsList)
            tvName = itemView.findViewById(R.id.tvNameFriendsList)
            tvEmail = itemView.findViewById(R.id.tvEmailFriendsList)
            add = itemView.findViewById(R.id.tvAddbtnSuggest)

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // 버그로 인해 -1이 아닐경우에
                    mOnItemClickListener.onItemClick(itemView, position)
                }
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.suggest_list, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val profile = suggestList[position].profile
        val name = suggestList[position].name
        val email = suggestList[position].email


        holder.tvName.text = name
        holder.tvEmail.text = email

        if(profile == "union"){
            Glide.with(context)
                .load(R.drawable.top_logo)
                .into(holder.imgProfile)
        }else if(profile == ""){
            Glide.with(context)
                .load(R.drawable.profile)
                .into(holder.imgProfile)
        }else{
            Glide.with(context)
                .load(profile)
                .into(holder.imgProfile)
        }
    }

    override fun getItemCount(): Int {
        return suggestList.size
    }
}