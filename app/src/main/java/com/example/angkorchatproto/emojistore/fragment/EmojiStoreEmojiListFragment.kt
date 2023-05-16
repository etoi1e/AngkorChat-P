package com.example.angkorchatproto.emojistore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentEmojiStoreEmojiListBinding
import com.example.angkorchatproto.emojistore.adapter.ListEmojiAdapter
import com.example.angkorchatproto.emojistore.data.Data
import com.example.angkorchatproto.emojistore.viewmodel.EmojiStoreViewModel

class EmojiStoreEmojiListFragment : Fragment() {
    private val activityViewModel: EmojiStoreViewModel? by activityViewModels()
    private var data: ArrayList<Data.EmojiInfo> = ArrayList()
    lateinit var binding: FragmentEmojiStoreEmojiListBinding
    private lateinit var type: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments?.getString("type", "").toString()
        data = activityViewModel?.testEmojiList?.let { ArrayList(it) }!!
        if (type.isEmpty()) {
            view?.findNavController()?.popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmojiStoreEmojiListBinding.inflate(inflater, container, false)
        binding.tvTitle.text = type

        binding.ivBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.ivHome.setOnClickListener {
            view?.findNavController()?.navigate(R.id.emojiStoreMainFragment)
        }
        binding.ivClose.setOnClickListener {
            requireActivity().finish()
        }

        if (type == "New") {
            binding.clBanner.visibility = View.VISIBLE
        }

        if (type == "Gift Box") {
            binding.clGiftTab.visibility = View.VISIBLE
            emptyAdapter()

            binding.tbReceivedGift.setOnClickListener {
                binding.tbSendGift.isChecked = false
                emptyAdapter()
            }
            binding.tbSendGift.setOnClickListener {
                binding.clGiftTab.visibility = View.VISIBLE
                initAdapter()
                binding.tbReceivedGift.isChecked = false
            }
        } else {
            initAdapter()
        }
        return binding.root
    }

    private fun emptyAdapter() {
        binding.rvList.adapter = ListEmojiAdapter(requireContext(),
            "new",
            null,
            ArrayList(),
            object : ListEmojiAdapter.OnPopularityEmojiListener {
                override fun onItemClicked(item: Data.EmojiInfo?) {
                    view?.findNavController()?.navigate(R.id.emojiStoreDetailFragment, bundleOf("data" to item))
                }
            }
        )
    }

    private fun initAdapter() {
        binding.rvList.adapter = ListEmojiAdapter(requireContext(),
            when (type) {
                "My Emoticon" -> "search"
                "Gift Box"->"search"
                "Likes"->"like"
                "Purchase history"->"search"
                "New"->"new"
                else->"search"
            },
            null,
            data,
            object : ListEmojiAdapter.OnPopularityEmojiListener {
                override fun onItemClicked(item: Data.EmojiInfo?) {
                    view?.findNavController()?.navigate(R.id.emojiStoreDetailFragment, bundleOf("data" to item))
                }
            }
        )
    }
}