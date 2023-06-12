package com.example.angkorchatproto.more.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ItemMoreServiceBinding

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