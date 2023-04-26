package com.example.angkorchatproto.Chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.Profile.ProfileActivity
import com.example.angkorchatproto.databinding.FragmentChatBinding
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue


class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding
    lateinit var myNumber: String
    lateinit var adapter: ChatRoomAdapter
    val chatRef = FBdataBase.getChatRef()
    var chatRoomsKey : String= ""
    val chatInfoList = ArrayList<ChatModel.Comment>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatBinding.inflate(inflater, container, false)

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        myNumber = shared.getString("userNumber", "").toString()

        getChatRoomList()

        adapter = ChatRoomAdapter(requireContext(), chatInfoList)
        binding.rvChatListChats.adapter = adapter
        binding.rvChatListChats.layoutManager = GridLayoutManager(requireContext(), 1)


        return binding.root
    }
    //onCreateView 바깥

    fun getChatRoomList(){
        chatRef.orderByChild("users/$myNumber").equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (item in snapshot.children) {
                        chatRoomsKey = item.key.toString()
                        getChatRoom()
//                        val chatModel = item.getValue<ChatModel>()
//                        if (chatModel?.users!!.containsKey(myNumber)) {
//                            chatRoomsKey = item.key.toString()
//                            getChatRoomList()
//                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }


    fun getChatRoom() {
        chatRef.child(chatRoomsKey).child("comments")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    for (data in snapshot.children) {
                        val item = data.getValue<ChatModel.Comment>()

                        if (item != null) {
                            chatInfoList.add(item)
                        }

                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
    }


}