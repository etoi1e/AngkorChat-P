package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentPayMainBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel

class PayMainFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPayMainBinding
    private var mNavController: NavController? = null
    private var mNavHostFragment: NavHostFragment? = null
    private var mSelectTab = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayMainBinding.inflate(inflater, container, false)
        mNavHostFragment = childFragmentManager.findFragmentById(R.id.pay_main_container) as? NavHostFragment
        mNavController = mNavHostFragment?.navController

        if (activityViewModel?.payType == "transfer") {
            if (mSelectTab == 0) {
                mSelectTab = 1
                binding.btnPayCode.isChecked = false
                binding.btnScan.isChecked = true
                mNavController?.navigate(R.id.action_payMainCodeFragment_to_payMainScanFragment)
            }
        }

        binding.btnPayCode.setOnClickListener {
            if (mSelectTab == 1) {
                mSelectTab = 0
                binding.btnPayCode.isChecked = true
                binding.btnScan.isChecked = false
                mNavController?.popBackStack()
            }
        }
        binding.btnScan.setOnClickListener {
            if (mSelectTab == 0) {
                mSelectTab = 1
                binding.btnPayCode.isChecked = false
                binding.btnScan.isChecked = true
                mNavController?.navigate(R.id.action_payMainCodeFragment_to_payMainScanFragment)
            }
        }
        binding.ivClose.setOnClickListener {
            requireActivity().finish()
        }
        return binding.root
    }
}