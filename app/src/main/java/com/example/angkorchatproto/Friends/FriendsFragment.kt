package com.example.angkorchatproto.Friends



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.Profile.ProfileActivity
import com.example.angkorchatproto.R
import com.example.angkorchatproto.UserVO
import com.example.angkorchatproto.databinding.FragmentFriendsBinding
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue


class FriendsFragment : Fragment() {

    lateinit var binding: FragmentFriendsBinding
    lateinit var friendList: ArrayList<UserVO>
    lateinit var friendAdapter : FriendsAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFriendsBinding.inflate(inflater, container, false)
        var favoriteList = arrayListOf<UserVO>()


        //친구추가 버튼 클릭시
        binding.imgAddFriendsFriends.setOnClickListener {
            val intent = Intent(requireContext(), AddFriendsActivity::class.java)
            startActivity(intent)

        }

        //로그인한 계정 번호 불러오기
        //SharedPreferences
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val userNumber = shared.getString("userNumber", "").toString()




        //즐겨찾는 친구에 유니온모바일 무조건 추가
        favoriteList.add(UserVO("유니온모바일", "union-mobile@union-mobile.co.kr", "union", "+123456789"))

        //즐겨찾는 친구 Adapter
        val favoriteAdapter = FriendsAdapter(requireContext(), favoriteList)
        favoriteAdapter.setOnItemClickListener(object : FriendsAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {

                val intent = Intent(requireContext(), ProfileActivity::class.java)

                intent.putExtra("name", favoriteList[position].name)
                intent.putExtra("number", favoriteList[position].phone)
                intent.putExtra("email", favoriteList[position].email)
                intent.putExtra("profile", favoriteList[position].profile)

                startActivity(intent)

            }

        })
        binding.rvFavoriteFriends.adapter = favoriteAdapter
        binding.rvFavoriteFriends.layoutManager = GridLayoutManager(requireContext(), 1)

        //즐겨찾는 친구 Count
        val countFavorite = favoriteList.size.toString()
        binding.tvCountFavoriteFriends.text = countFavorite

        friendList = getFirebase()

        friendList = ArrayList()

        friendAdapter = FriendsAdapter(requireContext(), friendList)


        //친구 목록 접기
        binding.imgFoldFavoriteFriends.setOnClickListener {
            if (binding.imgFoldFavoriteFriends.tag == true) { //목록 펼치기
                binding.imgFoldFavoriteFriends.setImageResource(R.drawable.ic_indecator_up_16)
                binding.rvFavoriteFriends.visibility = View.VISIBLE
                binding.imgFoldFavoriteFriends.tag = false

            } else { //목록 접기
                binding.imgFoldFavoriteFriends.setImageResource(R.drawable.ic_indecator_up_16)
                binding.rvFavoriteFriends.visibility = View.GONE
                binding.imgFoldFavoriteFriends.tag = true

            }
        }

        binding.imgFoldFriendsFriends.setOnClickListener {
            if (binding.imgFoldFriendsFriends.tag == true) { //목록 펼치기
                binding.imgFoldFriendsFriends.setImageResource(R.drawable.ic_indecator_up_16)
                binding.rvFriendsFriends.visibility = View.VISIBLE
                binding.imgFoldFriendsFriends.tag = false

            } else { //목록 접기
                binding.imgFoldFriendsFriends.setImageResource(R.drawable.ic_indecator_up_16)
                binding.rvFriendsFriends.visibility = View.GONE
                binding.imgFoldFriendsFriends.tag = true

            }
        }


        // 검색 기능
        var searchViewTextListener: SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener {
                //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
                override fun onQueryTextSubmit(s: String): Boolean {
                    return false
                }
                //텍스트 입력/수정시에 호출
                override fun onQueryTextChange(s: String): Boolean {
                    friendAdapter.getFilter().filter(s)
                    Log.d("test-서치뷰", "SearchViews Text is changed : $s")
                    return false
                }
            }

        binding.svSearchFriendFriends.setOnQueryTextListener(searchViewTextListener)


        //전체 친구 Adapter
        binding.rvFriendsFriends.adapter = friendAdapter
        binding.rvFriendsFriends.layoutManager = GridLayoutManager(requireContext(), 1)

        return binding.root

    }

    fun getFirebase():ArrayList<UserVO>{
        friendList = ArrayList()

        //SharedPreferences
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val userNumber = shared.getString("userNumber", "").toString()

        val friendRef = FBdataBase.getFriendRef()

        friendRef.child(userNumber).addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //Log.d("TAG-전체친구목록",snapshot.toString())
                val firendItem = snapshot.getValue<UserVO>() as UserVO
                friendList.add(firendItem)

                //전체 친구 Count
                if (friendList.size == 0) {
                    binding.tvCountFriendsFriends.text = "0"
                } else {
                    val testList = friendList.size.toString()
                    binding.tvCountFriendsFriends.text = testList
                }

                friendAdapter.notifyDataSetChanged()


            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                friendAdapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




     return friendList
    }




//
//    @SuppressLint("Range")
//    private fun getContacts(): ArrayList<UserVO> {
//        contactsList = ArrayList()
//
//        // 주소록에 접근하기 위한 ContentResolver 생성
//        val cr = requireContext().contentResolver
//
//        // 주소록에 저장된 연락처 정보를 가져오기 위한 URI 생성
//        val uri = ContactsContract.Contacts.CONTENT_URI
//
//        // 연락처 정보를 가져오기 위한 쿼리문 실행
//        val cursor = cr.query(
//            uri,
//            null,
//            null,
//            null,
//            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
//        )
//
//
//
//        // 가져온 연락처 정보를 리스트에 저장
//        if (cursor != null && cursor.count > 0) {
//            while (cursor.moveToNext()) {
//                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
//                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
//
//                // 전화번호 정보를 가져오기 위한 URI 생성
//                val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
//
//                // 가져올 전화번호 정보의 컬럼 정보
//                val phoneProjection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
//
//                // 전화번호 정보를 가져오기 위한 쿼리문 실행
//                val phoneCursor = cr.query(phoneUri, phoneProjection, "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?", arrayOf(id), null)
//
//                var phoneNumber = ""
//                if (phoneCursor != null && phoneCursor.moveToFirst()) {
//
//                    phoneNumber =phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//
//
//                    phoneCursor.close()
//                }
//
//                // 이메일 정보를 가져오기 위한 URI 생성
//                val emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI
//
//                // 가져올 이메일 정보의 컬럼 정보
//                val emailProjection = arrayOf(ContactsContract.CommonDataKinds.Email.DATA)
//
//                // 이메일 정보를 가져오기 위한 쿼리문 실행
//                val emailCursor = cr.query(emailUri, emailProjection, "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?", arrayOf(id), null)
//
//                var emailAddress = ""
//                if (emailCursor != null && emailCursor.moveToFirst()) {
//                    emailAddress = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
//                    emailCursor.close()
//                }
//
//                // 사진 정보를 가져오기 위한 URI 생성
//                val photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI))
//
//
//
//                if(photoUri != null){
//                    contactsList.add(UserVO(name, emailAddress, photoUri,phoneNumber))
//                }else{
//                    contactsList.add(UserVO(name, emailAddress, "",phoneNumber))
//                }
//
//
//            }
//            cursor.close()
//        }
//
//
//
//        return contactsList
//    }


}