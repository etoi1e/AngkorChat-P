package com.example.angkorchatproto.settings.fragment

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.databinding.FragmentSettingsAccountBinding
import com.example.angkorchatproto.databinding.FragmentSettingsChattingBinding
import com.example.angkorchatproto.settings.viewmodel.SettingsViewModel

class SettingsAccountFragment : Fragment() {
    lateinit var binding: FragmentSettingsAccountBinding
    private val activityViewModel: SettingsViewModel? by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Log.d("FragmentSettingsAccountBinding", "onResume")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("FragmentSettingsAccountBinding", "onCreateView")
        binding = FragmentSettingsAccountBinding.inflate(inflater, container, false)
        val shared = requireActivity().getSharedPreferences("loginNumber", 0)
        val userNumber = shared.getString("userNumber", "")
        val id = shared.getString("id", "")
        binding.tvUserPhoneNumber.text = userNumber
        binding.tvChangeNumber.text = userNumber
        binding.tvDeleteAccount.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvUserName.text = id
        binding.tvUserId.text = id
        binding.ivClose.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        return binding.root
    }
}