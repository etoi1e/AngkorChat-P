package com.example.angkorchatproto.pay.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.angkorchatproto.databinding.FragmentHistorySearchBinding

class HistorySearchFragment : DialogFragment() {

    lateinit var binding : FragmentHistorySearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistorySearchBinding.inflate(inflater,container,false)



        return binding.root
    }

}