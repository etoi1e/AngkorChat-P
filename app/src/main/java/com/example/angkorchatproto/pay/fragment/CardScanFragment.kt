package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.angkorchatproto.databinding.FragmentCardScanBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel

class CardScanFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentCardScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardScanBinding.inflate(inflater, container, false)
        return binding.root
    }
}