package com.example.angkorchatproto.chat.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.angkorchatproto.R
import com.example.angkorchatproto.UserVO
import com.example.angkorchatproto.emojistore.data.Data
import com.example.angkorchatproto.utils.FBdataBase

class SelectUsersAdapter(
    val context: Context,
    private var mSuggestList: ArrayList<UserVO>,
    private var mFriendList: ArrayList<String>,
    private var mListener: OnSelectUsersListener
) : RecyclerView.Adapter<SelectUsersAdapter.ViewHolder>() {
    interface OnSelectUsersListener {
        fun onItemClicked(user: UserVO)
    }

    private var selectItemPosition: Int? = null
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProfile: ImageView
        val tvName: TextView
        val tbSelect: ToggleButton

        init {
            imgProfile = itemView.findViewById(R.id.imgProfileFriendsList)
            tvName = itemView.findViewById(R.id.tvNameFriendsList)
            tbSelect = itemView.findViewById(R.id.tbSelectUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.select_user_list, null)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val profile = mSuggestList[position].profile
        val name = mSuggestList[position].name
        val phone = mSuggestList[position].phone

        holder.tvName.text = name
        if (selectItemPosition != null) {
            holder.tbSelect.isChecked = position == selectItemPosition
        }
        holder.tbSelect.isEnabled = false
        //프로필 사진 적용
        if (profile == "union") {//공식계정인 경우
            Glide.with(context)
                .load(R.drawable.top_logo)
                .into(holder.imgProfile)
        } else if (profile == "") {//사진 미등록의 경우
            Glide.with(context)
                .load(R.drawable.ic_profile_default_72)
                .into(holder.imgProfile)
        } else {//저장된 사진 가져오기
            Glide.with(context)
                .load(profile)
                .into(holder.imgProfile)
        }

        val removeDash = phone.toString().replace("-", "")
        val removeSpace = removeDash.replace(" ", "")

        if (mFriendList.contains(removeSpace)) {
            //친구 목록에 있는 경우
        }

        holder.itemView.setOnClickListener {
            selectItemPosition = position
            mListener.onItemClicked(mSuggestList[position])
            notifyDataSetChanged()
        }

        //뷰 재활용 막기(데이터꼬임방지)
        holder.setIsRecyclable(false);

    }

    override fun getItemCount(): Int {
        return mSuggestList.size
    }

    fun setItem(suggestList: ArrayList<UserVO>, friendList: ArrayList<String>) {
        mSuggestList = suggestList
        mFriendList = friendList
        notifyDataSetChanged()
    }
}