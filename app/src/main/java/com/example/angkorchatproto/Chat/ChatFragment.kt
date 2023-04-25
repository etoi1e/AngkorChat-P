package com.example.angkorchatproto.Chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    lateinit var binding : FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatBinding.inflate(inflater, container, false)

        var chatRoomList = arrayListOf<ChatVO>()

        chatRoomList.add(ChatVO("Text","하하","PM 05:00"))

        val adapter = ChatRoomAdapter(requireContext(),chatRoomList)
        binding.rvChatListChats.adapter = adapter
        binding.rvChatListChats.layoutManager = GridLayoutManager(requireContext(),1)





        return binding.root
    }

}