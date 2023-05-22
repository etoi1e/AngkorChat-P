package com.example.angkorchatproto.settings.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.ActivityEmojiStoreBinding
import com.example.angkorchatproto.databinding.FragmentEmojiStoreMainBinding
import com.example.angkorchatproto.databinding.FragmentFriendsBinding
import com.example.angkorchatproto.databinding.FragmentSettingsFriendsBinding
import com.example.angkorchatproto.databinding.FragmentSettingsMainBinding
import com.example.angkorchatproto.databinding.FragmentSettingsVersionBinding
import com.example.angkorchatproto.emojistore.adapter.DrawerEmojiAdapter
import com.example.angkorchatproto.emojistore.adapter.NewEmojiAdapter
import com.example.angkorchatproto.emojistore.adapter.ListEmojiAdapter
import com.example.angkorchatproto.emojistore.data.Data
import com.example.angkorchatproto.emojistore.viewmodel.EmojiStoreViewModel
import com.example.angkorchatproto.settings.viewmodel.SettingsViewModel

class SettingsFriendsFragment : Fragment() {
    lateinit var binding: FragmentSettingsFriendsBinding
    private val activityViewModel: SettingsViewModel? by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Log.d("FragmentSettingsFriendsBinding", "onResume")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("FragmentSettingsFriendsBinding", "onCreateView")
        binding = FragmentSettingsFriendsBinding.inflate(inflater, container, false)
        binding.ivClose.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        return binding.root
    }
}