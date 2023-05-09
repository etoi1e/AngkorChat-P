/**
 * Package Name : com.example.angkorchatproto.emojistore.adapter
 * Class Name : NewEmojiAdapter
 * Description :
 * Created by de5ember on 2023/05/08.
 */

package com.example.angkorchatproto.emojistore.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ItemListEmojiBinding
import com.example.angkorchatproto.emojistore.data.Data

class ListEmojiAdapter(
    context: Context,
    mode: String?,
    searchWord: String?,
    items: ArrayList<Data.EmojiInfo>?,
    listener: OnPopularityEmojiListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    interface OnPopularityEmojiListener {
        fun onItemClicked(item: Data.EmojiInfo?)
    }

    private val mContext: Context?
    private val mMode: String?
    private val mSearchWord: String?
    private var mItems: ArrayList<Data.EmojiInfo>? = null
    private var mListener: OnPopularityEmojiListener? = null

    init {
        mContext = context
        mMode = mode
        mSearchWord = searchWord
        mItems = items
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RCViewHolder(
            ItemListEmojiBinding.inflate(
                (mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = position
        holder as RCViewHolder
        mItems?.get(position)?.let {
            it.imageId?.let { it1 ->
                holder.binding?.imageView?.setImageResource(
                    it1
                )
            }
        }
        holder.itemView.setOnClickListener {
            mListener?.onItemClicked(mItems?.get(position))
        }
        holder.binding?.tvMakeEmoji?.text = mItems?.get(position)?.make
        if (mMode == "search") {
            holder.binding?.ivCoin?.visibility = View.GONE
            holder.binding?.tvCoin?.visibility = View.GONE
            holder.binding?.tvEmojiNum?.visibility = View.GONE
            if (mSearchWord != null) {
                val spannable = SpannableStringBuilder(mItems?.get(position)?.title)
                val color = mContext?.getColor(R.color.mainYellow)!!
                val start = mItems?.get(position)?.title?.indexOf(mSearchWord)
                if (start != null && start != -1) {
                    spannable.setSpan(
                        ForegroundColorSpan(color),
                        start,
                        start + mSearchWord.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    holder.binding?.tvEmojiName?.text = spannable
                } else {
                    holder.binding?.tvEmojiName?.text = mItems?.get(position)?.title
                }
            } else {
                holder.binding?.tvEmojiName?.text = mItems?.get(position)?.title
            }
        } else if (mMode == "new") {
            holder.binding?.ivCoin?.visibility = View.VISIBLE
            holder.binding?.tvCoin?.visibility = View.VISIBLE
            holder.binding?.tvEmojiNum?.visibility = View.GONE
            holder.binding?.tvEmojiNum?.text = (position + 1).toString()
            holder.binding?.tvCoin?.text = mItems?.get(position)?.coin.toString()
            holder.binding?.tvEmojiName?.text = mItems?.get(position)?.title
        } else if (mMode == "like") {
            holder.binding?.ivCoin?.visibility = View.GONE
            holder.binding?.tvCoin?.visibility = View.GONE
            holder.binding?.tvEmojiNum?.visibility = View.GONE
            holder.binding?.ivHeart?.visibility = View.VISIBLE
            holder.binding?.ivHeart?.isChecked = true
            holder.binding?.tvEmojiName?.text = mItems?.get(position)?.title
            holder.binding?.ivHeart?.setOnClickListener {
                if (holder.binding?.ivHeart?.isChecked == false) {
                    mItems?.removeAt(position)
                    notifyDataSetChanged()
                }
            }
        } else {
            holder.binding?.ivCoin?.visibility = View.VISIBLE
            holder.binding?.tvCoin?.visibility = View.VISIBLE
            holder.binding?.tvEmojiNum?.visibility = View.VISIBLE
            holder.binding?.tvEmojiNum?.text = (position+1).toString()
            holder.binding?.tvCoin?.text = mItems?.get(position)?.coin.toString()
            holder.binding?.tvEmojiName?.text = mItems?.get(position)?.title
        }
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

    class RCViewHolder(b: ItemListEmojiBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemListEmojiBinding? = null

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