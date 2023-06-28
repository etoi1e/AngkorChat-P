package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentPayMainBinding
import com.example.angkorchatproto.emojistore.adapter.DrawerEmojiAdapter
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
        binding.ivMenu.setOnClickListener {
            if (binding.dl.isDrawerOpen(GravityCompat.END)) {
                binding.dl.closeDrawer(GravityCompat.END)
            } else {
                binding.dl.openDrawer(GravityCompat.END)
            }
        }
        binding.rvDrawer.adapter = DrawerEmojiAdapter(requireContext(),
            arrayListOf("My QR", "Angkor Point", "Angkor Giftcard", "Payment History", "Transfer History", "Password", "Notice", "Help and Contact Us"),
            object: DrawerEmojiAdapter.OnDrawerEmojiListener {
                override fun onItemClicked(item: String) {
                    when (item) {
                        "My QR"-> view?.findNavController()?.navigate(R.id.action_payMainFragment_to_payMyQrFragment)
                        "Angkor Point"-> view?.findNavController()?.navigate(R.id.action_payMainFragment_to_payPointFragment)
                        "Angkor Giftcard"-> view?.findNavController()?.navigate(R.id.action_payMainFragment_to_payAngkorGiftCardFragment)
                        "Payment History"-> {
                            val args = Bundle()
                            args.putString("title","Payment Details")
                            view?.findNavController()?.navigate(R.id.action_payMainFragment_to_payHistoryFragment, args)
                        }
                        "Transfer History"-> {
                            val args = Bundle()
                            args.putString("title","Transfer details")
                            view?.findNavController()?.navigate(R.id.action_payMainFragment_to_payHistoryFragment, args)
                        }
                        "Password"-> {
                            val args = Bundle()
                            args.putString("title","Password")
                            view?.findNavController()?.navigate(R.id.action_payMainFragment_to_paymentPasswordFragment, args)
                        }
                        "Notice"-> view?.findNavController()?.navigate(R.id.action_payMainFragment_to_payMyQrFragment)
                        "Help and Contact Us"-> view?.findNavController()?.navigate(R.id.action_payMainFragment_to_payMyQrFragment)
                    }
                }
            })
        binding.ivClose.setOnClickListener {
            requireActivity().finish()
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (activityViewModel?.payType == "myQr") {
            view?.findNavController()
                ?.navigate(R.id.action_payMainFragment_to_payMyQrFragment)
        }
    }
}