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
import com.example.angkorchatproto.databinding.FragmentPayTransferCompleteBinding
import com.example.angkorchatproto.databinding.FragmentPaymentPasswordBinding
import com.example.angkorchatproto.databinding.FragmentPaymentRegistrationBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel

class PayTransferCompleteFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding:FragmentPayTransferCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayTransferCompleteBinding.inflate(inflater, container, false)
        binding.btnDone.setOnClickListener {

        }
        return binding.root
    }
}