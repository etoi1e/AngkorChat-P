package com.example.angkorchatproto.pay.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentSmsVerificationBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel
import com.example.angkorchatproto.utils.CountryAdapter
import com.example.angkorchatproto.utils.CountryUtils

class SmsVerificationFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()

    //디바이스 번호
    var phoneNumber: String = ""

    //디바이스 국가
    var phoneCountry: String = ""
    lateinit var binding: FragmentSmsVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSmsVerificationBinding.inflate(inflater, container, false)//뒤로가기
        binding.imgMoveBackLogin.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        //디바이스 정보 가져오기
        getPhoneNumber()
        GetCountryZipCode()

        //국가 spinner
        val countries = CountryUtils.getCountries()
        val adapter = CountryAdapter(requireContext(), countries)

        binding.spCountryCodeLogin.adapter = adapter

        //디바이스 번호 자동 입력
        binding.etPhoneNumberLogin.setText(phoneNumber)
        binding.btnNext.setBackgroundResource(R.drawable.style_login_btn)
        binding.viewUnderLineLogin.setBackgroundColor(requireActivity().getColor(R.color.mainYellow))

        //전화번호 입력 감지
        binding.etPhoneNumberLogin.addTextChangedListener(textChecker)

        //SendCode 클릭 시 동작
        binding.btnNext.setOnClickListener {

            if (phoneNumber == "") {
                phoneNumber = binding.etPhoneNumberLogin.text.toString()
            }
            if (phoneNumber != binding.etPhoneNumberLogin.text.toString()) {
                Toast.makeText(context, "현재 디바이스 기기의 번호와 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else {
                view?.findNavController()
                    ?.navigate(R.id.action_smsVerificationFragment_to_paymentPasswordFragment)
            }

        }

        return binding.root
    }

    @SuppressLint("MissingPermission")
    fun getPhoneNumber() {
        val msg = requireContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (msg.line1Number != null) {
            phoneNumber = msg.line1Number.toString()
            phoneCountry = msg.simCountryIso.toUpperCase()

            Log.d("TAG-로그인 정보 번호/국가", phoneNumber + "/" + phoneCountry)
        } else {
            Log.d("TAG-번호가져오기", "없음")
        }
    }

    //국가코드 가져오기
    open fun GetCountryZipCode(): String? {
        var CountryID = ""
        var CountryZipCode = ""
        CountryID = phoneCountry
        val rl = this.resources.getStringArray(R.array.countryCode)
        for (i in rl.indices) {
            val g = rl[i].split(",").toTypedArray()
            if (g[1].trim { it <= ' ' } == CountryID.trim { it <= ' ' }) {
                CountryZipCode = g[0]
                break
            }
        }
//        Log.d("TAG-국가코드",CountryZipCode)
        return CountryZipCode
    }

    //edtiText 값 확인 버튼 활성화
    private val textChecker = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val number = p0.toString().trim()

            if (p0.toString().isNotEmpty()) {
                binding.btnNext.isClickable = true
                binding.viewUnderLineLogin.setBackgroundColor(requireActivity().getColor(R.color.mainYellow))
                binding.btnNext.setBackgroundResource(R.drawable.style_login_btn)
//                Log.d("TAG-번호입력",number)

            } else {
                binding.btnNext.isClickable = false
                binding.viewUnderLineLogin.setBackgroundColor(requireActivity().getColor(R.color.gray))
                binding.btnNext.setBackgroundResource(R.drawable.style_disable_btn)
//                Log.d("TAG-번호미입력",p0.toString())
            }

        }

        override fun afterTextChanged(p0: Editable?) {}

    }
}