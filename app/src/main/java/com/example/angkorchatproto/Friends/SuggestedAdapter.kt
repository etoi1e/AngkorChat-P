package com.example.angkorchatproto.Friends

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.angkorchatproto.R
import com.example.angkorchatproto.UserVO
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.DatabaseReference

class SuggestedAdapter(
    val context: Context,
    val suggestList: ArrayList<UserVO>,
    val userNum: String,
    val sendList: ArrayList<String>
) :
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
                    //mOnItemClickListener.onItemClick(itemView, position)
                }
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.suggest_list, null)

        Log.d("TAG-실행순서", "어댑터 onCreateViewHolder")

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("TAG-실행순서", "어댑터 onBindViewHolder")

        val profile = suggestList[position].profile
        val name = suggestList[position].name
        val email = suggestList[position].email
        val phone = suggestList[position].phone

        holder.tvName.text = name
        holder.tvEmail.text = email

        val friendRef = FBdataBase.getFriendRef()

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



        //Log.d("TAG-유저친구목록", sendList.toString())
        //Log.d("TAG-유저추천목록", suggestList.toString())



        if (sendList.contains(phone)) {
            //친구 목록에 있는 경우
            holder.add.setBackgroundResource(R.drawable.added_btn)
            holder.add.text = "Added"
            holder.add.tag = "false"

//            holder.add.setOnClickListener {
//                if (holder.add.tag == "false") {//친구 삭제할 때
//                    holder.add.setBackgroundResource(R.drawable.add_btn)
//                    holder.add.text = "Add"
//                    holder.add.tag = "true"
//
//                    //친구 목록에서 제거
//                    friendRef.child(userNum).child(phone.toString()).removeValue()
//
//                }
//            }

        } else {
            //친구 목록에 없는 경우
            holder.add.setBackgroundResource(R.drawable.add_btn)
            holder.add.text = "Add"
            holder.add.tag = "true"

//            holder.add.setOnClickListener {
//                holder.add.setBackgroundResource(R.drawable.added_btn)
//                holder.add.text = "Added"
//                holder.add.tag = "false"
//
//                val removeDash = phone.toString().replace("-", "")
//                val removeSpace = removeDash.replace(" ", "")
//
//                friendRef.child(userNum).child(removeSpace!!)
//                    .setValue(UserVO(name, email, profile, removeSpace))
//            }

        }

        //뷰 재활용 막기(데이터꼬임방지)
        holder.setIsRecyclable(false);


    }


    override fun getItemCount(): Int {
        return suggestList.size
    }
}