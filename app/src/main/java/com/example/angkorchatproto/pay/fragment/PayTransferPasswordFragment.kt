package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentPayTransferBinding
import com.example.angkorchatproto.databinding.FragmentPayTransferPasswordBinding
import com.example.angkorchatproto.databinding.FragmentPaymentPasswordBinding
import com.example.angkorchatproto.databinding.FragmentPaymentRegistrationBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel

class PayTransferPasswordFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPayTransferPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayTransferPasswordBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.btnNext.setOnClickListener {

        }
        return binding.root
    }
}