package com.example.angkorchatproto.more.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ItemMoreServiceBinding
import com.example.angkorchatproto.more.AngkorFriends
import com.example.angkorcheckprototype.AngkorCheckActivity
import com.example.angkoreats.AngkorEatsActivity
import com.example.angkorechoesprototype.AngkorEchoesActivity
import com.example.angkorgamesprototype.AngkorGamesActivity
import com.example.angkorplayprototype.AngkorPlayActivity
import com.example.angkorwebtoonprototype.AngkorWebtoonActivity
import com.example.bankangkorprototype.AngkorBankActivity

/**
 * Package Name : com.example.angkorchatproto.Chat.adapter
 * Class Name : ChatEmogeAdapter
 * Description :
 * Created by de5ember on 2023/05/03.
 */
class ServiceAdapter(
    context: Context?,
    menuName: ArrayList<String>?,
    items: ArrayList<Int>?,
    listener: OnServiceAdapterListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    interface OnServiceAdapterListener {
        fun onItemClicked(itemIdx: Int?, characterName: String?)
    }

    private val mContext: Context?
    private var mMenuName: ArrayList<String>? = null
    private var mItems: ArrayList<Int>? = null
    private var mListener: OnServiceAdapterListener? = null

    init {
        mContext = context
        mMenuName = menuName
        mItems = items
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RCViewHolder(
            ItemMoreServiceBinding.inflate(
                (mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = position
        holder as RCViewHolder
        holder.binding?.textView?.text = mMenuName?.get(position)
        holder.binding?.imageView?.let {
            if (mItems?.isNotEmpty() == true) {
                Glide.with(mContext!!)
                    .load(mItems?.get(position))
                    .placeholder(R.drawable.add_btn)
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into(it)
            }
        }
        holder.itemView.setOnClickListener {
            mListener?.onItemClicked(position, mMenuName?.get(position))


            if(mMenuName?.get(position) == "Friends"){
                //Friends
                val intent = Intent(mContext,AngkorFriends::class.java)
                mContext?.startActivity(intent)
            }

            if(mMenuName?.get(position) == "Eats"){
                //Eats
                mContext?.startActivity(Intent(mContext, AngkorEatsActivity::class.java))
            }
            if(mMenuName?.get(position) == "Webtoon"){
                //Webtoon
                mContext?.startActivity(Intent(mContext, AngkorWebtoonActivity::class.java))
            }
            if(mMenuName?.get(position) == "Games"){
                //Games
                mContext?.startActivity(Intent(mContext, AngkorGamesActivity::class.java))
            }
            if(mMenuName?.get(position) == "Play"){
                //Play
                mContext?.startActivity(Intent(mContext, AngkorPlayActivity::class.java))
            }
            if(mMenuName?.get(position) == "Check"){
                //Check
                mContext?.startActivity(Intent(mContext, AngkorCheckActivity::class.java))
            }
            if(mMenuName?.get(position) == "Echoes"){
                //Echoes
                mContext?.startActivity(Intent(mContext, AngkorEchoesActivity::class.java))

            }
            if(mMenuName?.get(position) == "Bank"){
                //Bank
                mContext?.startActivity(Intent(mContext, AngkorBankActivity::class.java))
            }


        }
    }

    override fun getItemCount(): Int {
        return mMenuName?.size!!
    }

    private fun getItem(position: Int): Int? {
        return mItems?.get(position)!!
    }

    private fun setItem(items: ArrayList<Int>) {
        mItems?.clear()
        mItems?.addAll(items)
        notifyDataSetChanged()
    }

    class RCViewHolder(b: ItemMoreServiceBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemMoreServiceBinding? = null

        init {
            binding = b
            val layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding?.root?.layoutParams = layoutParams
        }
    }
}