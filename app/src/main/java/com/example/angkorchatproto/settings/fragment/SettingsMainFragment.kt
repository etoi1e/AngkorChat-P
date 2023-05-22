package com.example.angkorchatproto.settings.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.angkorchatproto.settings.data.Data.SettingsItem
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentSettingsMainBinding
import com.example.angkorchatproto.settings.adapter.SettingsMainAdapter
import com.example.angkorchatproto.settings.viewmodel.SettingsViewModel

class SettingsMainFragment : Fragment() {
    lateinit var binding: FragmentSettingsMainBinding
    private val activityViewModel: SettingsViewModel? by activityViewModels()
    private val settingList: ArrayList<SettingsItem> = arrayListOf(
        SettingsItem(R.drawable.ic_info_line_24, "Version"),
        SettingsItem(R.drawable.ic_noti_line_24, "Notifications and Sounds"),
        SettingsItem(R.drawable.ic_lock_line_24, "Privacy and Security"),
        SettingsItem(R.drawable.ic_user_line_24, "Friends"),
        SettingsItem(R.drawable.ic_chat_line_24, "Chatting"),
        SettingsItem(R.drawable.ic_display_line_24, "Display"),
        SettingsItem(R.drawable.ic_world_line_24, "Language"),
        SettingsItem(R.drawable.ic_question_line_24, "Help Center"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Log.d("FragmentSettingsMainBinding", "onResume")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("FragmentSettingsMainBinding", "onCreateView")
        binding = FragmentSettingsMainBinding.inflate(inflater, container, false)
        val shared = requireActivity().getSharedPreferences("loginNumber", 0)
        val userNumber = shared.getString("userNumber", "")
        binding.tvUserPhoneNumber.text = userNumber
        binding.clAccount.setOnClickListener {
            view?.findNavController()?.navigate(R.id.settingsAccountFragment)
        }
        binding.rvSettingsList.adapter = SettingsMainAdapter(requireContext(),
            settingList,
            object: SettingsMainAdapter.OnSettingsItemListener {
                override fun onItemClicked(item: SettingsItem) {
                    when (item.title) {
                        "Version" -> view?.findNavController()?.navigate(R.id.settingsVersionFragment)
                        "Notifications and Sounds" -> view?.findNavController()?.navigate(R.id.settingsNotificationsFragment)
                        "Privacy and Security" -> view?.findNavController()?.navigate(R.id.settingsPrivacyFragment)
                        "Friends" -> view?.findNavController()?.navigate(R.id.settingsFriendsFragment)
                        "Chatting" -> view?.findNavController()?.navigate(R.id.settingsChattingFragment)
                        "Display" -> view?.findNavController()?.navigate(R.id.settingsDisplayFragment)
                        "Language" -> view?.findNavController()?.navigate(R.id.settingsLanguageFragment)
                        "Help Center" -> view?.findNavController()?.navigate(R.id.settingsHelpFragment)
                    }
                }
            }
        )

        binding.ivClose.setOnClickListener {
            requireActivity().finish()
        }

        binding.ivSearch.setOnClickListener {

        }
        return binding.root
    }
}