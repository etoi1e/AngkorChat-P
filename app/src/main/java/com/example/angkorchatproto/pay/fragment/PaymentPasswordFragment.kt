package com.example.angkorchatproto.pay.fragment

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentPaymentPasswordBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel
import com.example.angkorchatproto.pay.room.AccountInfo
import com.example.angkorchatproto.pay.room.AppDatabase
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class PaymentPasswordFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPaymentPasswordBinding
    lateinit var etArray: ArrayList<EditText>
    lateinit var vArray: ArrayList<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentPasswordBinding.inflate(inflater, container, false)

        val title = arguments?.getString("title").toString()

        binding.textView50.text = title

        binding.btnBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.ivClose.setOnClickListener {
            requireActivity().finish()
        }
        etArray = arrayListOf(
            binding.etOne,
            binding.etTwo,
            binding.etThree,
            binding.etFour,
            binding.etFive,
            binding.etSix
        )
        vArray = arrayListOf(
            binding.vOne,
            binding.vTwo,
            binding.vThree,
            binding.vFour,
            binding.vFive,
            binding.vSix
        )

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()

        //이전 페이지 확인
        val checkTopUp = requireContext().getSharedPreferences("checkTopUp", 0)
        val prePage = checkTopUp.getBoolean("checkTopUp", false)

        if (prePage) {
            binding.textView56.text == "Top Up"
        }

        binding.btnNext.setOnClickListener {
            if (binding.textView56.text == "Enter Payment Password") {
                onDeleteListener()
                setEtTextChangeListener()

                var pinNumber = ""

                for (i in 0 until etArray.size) {
                    var pin = etArray[i].text.toString()
                    pinNumber += pin
                }

                //계좌 생성
                val db = AppDatabase.getInstance(requireContext().applicationContext)

                if (db != null) {
                    val newAccountNumber = formatString(16)
                    val time = LocalDateTime.now().toString()

                    val newAccount =
                        AccountInfo(0,"0", newAccountNumber, myNumber, 3000, 0, "new","", "", "", "", time)

                    db.paymentDao().insertAccount(newAccount)
                    Log.d("TAG-계좌생성", newAccountNumber)
                    Log.d("TAG-계좌생성", newAccount.toString())
                }

                binding.textView56.text = "Enter Payment Password Again"
            } else {
                view?.findNavController()
                    ?.navigate(R.id.action_paymentPasswordFragment_to_payMainFragment)
            }
        }
        onDeleteListener()
        setEtTextChangeListener()
        return binding.root
    }

    fun randomNumber(length:Int): String {
        val randomNumberLength = length
        val allowedChars = "0123456789"
        val random = Random(System.currentTimeMillis())

        return buildString {
            repeat(randomNumberLength) {
                val randomIndex = random.nextInt(allowedChars.length)
                append(allowedChars[randomIndex])
            }
        }
    }

    fun formatString(length: Int): String {

        val input = randomNumber(length)
        val formattedString = StringBuilder()
        val chunkSize = 4 // 변경된 문자열의 청크 크기

        for (i in input.indices) {
            formattedString.append(input[i])

            // 청크 크기에 도달할 때마다 "-" 추가
            if ((i + 1) % chunkSize == 0 && i != input.length - 1) {
                formattedString.append("-")
            }
        }

        return formattedString.toString()
    }

    private fun onDeleteListener() {
        for (i in 0 until etArray.size) {
            etArray[i].setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (i == 0) {
                        vArray[i].background =
                            requireActivity().getDrawable(R.color.colorLightGray)
                        etArray[i].text = null
                    } else {
                        vArray[i].background =
                            requireActivity().getDrawable(R.color.colorLightGray)
                        etArray[i].text = null
                        etArray[i - 1].requestFocus()
                    }
                }
                false
            }
        }
    }

    private fun setEtTextChangeListener() {
        for (i in 0 until etArray.size) {
            etArray[i].addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        Log.d("beforeTextChanged", "$start $count $after")
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (!s.isNullOrBlank() && !s.matches(Regex("[0-9]+"))) {
                            etArray[i].setText(s.filter { it.isDigit() })
                            etArray[i].setSelection(etArray[i].text.length) // 커서 위치를 마지막으로 이동
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (i == 0) {
                            if (etArray[i].length() == 1) {
                                vArray[i].background =
                                    requireActivity().getDrawable(R.color.mainYellow)
                                etArray[i + 1].requestFocus()
                                etArray[i + 1].text = null
                            } else {
                                vArray[i].background =
                                    requireActivity().getDrawable(R.color.colorLightGray)
                            }
                        } else {
                            if (etArray[i].length() == 1) {
                                if (i == etArray.size - 1) {
                                    vArray[i].background =
                                        requireActivity().getDrawable(R.color.mainYellow)
                                } else {
                                    vArray[i].background =
                                        requireActivity().getDrawable(R.color.mainYellow)
                                    etArray[i + 1].requestFocus()
                                    etArray[i + 1].text = null
                                }
                            } else {
                                vArray[i].background =
                                    requireActivity().getDrawable(R.color.colorLightGray)
                            }
                        }
                    }
                }
            )
        }
    }
}