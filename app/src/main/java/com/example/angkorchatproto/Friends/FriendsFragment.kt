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
import com.bumptech.glide.Glide
import com.example.angkorchatproto.Profile.ProfileActivity
import com.example.angkorchatproto.R
import com.example.angkorchatproto.UserVO
import com.example.angkorchatproto.databinding.FragmentFriendsBinding


class FriendsFragment : Fragment() {

    lateinit var binding: FragmentFriendsBinding
    lateinit var contactsList:ArrayList<UserVO>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFriendsBinding.inflate(inflater, container, false)
        var favoriteList = arrayListOf<UserVO>()


        //즐겨찾는 친구 부분
        favoriteList.add(UserVO("유니온모바일","union-mobile@union-mobile.co.kr","test","123-456-789"))

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

        //전체 친구 Adapter
        binding.rvFriendsFriends.adapter = friendAdapter
        binding.rvFriendsFriends.layoutManager = GridLayoutManager(requireContext(),1)

        //전체 친구 Count
        val countFriends = contactsList.size.toString()
        binding.tvCountFriendsFriends.text = countFriends




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
                    phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
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