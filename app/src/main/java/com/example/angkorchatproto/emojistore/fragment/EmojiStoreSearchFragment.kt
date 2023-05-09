package com.example.angkorchatproto.emojistore.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentEmojiStoreMainBinding
import com.example.angkorchatproto.databinding.FragmentEmojiStoreSearchBinding
import com.example.angkorchatproto.emojistore.adapter.ListEmojiAdapter
import com.example.angkorchatproto.emojistore.data.Data
import com.example.angkorchatproto.emojistore.viewmodel.EmojiStoreViewModel

class EmojiStoreSearchFragment : Fragment() {
    lateinit var binding: FragmentEmojiStoreSearchBinding
    private val activityViewModel: EmojiStoreViewModel? by activityViewModels()
    private var totalData: ArrayList<Data.EmojiInfo>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        totalData = activityViewModel?.testEmojiList
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmojiStoreSearchBinding.inflate(inflater, container, false)
        binding.rvSearchLabelList.adapter = ListEmojiAdapter(requireContext(),
            "search",
            null,
            activityViewModel?.testEmojiList,
            object : ListEmojiAdapter.OnPopularityEmojiListener {
                override fun onItemClicked(item: Data.EmojiInfo?) {
                    view?.findNavController()?.navigate(R.id.emojiStoreDetailFragment, bundleOf("data" to item))
                }
            }
        )
        binding.button.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("search", charSequence.toString())

                if (charSequence.toString().isNotEmpty()) {
                    binding.tvRecommendLabel.visibility = View.GONE
                    binding.ibDelete.visibility = View.VISIBLE
                } else {
                    binding.tvRecommendLabel.visibility = View.VISIBLE
                    binding.ibDelete.visibility = View.GONE
                }

                val result = totalData?.map {
                    if (it.title?.contains(charSequence.toString()) == true) {
                        it
                    } else {
                        Data.EmojiInfo(null, null, null, null)
                    }
                }?.filter {
                    it.title != null
                } as ArrayList
                Log.d("search", result.toString())
                binding.rvSearchLabelList.adapter = ListEmojiAdapter(requireContext(),
                    "search",
                    charSequence.toString(),
                    result,
                    object : ListEmojiAdapter.OnPopularityEmojiListener {
                        override fun onItemClicked(item: Data.EmojiInfo?) {
                            view?.findNavController()?.navigate(R.id.emojiStoreDetailFragment, bundleOf("data" to item))
                        }
                    }
                )
            }

            override fun afterTextChanged(charSequence: Editable?) {
            }
        })
        binding.ibDelete.setOnClickListener {
            binding.etSearch.text.clear()
        }

        return binding.root
    }
}