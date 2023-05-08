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

class NewEmojiAdapter(
    context: Context,
    characterName: ArrayList<String>?,
    items: ArrayList<Int>?,
    listener: OnNewEmojiListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    interface OnNewEmojiListener {
        fun onItemClicked(item: Int?, itemIdx: Int?, characterName: String?)
    }

    private val mContext: Context?
    private var mCharacterName: ArrayList<String>?
    private var mItems: ArrayList<Int>? = null
    private var mListener: OnNewEmojiListener? = null

    init {
        mContext = context
        mCharacterName = characterName
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
        mItems?.get(position)?.let { holder.binding?.imageView?.setImageResource(it) }
        holder.itemView.setOnClickListener {
            mListener?.onItemClicked(mItems?.get(position), position, mCharacterName?.get(position))
        }
        holder.binding?.textView?.text = mCharacterName?.get(position)
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