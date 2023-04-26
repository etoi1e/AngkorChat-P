package com.example.angkorchatproto.Chat


import android.os.Bundle
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

                    //채팅 리스트 0 일 때
                    if ( chatInfoList.size == 0 ){

                        binding.rvChatListChats.visibility = View.GONE
                        binding.boxNoChat.visibility = View.VISIBLE

                    }else{

                        binding.rvChatListChats.visibility = View.VISIBLE
                        binding.boxNoChat.visibility = View.GONE


                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
    }


}