/**
 * Package Name : com.example.angkorchatproto.emojistore.adapter
 * Class Name : NewEmojiAdapter
 * Description :
 * Created by de5ember on 2023/05/08.
 */

package com.example.angkorchatproto.emojistore.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.databinding.ItemNewEmojiBinding
import com.example.angkorchatproto.databinding.ItemPopularityEmojiBinding

class PopularityEmojiAdapter(
    context: Context,
    characterName: ArrayList<String>?,
    characterMakeCorp: ArrayList<String>?,
    coin: ArrayList<Int>?,
    items: ArrayList<Int>?,
    listener: OnPopularityEmojiListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    interface OnPopularityEmojiListener {
        fun onItemClicked(item: Int?, itemIdx: Int?, characterName: String?)
    }

    private val mContext: Context?
    private var mCharacterName: ArrayList<String>?
    private var mCharacterMakeCorp: ArrayList<String>?
    private var mCoin: ArrayList<Int>?
    private var mItems: ArrayList<Int>? = null
    private var mListener: OnPopularityEmojiListener? = null

    init {
        mContext = context
        mCharacterName = characterName
        mCharacterMakeCorp = characterMakeCorp
        mCoin = coin
        mItems = items
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RCViewHolder(
            ItemPopularityEmojiBinding.inflate(
                (mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = position
        holder as RCViewHolder
        holder.binding?.tvEmojiNum?.text = (position+1).toString()
        mItems?.get(position)?.let { holder.binding?.imageView?.setImageResource(it) }
        holder.itemView.setOnClickListener {
            mListener?.onItemClicked(mItems?.get(position), position, mCharacterName?.get(position))
        }
        holder.binding?.tvEmojiName?.text = mCharacterName?.get(position)
        holder.binding?.tvMakeEmoji?.text = mCharacterMakeCorp?.get(position)
        holder.binding?.tvCoin?.text = mCoin?.get(position).toString()
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

    class RCViewHolder(b: ItemPopularityEmojiBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemPopularityEmojiBinding? = null

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