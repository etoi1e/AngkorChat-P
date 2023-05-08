package com.example.angkorchatproto.friends

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.angkorchatproto.profile.ProfileActivity
import com.example.angkorchatproto.R
import com.example.angkorchatproto.UserVO

class FriendsAdapter(val context: Context, val friendList: ArrayList<UserVO>) :
    RecyclerView.Adapter<FriendsAdapter.ViewHolder>(), Filterable {

    //필터 기능
    var initCk = true
    var initCn = 0
    var filteredBoard = ArrayList<UserVO>()
    var itemFilter = ItemFilter()

    // 리스너 커스텀
    interface  OnItemClickListener{
        fun  onItemClick(view : View, position: Int)
    }

    // 객체 저장 변수 선언
    lateinit var mOnItemClickListener : OnItemClickListener

    //객체 전달 메서드
    fun setOnItemClickListener(OnItemClickListener : OnItemClickListener){
        mOnItemClickListener = OnItemClickListener
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProfile: ImageView
        val tvName: TextView
        val tvEmail: TextView

        init {
            imgProfile = itemView.findViewById(R.id.imgProfileFriendsList)
            tvName = itemView.findViewById(R.id.tvNameFriendsList)
            tvEmail = itemView.findViewById(R.id.tvEmailFriendsList)

            itemView.setOnClickListener {
                val position = adapterPosition
                val filterList = filteredBoard[position]
//                if (position != RecyclerView.NO_POSITION){
//                    // 버그로 인해 -1이 아닐경우에
//                    mOnItemClickListener.onItemClick(itemView,position)
//                }

                val intent = Intent(context, ProfileActivity::class.java)

                intent.putExtra("name", filterList.name)
                intent.putExtra("number", filterList.phone)
                intent.putExtra("email", filterList.email)
                intent.putExtra("profile", filterList.profile)

                context.startActivity(intent)



            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.friends_list, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filteredList: UserVO = filteredBoard[position]

        initCk = false

        val profile = filteredList.profile
        val name = filteredList.name
        val email = filteredList.email



        holder.tvName.text = name
        holder.tvEmail.text = email

        if(profile == "union"){
            Glide.with(context)
                .load(R.drawable.top_logo)
                .into(holder.imgProfile)
        }else if(profile == ""){
            Glide.with(context)
                .load(R.drawable.ic_profile_default_72)
                .into(holder.imgProfile)
        }else{
            Glide.with(context)
                .load(profile)
                .into(holder.imgProfile)
        }


    }

    override fun getItemCount(): Int {
        if(initCk&&initCn==3){ filteredBoard.addAll(friendList)}
        initCn++
        return filteredBoard.size
    }

    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()
            initCk = false
            //검색이 필요없을 경우를 위해 원본 배열을 복제
            var filterList: ArrayList<UserVO> = ArrayList()
            //공백제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                filterList = friendList
            } //그 외의 경우 검색
            else {
                for (friend in friendList) {
                    if (friend.name!!.contains(filterString)
                        || friend.phone!!.contains(filterString)
                    ) {
                        filterList.add(friend)
                    }
                }
            }
            results.values = filterList
            results.count = filterList.size

            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            filteredBoard.clear()
            filteredBoard.addAll(filterResults.values as ArrayList<UserVO>)
            notifyDataSetChanged()
        }


    }



}







