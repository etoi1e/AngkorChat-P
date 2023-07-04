/**
 * Package Name : com.example.angkorchatproto.emojistore.adapter
 * Class Name : DrawerEmojiAdapter
 * Description :
 * Created by de5ember on 2023/05/09.
 */

package com.example.angkorchatproto.emojistore.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ItemDrawerBinding

class DrawerEmojiAdapter(
    context: Context,
    items: ArrayList<String>,
    listener: OnDrawerEmojiListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    interface OnDrawerEmojiListener {
        fun onItemClicked(item: String)
    }

    private val mContext: Context?
    private var mItems: ArrayList<String>?
    private var mListener: OnDrawerEmojiListener? = null

    init {
        mContext = context
        mItems = items
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RCViewHolder(
            ItemDrawerBinding.inflate(
                (mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = position
        holder as RCViewHolder
        holder.binding?.tvDrawerMenu?.text = mItems?.get(position)
        holder.itemView.setOnClickListener {
            mListener?.onItemClicked(mItems?.get(position)!!)
        }
        if(mItems?.get(position) == "Payment History"){
            holder.binding?.tvDrawerMenu?.setTextColor(mContext!!.getColor(R.color.colorLightGray))
        }else{
            holder.binding?.tvDrawerMenu?.textColors
        }
    }

    override fun getItemCount(): Int {
        return mItems?.size!!
    }

    private fun getItem(position: Int): String {
        return mItems?.get(position)!!
    }

    class RCViewHolder(b: ItemDrawerBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemDrawerBinding? = null

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