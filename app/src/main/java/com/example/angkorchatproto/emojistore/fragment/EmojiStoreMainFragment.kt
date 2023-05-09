package com.example.angkorchatproto.emojistore.fragment

import android.os.Bundle
import android.util.Log
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
import com.example.angkorchatproto.emojistore.adapter.DrawerEmojiAdapter
import com.example.angkorchatproto.emojistore.adapter.NewEmojiAdapter
import com.example.angkorchatproto.emojistore.adapter.ListEmojiAdapter
import com.example.angkorchatproto.emojistore.data.Data
import com.example.angkorchatproto.emojistore.viewmodel.EmojiStoreViewModel

class EmojiStoreMainFragment : Fragment() {
    lateinit var binding: FragmentEmojiStoreMainBinding
    private var data: ArrayList<Data.EmojiInfo> = ArrayList()
    private val activityViewModel: EmojiStoreViewModel? by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = activityViewModel?.testEmojiList?.let { ArrayList(it) }!!
    }

    override fun onResume() {
        super.onResume()
        Log.d("EmojiStoreMainFragment", "onResume")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("EmojiStoreMainFragment", "onCreateView")
        binding = FragmentEmojiStoreMainBinding.inflate(inflater, container, false)
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

        binding.rvDrawer.adapter = DrawerEmojiAdapter(requireContext(),
            arrayListOf("My Emoticon", "Gift Box", "Likes", "Purchase history"),
            object: DrawerEmojiAdapter.OnDrawerEmojiListener {
                override fun onItemClicked(item: String) {
                    view?.findNavController()?.navigate(R.id.emojiStoreEmojiListFragment, bundleOf("type" to item))
                }
        })

        binding.btnSeeAll.setOnClickListener {
            view?.findNavController()?.navigate(R.id.emojiStoreEmojiListFragment, bundleOf("type" to "New"))
        }

        binding.rvNewEmojiList.adapter = NewEmojiAdapter(requireContext(),
            data,
            object: NewEmojiAdapter.OnNewEmojiListener {
                override fun onItemClicked(item: Data.EmojiInfo) {
                    view?.findNavController()?.navigate(R.id.emojiStoreDetailFragment, bundleOf("data" to item))
                }
            }
        )

        binding.rvPopularityLabelList.adapter = ListEmojiAdapter(requireContext(),
        "popularity",
            null,
            data,
            object : ListEmojiAdapter.OnPopularityEmojiListener {
                override fun onItemClicked(item: Data.EmojiInfo?) {
                    view?.findNavController()?.navigate(R.id.emojiStoreDetailFragment, bundleOf("data" to item))
                }
            }
        )

        return binding.root
    }
}