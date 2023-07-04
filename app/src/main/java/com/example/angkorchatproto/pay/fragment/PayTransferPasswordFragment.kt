package com.example.angkorchatproto.pay.fragment

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.*
import com.example.angkorchatproto.dialog.CustomDialog
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel
import com.example.angkorchatproto.pay.room.AccountInfo
import com.example.angkorchatproto.pay.room.AppDatabase
import kr.co.kdnavien.naviensmart.presentation.custom.DialogNegativeBtnListener
import kr.co.kdnavien.naviensmart.presentation.custom.DialogPositiveBtnListener
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class PayTransferPasswordFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPayTransferPasswordBinding
    lateinit var etArray: ArrayList<EditText>
    lateinit var vArray: ArrayList<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayTransferPasswordBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        //현재 사용자 번호 불러오기
        val shared = requireContext().getSharedPreferences("loginNumber", 0)
        val myNumber = shared.getString("userNumber", "").toString()




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

        var pinNumber = ""

        for (i in 0 until etArray.size) {
            var pin = etArray[i].text.toString()
            pinNumber += pin
        }





        binding.btnNext.setOnClickListener {

            setEtTextChangeListener()
            onDeleteListener()

            var pinNumber = ""

            for (i in 0 until etArray.size) {
                var pin = etArray[i].text.toString()
                pinNumber += pin
            }

            //계좌 생성
            val db = AppDatabase.getInstance(requireContext().applicationContext)

            if (db != null) {

                val accountNumber = db.paymentDao().getAccountNumber(myNumber)
                val transferNumber = randomNumber(10)
                val time = LocalDateTime.now().toString()

                /**사용자가 원래 가지고 있던 잔액*/
                val userAmount = db.paymentDao().getAmount(accountNumber)

                /**송금하려는 금액*/
                val getAmount = requireArguments().getString("amount")
                val amount = getAmount!!.toInt()

                //잔액비교
                if (userAmount >= amount) {
                    val newAccount =
                        AccountInfo(
                            0,
                            transferNumber,
                            accountNumber,
                            myNumber,
                            0,
                            -amount,
                            "used_point",
                            "received",
                            "",
                            "Angkor",
                            "Angkor",
                            time
                        )

                    db.paymentDao().insertAccount(newAccount)

                    val bundle = bundleOf("transferNumber" to transferNumber)

                    view?.findNavController()
                        ?.navigate(R.id.action_payTransferPasswordFragment_to_transferCompleteFragment,bundle)
                }else{
                    //잔액부족

                    CustomDialog.create(requireContext())

                    /**유저이름들어가는 부분*/
                    val title = "Insufficient Balance"
                    val spannable = SpannableStringBuilder(title)
                    val color = requireActivity().getColor(R.color.red)
                    spannable.setSpan(
                        ForegroundColorSpan(color),
                        0,
                        title.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    CustomDialog.create(requireContext())
                        ?.setTitle(spannable)
                        ?.setDesc(SpannableStringBuilder("Your Amount : ${userAmount}$"))
                        ?.setCancelable(true)
                        ?.setPositiveButtonText(SpannableStringBuilder("Close"))
                        ?.setPositiveBtnListener(object : DialogPositiveBtnListener {
                            override fun confirm(division: Int) {
                                view?.findNavController()?.popBackStack()
                            }
                        })
                        ?.showOneButton()
                }




            }


        }

        setEtTextChangeListener()
        onDeleteListener()

        return binding.root
    }

    fun randomNumber(length: Int): String {
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
                                    binding.btnNext.setBackgroundResource(R.drawable.style_login_btn)
                            }
                        }
                    }
                }
            )
        }
    }

}