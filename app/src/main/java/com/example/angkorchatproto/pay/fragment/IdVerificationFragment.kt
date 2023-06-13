package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentIdCardBinding
import com.example.angkorchatproto.databinding.FragmentIdVerificationBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel

class IdVerificationFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentIdVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIdVerificationBinding.inflate(inflater, container, false)
        binding.btnPhoto.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_idVerificationFragment_to_idCardFragment)
        }
        binding.btnBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.ivClose.setOnClickListener {
            requireActivity().finish()
        }
        return binding.root
    }
}