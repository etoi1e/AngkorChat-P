/**
 * Package Name : com.example.angkorchatproto.emojistore.adapter
 * Class Name : NewEmojiAdapter
 * Description :
 * Created by de5ember on 2023/05/08.
 */

package com.example.angkorchatproto.settings.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.angkorchatproto.databinding.ItemSettingsMainBinding
import com.example.angkorchatproto.settings.data.Data.SettingsItem

class SettingsMainAdapter(
    context: Context,
    items: ArrayList<SettingsItem>?,
    listener: OnSettingsItemListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    interface OnSettingsItemListener {
        fun onItemClicked(item: SettingsItem)
    }

    private val mContext: Context?
    private var mItems: ArrayList<SettingsItem>? = null
    private var mListener: OnSettingsItemListener? = null

    init {
        mContext = context
        mItems = items
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RCViewHolder(
            ItemSettingsMainBinding.inflate(
                (mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = position
        holder as RCViewHolder

        holder.itemView.setOnClickListener {
            mItems?.get(position)?.let { it1 -> mListener?.onItemClicked(it1) }
        }

        if (position == 0) {
            holder.binding?.tvVersion?.visibility = View.VISIBLE
        }

        mItems?.get(position)?.let {
            it.imageId?.let { it1 ->
                holder.binding?.ivIcon?.setImageResource(
                    it1
                )
            }
        }

        holder.binding?.tvSettingsName?.text = mItems?.get(position)?.title
    }

    override fun getItemCount(): Int {
        return mItems?.size!!
    }

    private fun getItem(position: Int): SettingsItem? {
        return mItems?.get(position)!!
    }

    private fun setItem(items: ArrayList<SettingsItem>) {
        mItems?.clear()
        mItems?.addAll(items)
        notifyDataSetChanged()
    }

    class RCViewHolder(b: ItemSettingsMainBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemSettingsMainBinding? = null

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