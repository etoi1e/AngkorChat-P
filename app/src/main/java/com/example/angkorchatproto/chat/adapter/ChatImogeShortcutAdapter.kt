package com.example.angkorchatproto.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.angkorchatproto.databinding.ItemShortcutEmojiBinding

/**
 * Package Name : com.example.angkorchatproto.Chat.adapter
 * Class Name : ChatEmogeAdapter
 * Description :
 * Created by de5ember on 2023/05/03.
 */
class ChatImogeShortcutAdapter(
    context: Context?,
    items: ArrayList<Int>?,
    listener: OnChatImogeShortcutAdapterListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    interface OnChatImogeShortcutAdapterListener {
        fun onItemClicked(item: Int)
    }

    private val mContext: Context?
    private var mItems: ArrayList<Int>? = null
    private var mListener: OnChatImogeShortcutAdapterListener? = null

    init {
        mContext = context
        mItems = items
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RCViewHolder(
            ItemShortcutEmojiBinding.inflate(
                (mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = position
        holder as RCViewHolder
        holder.binding?.imageView?.let {
            Glide.with(mContext!!)
                .load(mItems?.get(position))
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(it)
        }
        holder.itemView.setOnClickListener {
            mListener?.onItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return mItems?.size!!
    }

    private fun getItem(position: Int): Int? {
        return mItems?.get(position)!!
    }

    private fun setItem(items: ArrayList<Int>) {
        mItems?.clear()
        mItems?.addAll(items)
        notifyDataSetChanged()
    }

    class RCViewHolder(b: ItemShortcutEmojiBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemShortcutEmojiBinding? = null

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