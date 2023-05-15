package com.example.angkorchatproto.chat


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.MainActivity
import com.example.angkorchatproto.auth.LoginActivity
import com.example.angkorchatproto.chat.ChatModel
import com.example.angkorchatproto.chat.ChatRoomAdapter
import com.example.angkorchatproto.databinding.FragmentChatBinding
import com.example.angkorchatproto.emojistore.EmojiStoreActivity
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.database.ktx.values


class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding
    lateinit var myNumber: String
    lateinit var adapter: ChatRoomAdapter
    val chatRef = FBdataBase.getChatRef()
    var chatRoomsKeys = ArrayList<String>()
    val chatInfoList = ArrayList<ChatModel.Comment>()
    val chatCountList = ArrayList<ChatModel.Comment>()
    var sender = ""
    var chatCount = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatBinding.inflate(inflater, container, false)

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        myNumber = shared.getString("userNumber", "").toString()


        //사용자가 포함된 채팅창 호출
        chatRef.orderByChild("users/$myNumber").equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    //같은 키를 가진 채팅방 마지막 1개만 출력하기
                    for (item in snapshot.children) {
                        if (!chatRoomsKeys.contains(item.key.toString())) {

                            chatRoomsKeys.add(item.key.toString())


                            //시간 기준 정렬
                            chatRef.child(item.key.toString()).child("comments")
                                .orderByChild("time").limitToLast(1)
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    @SuppressLint("NotifyDataSetChanged")
                                    override fun onDataChange(snapshot: DataSnapshot) {

                                        for (data in snapshot.children) {
                                            val item = data.getValue<ChatModel.Comment>()


                                            if (item != null) {
                                                chatInfoList.add(item)
                                            }

                                        }

                                        adapter.notifyDataSetChanged()
                                        chatRoomsKeys.add(item.key.toString())

                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }
                                })
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


        adapter = ChatRoomAdapter(requireContext(), chatInfoList, chatRoomsKeys, myNumber, chatCount)
        binding.rvChatListChats.adapter = adapter
        binding.rvChatListChats.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.imgEmojiChats.setOnClickListener {
            val intent = Intent(requireContext(), EmojiStoreActivity::class.java)
            startActivity(intent)
        }
        binding.imgAddChatChats.setOnClickListener {
            val intent = Intent(requireActivity(), SelectUserActivity::class.java)
            startActivity(intent)
        }

        return binding.root


    }
    //onCreateView 바깥


}