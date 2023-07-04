package com.example.angkorchatproto.chat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.R
import com.example.angkorchatproto.UserVO
import com.example.angkorchatproto.base.BaseActivity
import com.example.angkorchatproto.chat.adapter.SelectUsersAdapter
import com.example.angkorchatproto.databinding.ActivitySelectUserBinding
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

/**
 * Package Name : com.example.angkorchatproto.chat
 * Class Name : SelectUser
 * Description :
 * Created by de5ember on 2023/05/15.
 */
class SelectUserActivity : BaseActivity() {
    lateinit var binding: ActivitySelectUserBinding
    var mSuggestList: ArrayList<UserVO> = ArrayList()
    var mFriendList: ArrayList<String> = ArrayList()
    var mSelectUser: UserVO? = null
    var friendRef = FBdataBase.getFriendRef()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySelectUserBinding.inflate(layoutInflater)
        val shared = getSharedPreferences("loginNumber", 0)
        val userNumber = shared.getString("userNumber", "")
        getContacts()

        val checkProfile = intent.getStringExtra("sendProfile")
        Log.d("TAG-checkProfile",checkProfile.toString())

        val adapter = SelectUsersAdapter(
            this@SelectUserActivity,
            mSuggestList,
            mFriendList,
            object: SelectUsersAdapter.OnSelectUsersListener {
                @SuppressLint("ResourceAsColor")
                override fun onItemClicked(user: UserVO) {
                    mSelectUser = user
                    binding.btnNext.setTextColor(getColor(R.color.mainYellow))
                }
            }
        )

        if(checkProfile == "true"){
            with(binding){
                binding.btnNext.setOnClickListener {
                    val intent = Intent(this@SelectUserActivity,ChatActivity::class.java).apply {
                        putExtra("name",mSelectUser?.name)
                        putExtra("email",mSelectUser?.email)
                        putExtra("profile",mSelectUser?.profile)
                        putExtra("phone",mSelectUser?.phone)
                    }
                    setResult(RESULT_OK,intent)
                    finish()
                }
            }
        }

        if(checkProfile != "true"){
            binding.btnNext.setOnClickListener {
                if (binding.btnNext.currentTextColor == getColor(R.color.mainYellow)) {
                    Log.d("SelectUserActivity","채팅방을 열어주세요")
                    //채팅방으로 이동
                    if (mSelectUser != null) {
                        val intent = Intent(this, ChatActivity::class.java)
                        intent.putExtra("name", mSelectUser?.name)
                        intent.putExtra("number", mSelectUser?.phone)
                        intent.putExtra("profile", mSelectUser?.profile)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

        binding.imgMoveBack.setOnClickListener {
            finish()
        }
        binding.rvSuggestedListAddFriends.adapter = adapter
        binding.rvSuggestedListAddFriends.layoutManager =
            GridLayoutManager(this@SelectUserActivity, 1)

        //FireBase 데이터 불러와서 mFriendList에 저장하기
        friendRef.child(userNumber.toString()).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                val userData = dataSnapshot.value as HashMap<String, Any>?

                val friendNum = userData!!["phone"] as String
                mFriendList.add(friendNum)

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

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("search", charSequence.toString())
                if (charSequence.toString().isNotEmpty()) {
                    binding.ibDelete.visibility = View.VISIBLE
                } else {
                    binding.ibDelete.visibility = View.GONE
                }

                val suggestList = mSuggestList.map {
                    if (it.name?.contains(charSequence.toString()) == true) {
                        it
                    } else {
                        UserVO()
                    }
                }.filter {
                    it.name != null
                } as ArrayList

                val friendList = mFriendList.mapNotNull {
                    if (it.contains(charSequence.toString())) {
                        it
                    } else {
                        null
                    }
                } as ArrayList

                adapter.setItem(suggestList, friendList)
            }

            override fun afterTextChanged(charSequence: Editable?) {
            }
        })
        binding.ibDelete.setOnClickListener {
            binding.etSearch.text.clear()
        }

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

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
                    mSuggestList.add(UserVO(name, emailAddress, photoUri, phoneNumber, name))
                } else {
                    mSuggestList.add(UserVO(name, emailAddress, "", phoneNumber, name))
                }
            }
            cursor.close()
        }
        return mSuggestList

    }
}