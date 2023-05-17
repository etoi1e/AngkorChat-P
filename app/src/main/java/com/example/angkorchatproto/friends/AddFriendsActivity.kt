package com.example.angkorchatproto.friends

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.UserVO
import com.example.angkorchatproto.base.BaseActivity
import com.example.angkorchatproto.databinding.ActivityAddFriendsBinding
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError


class AddFriendsActivity : BaseActivity() {

    lateinit var binding: ActivityAddFriendsBinding
    var suggestList: ArrayList<UserVO> = ArrayList()
    var friendList: ArrayList<String> = ArrayList()
    var friendRef = FBdataBase.getFriendRef()


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityAddFriendsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //뒤로가기
        binding.imgMoveBackAddFriends.setOnClickListener {
            finish()
        }

        binding.imgContactAddFriends.setOnClickListener {
            val intent = Intent(this, AddByContactActivity::class.java)
            startActivity(intent)
        }


        //로그인한 계정 번호 불러오기
        //SharedPreferences
        val shared = getSharedPreferences("loginNumber", 0)
        val userNumber = shared.getString("userNumber", "")


        //주소록 친구 불러와서 rv에 출력
        getContacts()
        val adapter = SuggestedAdapter(
            this@AddFriendsActivity,
            suggestList,
            userNumber.toString(),
            friendList
        )
        binding.rvSuggestedListAddFriends.adapter = adapter
        binding.rvSuggestedListAddFriends.layoutManager =
        GridLayoutManager(this@AddFriendsActivity, 1)


        //추천 친구 Count
        if (suggestList.size == 0 || suggestList.size == null) {
            binding.tvSuggestedCountAddFriends.text = "0"
        } else {
            val testList = suggestList.size.toString()
            binding.tvSuggestedCountAddFriends.text = testList
        }


        //FireBase 데이터 불러와서 friendList에 저장하기
        friendRef.child(userNumber.toString()).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                val userData = dataSnapshot.value as HashMap<String, Any>?

                val friendNum = userData!!["phone"] as String
                friendList.add(friendNum)

            }


            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }
    //onCreate바깥


    //디바이스 주소록 가져오기

    @SuppressLint("Range")
    private fun getContacts(): ArrayList<UserVO> {

        // 주소록에 접근하기 위한 ContentResolver 생성
        val cr = this.contentResolver

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


        // 가져온 연락처 정보를 리스트에 저장
        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                // 전화번호 정보를 가져오기 위한 URI 생성
                val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

                // 가져올 전화번호 정보의 컬럼 정보
                val phoneProjection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)

                // 전화번호 정보를 가져오기 위한 쿼리문 실행
                val phoneCursor = cr.query(
                    phoneUri,
                    phoneProjection,
                    "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                    arrayOf(id),
                    null
                )

                var phoneNumber = ""
                if (phoneCursor != null && phoneCursor.moveToFirst()) {

                    phoneNumber =
                        phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))


                    phoneCursor.close()
                }

                // 이메일 정보를 가져오기 위한 URI 생성
                val emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI

                // 가져올 이메일 정보의 컬럼 정보
                val emailProjection = arrayOf(ContactsContract.CommonDataKinds.Email.DATA)

                // 이메일 정보를 가져오기 위한 쿼리문 실행
                val emailCursor = cr.query(
                    emailUri,
                    emailProjection,
                    "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
                    arrayOf(id),
                    null
                )

                var emailAddress = ""
                if (emailCursor != null && emailCursor.moveToFirst()) {
                    emailAddress =
                        emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                    emailCursor.close()
                }

                // 사진 정보를 가져오기 위한 URI 생성
                val photoUri =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI))



                if (photoUri != null) {
                    suggestList.add(UserVO(name, emailAddress, photoUri, phoneNumber))
                } else {
                    suggestList.add(UserVO(name, emailAddress, "", phoneNumber))
                }


            }
            cursor.close()
        }
        return suggestList

    }




}