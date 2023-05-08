/**
 * Package Name : com.example.angkorchatproto.emojistore.adapter
 * Class Name : NewEmojiAdapter
 * Description :
 * Created by de5ember on 2023/05/08.
 */

package com.example.angkorchatproto.emojistore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.databinding.ItemNewEmojiBinding
import com.example.angkorchatproto.emojistore.data.Data

class NewEmojiAdapter(
    context: Context,
    items: ArrayList<Data.EmojiInfo>?,
    listener: OnNewEmojiListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    interface OnNewEmojiListener {
        fun onItemClicked(item: Data.EmojiInfo)
    }

    private val mContext: Context?
    private var mItems: ArrayList<Data.EmojiInfo>? = null
    private var mListener: OnNewEmojiListener? = null

    init {
        mContext = context
        mItems = items
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RCViewHolder(
            ItemNewEmojiBinding.inflate(
                (mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = position
        holder as RCViewHolder
        mItems?.get(position)?.let { it.imageId?.let { it1 ->
            holder.binding?.imageView?.setImageResource(
                it1
            )
        }}
        holder.itemView.setOnClickListener {
            mItems?.get(position)?.let { it1 -> mListener?.onItemClicked(it1) }
        }
        holder.binding?.textView?.text = mItems?.get(position)?.title
    }

    override fun getItemCount(): Int {
        return mItems?.size!!
    }

    private fun getItem(position: Int): Data.EmojiInfo? {
        return mItems?.get(position)!!
    }

    private fun setItem(items: ArrayList<Data.EmojiInfo>) {
        mItems?.clear()
        mItems?.addAll(items)
        notifyDataSetChanged()
    }

    class RCViewHolder(b: ItemNewEmojiBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemNewEmojiBinding? = null

        init {
            binding = b
            val layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding?.root?.layoutParams = layoutParams
        }
    }
}