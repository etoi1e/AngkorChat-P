package com.example.angkorchatproto.Chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ItemImogeBinding

/**
 * Package Name : com.example.angkorchatproto.Chat.adapter
 * Class Name : ChatEmogeAdapter
 * Description :
 * Created by de5ember on 2023/05/03.
 */
class ChatImogeAdapter(
    context: Context?,
    characterName: String?,
    items: ArrayList<Int>?,
    listener: OnChatImogeAdapterListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    interface OnChatImogeAdapterListener {
        fun onItemClicked(item: Int?, itemIdx: Int?, characterName: String?)
    }

    private val mContext: Context?
    private var mCharacterName: String?
    private var mItems: ArrayList<Int>? = null
    private var mListener: OnChatImogeAdapterListener? = null

    init {
        mContext = context
        mCharacterName = characterName
        mItems = items
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RCViewHolder(
            ItemImogeBinding.inflate(
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
            mListener?.onItemClicked(mItems?.get(position), position, mCharacterName)
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

    class RCViewHolder(b: ItemImogeBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemImogeBinding? = null

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