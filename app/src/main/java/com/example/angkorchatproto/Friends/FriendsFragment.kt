package com.example.angkorchatproto.Friends


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.Msg
import com.example.angkorchatproto.Profile.ProfileActivity
import com.example.angkorchatproto.R
import com.example.angkorchatproto.UserVO
import com.example.angkorchatproto.databinding.FragmentFriendsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FriendsFragment : Fragment() {

    lateinit var binding: FragmentFriendsBinding
    lateinit var contactsList:ArrayList<UserVO>
    lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFriendsBinding.inflate(inflater, container, false)
        var favoriteList = arrayListOf<UserVO>()


        database = Firebase.database.reference



        //firebase 데이터 삽입 테스트
        binding.imgAddFriendsFriends.setOnClickListener{


            database.child("msg").setValue("테스트")
            val setData = Firebase.database.getReference("msg")
            setData.push().setValue("테스트2")
        }


        //즐겨찾는 친구 부분
        favoriteList.add(UserVO("유니온모바일","union-mobile@union-mobile.co.kr", "union","+123456789"))

        //즐겨찾는 친구 Adapter
        val favoriteAdapter = FriendsAdapter(requireContext(),favoriteList)
        favoriteAdapter.setOnItemClickListener(object : FriendsAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(requireContext(), ProfileActivity::class.java)

                intent.putExtra("name",favoriteList[position].name)
                intent.putExtra("number",favoriteList[position].phone)
                intent.putExtra("email",favoriteList[position].email)
                intent.putExtra("profile",favoriteList[position].profile)

                startActivity(intent)

            }

        })
        binding.rvFavoriteFriends.adapter = favoriteAdapter
        binding.rvFavoriteFriends.layoutManager = GridLayoutManager(requireContext(),1)

        //즐겨찾는 친구 Count
        val countFavorite = favoriteList.size.toString()
        binding.tvCountFavoriteFriends.text = countFavorite


        //주소록 목록 불러오기
        getContacts()

        val friendAdapter = FriendsAdapter(requireContext(), contactsList)

        //클릭 시 유저 프로필로 이동
        friendAdapter.setOnItemClickListener(object : FriendsAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(requireContext(), ProfileActivity::class.java)


                intent.putExtra("name",contactsList[position].name)
                intent.putExtra("number",contactsList[position].phone)
                intent.putExtra("email",contactsList[position].email)
                intent.putExtra("profile",contactsList[position].profile)

                startActivity(intent)

            }

        })

//        //전체 친구 Adapter
//        binding.rvFriendsFriends.adapter = friendAdapter
//        binding.rvFriendsFriends.layoutManager = GridLayoutManager(requireContext(),1)
//
        //전체 친구 Count
        val countFriends = contactsList.size.toString()
        binding.tvCountFriendsFriends.text = countFriends



        //친구 목록 접기
        //사용자 지정값 저장해서 다른 페이지 다녀와도 바뀌지 않게 하기
        binding.imgFoldFavoriteFriends.setOnClickListener {
            if(binding.imgFoldFavoriteFriends.tag==true){ //목록 펼치기
                binding.imgFoldFavoriteFriends.setImageResource(R.drawable.ic_indecator_up_16)
                binding.rvFavoriteFriends.visibility = View.VISIBLE
                binding.imgFoldFavoriteFriends.tag = false

            }else{ //목록 접기
                binding.imgFoldFavoriteFriends.setImageResource(R.drawable.ic_indicator_down_24)
                binding.rvFavoriteFriends.visibility = View.GONE
                binding.imgFoldFavoriteFriends.tag = true

            }
        }

        binding.imgFoldFriendsFriends.setOnClickListener {
            if(binding.imgFoldFriendsFriends.tag==true){ //목록 펼치기
                binding.imgFoldFriendsFriends.setImageResource(R.drawable.ic_indecator_up_16)
                binding.rvFriendsFriends.visibility = View.VISIBLE
                binding.imgFoldFriendsFriends.tag = false

            }else{ //목록 접기
                binding.imgFoldFriendsFriends.setImageResource(R.drawable.ic_indicator_down_24)
                binding.rvFriendsFriends.visibility = View.GONE
                binding.imgFoldFriendsFriends.tag = true

            }
        }



        return binding.root

    }





    @SuppressLint("Range")
    private fun getContacts(): ArrayList<UserVO> {
        contactsList = ArrayList()

        // 주소록에 접근하기 위한 ContentResolver 생성
        val cr = requireContext().contentResolver

        // 주소록에 저장된 연락처 정보를 가져오기 위한 URI 생성
        val uri = ContactsContract.Contacts.CONTENT_URI

        // 연락처 정보를 가져오기 위한 쿼리문 실행
        val cursor = cr.query(
            uri,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )
//        val cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, projection, null, null, null)

        // 가져온 연락처 정보를 리스트에 저장
        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                // 전화번호 정보를 가져오기 위한 URI 생성
                val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

                // 가져올 전화번호 정보의 컬럼 정보
                val phoneProjection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)

                // 전화번호 정보를 가져오기 위한 쿼리문 실행
                val phoneCursor = cr.query(phoneUri, phoneProjection, "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?", arrayOf(id), null)

                var phoneNumber = ""
                if (phoneCursor != null && phoneCursor.moveToFirst()) {
                    
                    phoneNumber =phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    
                    //firebase 연락처 저장
//                    val friendsList = phoneNumber.replace("-","")
//                    val database = Firebase.database.reference
//                    database.child("user").child(friendsList).setValue(friendsList)


                    phoneCursor.close()
                }

                // 이메일 정보를 가져오기 위한 URI 생성
                val emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI

                // 가져올 이메일 정보의 컬럼 정보
                val emailProjection = arrayOf(ContactsContract.CommonDataKinds.Email.DATA)

                // 이메일 정보를 가져오기 위한 쿼리문 실행
                val emailCursor = cr.query(emailUri, emailProjection, "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?", arrayOf(id), null)

                var emailAddress = ""
                if (emailCursor != null && emailCursor.moveToFirst()) {
                    emailAddress = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                    emailCursor.close()
                }

                // 사진 정보를 가져오기 위한 URI 생성
                val photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI))



                if(photoUri != null){
                    contactsList.add(UserVO(name, emailAddress, photoUri,phoneNumber))
                }else{
                    contactsList.add(UserVO(name, emailAddress, "",phoneNumber))
                }


            }
            cursor.close()
        }



        return contactsList
    }




}