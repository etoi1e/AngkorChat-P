package com.example.angkorchatproto.friends.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.UserVO
import com.example.angkorchatproto.databinding.FragmentContactsBinding
import com.example.angkorchatproto.dialog.CustomDialog
import com.example.angkorchatproto.utils.CountryAdapter
import com.example.angkorchatproto.utils.CountryUtils
import com.example.angkorchatproto.utils.FBdataBase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kr.co.kdnavien.naviensmart.presentation.custom.DialogPositiveBtnListener

class ContactsFragment : Fragment() {
    lateinit var binding: FragmentContactsBinding
    private var friendList: ArrayList<String> = ArrayList()
    private var friendRef = FBdataBase.getFriendRef()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userNumber = requireActivity().getSharedPreferences("loginNumber", 0).getString("userNumber", "")
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etPhoneNumberLogin.requestFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etPhoneNumberLogin, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        val countries = CountryUtils.getCountries()
        val adapter = CountryAdapter(requireContext(), countries)
        binding.spCountryCodeLogin.adapter = adapter
        binding.imgMoveBackLogin.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.etPhoneNumberLogin.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val resultPhoneNum = validateUser()
                val userNumber = requireActivity().getSharedPreferences("loginNumber", 0).getString("userNumber", "")
                val removeDash = resultPhoneNum?.phone.toString().replace("-", "")
                val removeSpace = removeDash.replace(" ", "")
                resultPhoneNum?.phone = removeSpace

                val resultPhoneNumAdded = friendList.contains(resultPhoneNum?.phone)
                if (resultPhoneNum != null) {
                    view?.findNavController()?.navigate(R.id.resultFragment, bundleOf(
                        "user" to resultPhoneNum,
                        "phoneNumberAdded" to resultPhoneNumAdded,
                        "userNumber" to userNumber
                    ))
                } else {
                    CustomDialog.create(requireActivity())
                        ?.setDesc(SpannableStringBuilder("Invalid phone number. Please\nenter a valid phone number."))
                        ?.setCancelable(true)
                        ?.setPositiveButtonText(SpannableStringBuilder("Done"))
                        ?.setPositiveBtnListener(object: DialogPositiveBtnListener {
                            override fun confirm(division: Int) {
                            }
                        })
                        ?.showOneButton()
                }
                true
            } else {
                false
            }
        }
        return binding.root
    }

    private fun validateUser(): UserVO? {
        Log.d("ContactsFragment","${binding.etPhoneNumberLogin.text}")
        return getContacts().find {
            it.phone == binding.etPhoneNumberLogin.text.toString()
        }
    }

    private fun isUserAdded() {

    }

    @SuppressLint("Range")
    private fun getContacts(): ArrayList<UserVO> {
        val suggestList: ArrayList<UserVO> = ArrayList()
        // 주소록에 접근하기 위한 ContentResolver 생성
        val cr = requireActivity().contentResolver

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