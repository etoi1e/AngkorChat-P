package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.databinding.FragmentPayGiftcardCodeBinding
import com.example.angkorchatproto.databinding.FragmentPayGiftcardHistoryBinding
import com.example.angkorchatproto.databinding.FragmentPayMainCodeBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel

class PayAngkorGiftCardHistoryFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPayGiftcardHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayGiftcardHistoryBinding.inflate(inflater, container, false)

        binding.ivClose.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }


        return binding.root
    }
}