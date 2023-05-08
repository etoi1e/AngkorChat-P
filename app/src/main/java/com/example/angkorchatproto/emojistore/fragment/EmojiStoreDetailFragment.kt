package com.example.angkorchatproto.emojistore.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentEmojiStoreDetailBinding
import com.example.angkorchatproto.emojistore.adapter.DetailEmogiAdapter
import com.example.angkorchatproto.emojistore.data.Data
import com.example.angkorchatproto.emojistore.viewmodel.EmojiStoreViewModel
import com.example.angkorchatproto.utils.Utils

class EmojiStoreDetailFragment : Fragment() {
    private val activityViewModel: EmojiStoreViewModel? by activityViewModels()
    private var data: Data.EmojiInfo? = null
    lateinit var binding: FragmentEmojiStoreDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (arguments != null) {
                data = arguments?.getSerializable("data", Data.EmojiInfo::class.java) as Data.EmojiInfo
            }
        } else {
            if (arguments != null) {
                data = arguments?.getSerializable("data") as Data.EmojiInfo
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmojiStoreDetailBinding.inflate(inflater, container, false)
        binding.ibBuy.setOnClickListener {

        }
        binding.ibGift.setOnClickListener {

        }
        binding.ivBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.ivHome.setOnClickListener {
            view?.findNavController()?.navigate(R.id.emojiStoreMainFragment)
        }
        binding.ivClose.setOnClickListener {
            requireActivity().finish()
        }
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvEmojiList.layoutManager = gridLayoutManager
        var defaultArrayList = arrayListOf<Int>()
        data?.imageId?.let { binding.ivEmoji.setImageResource(it) }
        when (data?.imageId) {
            R.drawable.nunu1 -> {
                defaultArrayList = Utils.typedArrayToArrayList(resources.obtainTypedArray(R.array.nunuImoge))
            }
            R.drawable.gana1 -> {
                binding.ivEmoji.setImageResource(R.drawable.gana1)
                defaultArrayList = Utils.typedArrayToArrayList(resources.obtainTypedArray(R.array.ganaImoge))
            }
            R.drawable.haha1 -> {
                binding.ivEmoji.setImageResource(R.drawable.haha1)
                defaultArrayList = Utils.typedArrayToArrayList(resources.obtainTypedArray(R.array.hahaImoge))
            }
            else -> {
                view?.findNavController()?.popBackStack()
            }
        }

        binding.rvEmojiList.adapter = DetailEmogiAdapter(
            requireContext(),
            activityViewModel?.selectEmojiName,
            defaultArrayList,
            object : DetailEmogiAdapter.OnChatImogeAdapterListener {
                override fun onItemClicked(item: Int?, itemIdx: Int?, characterName: String?) {
                    Log.d("이모지 선택", "리소스 아이디 : $item")
                }
            })
        return binding.root
    }
}