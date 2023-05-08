package com.example.angkorchatproto.Chat


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.databinding.FragmentChatBinding
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


                            //채팅창 내 채팅 갯수 불러오기
                            chatRef.child(item.key.toString()).child("comments")
                                .orderByChild("state").equalTo(false)
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        for (chats in snapshot.children) {
                                            val item = chats.getValue<ChatModel.Comment>()
                                            if (item != null) {
                                                chatCountList.add(item)
                                            }
                                        }
                                        chatCount.add(chatCountList.size.toString())
                                        chatCountList.clear()
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }

                                })

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

        adapter = ChatRoomAdapter(requireContext(), chatInfoList, chatRoomsKeys, sender, chatCount)
        binding.rvChatListChats.adapter = adapter
        binding.rvChatListChats.layoutManager = GridLayoutManager(requireContext(), 1)



        return binding.root


    }
    //onCreateView 바깥


}