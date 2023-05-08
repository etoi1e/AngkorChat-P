package com.example.angkorchatproto.emojistore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityEmojiStoreBinding
import com.example.angkorchatproto.databinding.FragmentEmojiStoreMainBinding
import com.example.angkorchatproto.databinding.FragmentFriendsBinding
import com.example.angkorchatproto.emojistore.adapter.NewEmojiAdapter
import com.example.angkorchatproto.emojistore.adapter.PopularityEmojiAdapter
import com.example.angkorchatproto.emojistore.data.Data
import com.example.angkorchatproto.emojistore.viewmodel.EmojiStoreViewModel

class EmojiStoreMainFragment : Fragment() {
    lateinit var binding: FragmentEmojiStoreMainBinding
    private val activityViewModel: EmojiStoreViewModel? by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmojiStoreMainBinding.inflate(inflater, container, false)
        val menuItems = listOf("Menu 1", "Menu 2", "Menu 3")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, menuItems)
        binding.lvDrawer.adapter = adapter

        // 드로어 메뉴 아이템 클릭 이벤트 처리
        binding.lvDrawer.setOnItemClickListener { _, _, position, _ ->
            // TODO: 메뉴 아이템 클릭 이벤트 처리
        }

        binding.ivClose.setOnClickListener {
            requireActivity().finish()
        }
        binding.ivSearch.setOnClickListener {
            view?.findNavController()?.navigate(R.id.emojiStoreSearchFragment)
        }
        binding.ivMenu.setOnClickListener {
            if (binding.dl.isDrawerOpen(GravityCompat.END)) {
                binding.dl.closeDrawer(GravityCompat.END)
            } else {
                binding.dl.openDrawer(GravityCompat.END)
            }
        }

        binding.rvNewEmojiList.adapter = NewEmojiAdapter(requireContext(),
            activityViewModel?.testEmojiList,
            object: NewEmojiAdapter.OnNewEmojiListener {
                override fun onItemClicked(item: Data.EmojiInfo) {
                    view?.findNavController()?.navigate(R.id.emojiStoreDetailFragment, bundleOf("data" to item))
                }
            }
        )

        binding.rvPopularityLabelList.adapter = PopularityEmojiAdapter(requireContext(),
        "popularity",
            null,
            activityViewModel?.testEmojiList,
            object : PopularityEmojiAdapter.OnPopularityEmojiListener {
                override fun onItemClicked(item: Data.EmojiInfo?) {
                    view?.findNavController()?.navigate(R.id.emojiStoreDetailFragment, bundleOf("data" to item))
                }
            }
        )

        return binding.root
    }
}