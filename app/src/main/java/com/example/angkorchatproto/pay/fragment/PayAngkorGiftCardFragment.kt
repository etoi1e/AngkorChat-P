package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentPayGiftcardBinding
import com.example.angkorchatproto.databinding.FragmentPayMainCodeBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel

class PayAngkorGiftCardFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPayGiftcardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayGiftcardBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        binding.ivClose.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        binding.angkorGiftcardCodeLayout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_payAngkorGiftCardFragment_to_payAngkorGiftCardCodeFragment)
        }

        binding.angkorGiftcardHistoryLayout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_payAngkorGiftCardFragment_to_payAngkorGiftCardHistoryFragment)
        }

        return binding.root
    }
}