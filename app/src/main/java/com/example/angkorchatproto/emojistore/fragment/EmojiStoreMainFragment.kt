package com.example.angkorchatproto.emojistore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityEmojiStoreBinding
import com.example.angkorchatproto.databinding.FragmentEmojiStoreMainBinding
import com.example.angkorchatproto.databinding.FragmentFriendsBinding
import com.example.angkorchatproto.emojistore.adapter.NewEmojiAdapter
import com.example.angkorchatproto.emojistore.adapter.PopularityEmojiAdapter

class EmojiStoreMainFragment : Fragment() {
    lateinit var binding: FragmentEmojiStoreMainBinding
    private var testNewEmojiImageList = arrayListOf(R.drawable.gana1, R.drawable.haha1, R.drawable.nunu1)
    private var testNewEmojiTextList = arrayListOf("Emoticon","Emoticon","Emoticon")
    private var testPopularityEmojiImageList = arrayListOf(R.drawable.gana1, R.drawable.haha1, R.drawable.nunu1)
    private var testPopularityEmojiCoinList = arrayListOf(100, 200, 100)
    private var testPopularityEmojiCorpList = arrayListOf("Union","Union","Union")
    private var testPopularityEmojiTextList = arrayListOf("Emoticon","Emoticon","Emoticon")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmojiStoreMainBinding.inflate(inflater, container, false)
        binding.ivClose.setOnClickListener {
            requireActivity().finish()
        }

        binding.rvNewEmojiList.adapter = NewEmojiAdapter(requireContext(),
            testNewEmojiTextList,
            testNewEmojiImageList,
            object: NewEmojiAdapter.OnNewEmojiListener {
                override fun onItemClicked(item: Int?, itemIdx: Int?, characterName: String?) {
                    view?.findNavController()?.navigate(R.id.emojiStoreDetailFragment)
                }
            }
        )

        binding.rvPopularityLabelList.adapter = PopularityEmojiAdapter(requireContext(),
            testPopularityEmojiTextList,
            testPopularityEmojiCorpList,
            testPopularityEmojiCoinList,
            testPopularityEmojiImageList,
            object : PopularityEmojiAdapter.OnPopularityEmojiListener {
                override fun onItemClicked(item: Int?, itemIdx: Int?, characterName: String?) {
                    view?.findNavController()?.navigate(R.id.emojiStoreDetailFragment)
                }
            }
        )

        return binding.root
    }
}