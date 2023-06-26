package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.*
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel

class PayTransferPasswordFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPayTransferPasswordBinding
    private var mNavController: NavController? = null
    private var mNavHostFragment: NavHostFragment? = null

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