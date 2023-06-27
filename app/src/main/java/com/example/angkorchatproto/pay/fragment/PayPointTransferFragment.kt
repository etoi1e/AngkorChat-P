package com.example.angkorchatproto.pay.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angkorchatproto.R
import com.example.angkorchatproto.UserVO
import com.example.angkorchatproto.chat.adapter.SelectUsersAdapter
import com.example.angkorchatproto.databinding.FragmentPayPointTransferBinding
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class PayPointTransferFragment : Fragment() {

    lateinit var binding: FragmentPayPointTransferBinding
    lateinit var adapter: SelectUsersAdapter
    var mSuggestList: ArrayList<UserVO> = ArrayList()
    var mFriendList: ArrayList<String> = ArrayList()
    var mSelectUser: UserVO? = null
    var friendRef = FBdataBase.getFriendRef()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPayPointTransferBinding.inflate(inflater, container, false)

        binding.ivClosePointTransfer.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()

        getContacts()

        adapter = SelectUsersAdapter(
            requireContext(),
            mSuggestList,
            mFriendList,
            object : SelectUsersAdapter.OnSelectUsersListener {
                @SuppressLint("ResourceAsColor")
                override fun onItemClicked(user: UserVO) {
                    mSelectUser = user
                    binding.btnNextPointTransfer.setTextColor(getColor(requireContext(),R.color.mainYellow))
                    binding.btnNextPointTransfer.tag = "true"
                }
            }
        )

        binding.rvPointTransfer.adapter = adapter
        binding.rvPointTransfer.layoutManager =
            GridLayoutManager(requireContext(), 1)

        //FireBase 데이터 불러와서 mFriendList에 저장하기
        friendRef.child(myNumber).addChildEventListener(object : ChildEventListener {
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

        binding.etSearchPayPoint.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (charSequence.toString().isNotEmpty()) {
                    binding.ibDeletePayPoint.visibility = View.VISIBLE
                } else {
                    binding.ibDeletePayPoint.visibility = View.GONE
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

        binding.ibDeletePayPoint.setOnClickListener {
            binding.etSearchPayPoint.text.clear()
        }


        //Next 활성화
        binding.btnNextPointTransfer.setOnClickListener {
            if(binding.btnNextPointTransfer.tag == "false"){

            }else{
                //SharedPreferences
                val shared = requireContext().getSharedPreferences("checkPrePage", 0)
                val editor = shared.edit()

                editor.putBoolean("checkPrePage",true)
                editor.apply()

                val bundle = bundleOf("name" to mSelectUser!!.name, "number" to mSelectUser!!.phone)
                view?.findNavController()?.navigate(R.id.action_payPointTransferFragment_to_payPointTopUpFragment,bundle)
            }

        }



        return binding.root
    }

    @SuppressLint("Range")
    private fun getContacts(): ArrayList<UserVO> {
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
                    mSuggestList.add(UserVO(name, emailAddress, photoUri, phoneNumber))
                } else {
                    mSuggestList.add(UserVO(name, emailAddress, "", phoneNumber))
                }
            }
            cursor.close()
        }
        return mSuggestList

    }


}