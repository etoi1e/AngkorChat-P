package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentPayTransferBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel

class PayTransferFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPayTransferBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayTransferBinding.inflate(inflater, container, false)

        //Text Checker
        val textChecker = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.editTextNumberDecimal.text.isEmpty()) {
                    binding.btnTransfer.background =
                        requireActivity().getDrawable(R.drawable.style_disable_btn)
                    binding.btnTransfer.isEnabled = false
                } else {
                    binding.btnTransfer.background =
                        requireActivity().getDrawable(R.drawable.style_login_btn)
                    binding.btnTransfer.isEnabled = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        }

        binding.editTextNumberDecimal.addTextChangedListener(textChecker)



        binding.btnBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.btnTransfer.setOnClickListener {
            val bundle = bundleOf("amount" to binding.editTextNumberDecimal.text.toString())
            view?.findNavController()?.navigate(R.id.action_payTransferFragment_to_payTransferPasswordFragment,bundle)
        }
        return binding.root
    }
}