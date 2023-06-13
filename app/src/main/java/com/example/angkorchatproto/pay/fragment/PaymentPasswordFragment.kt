package com.example.angkorchatproto.pay.fragment

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
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.angkorchatproto.R
import com.example.angkorchatproto.databinding.FragmentJoinBinding
import com.example.angkorchatproto.databinding.FragmentPaymentPasswordBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel
import org.w3c.dom.Text

class PaymentPasswordFragment : Fragment() {
    private val activityViewModel: PayViewModel? by activityViewModels()
    lateinit var binding: FragmentPaymentPasswordBinding
    lateinit var etArray: ArrayList<EditText>
    lateinit var vArray: ArrayList<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentPasswordBinding.inflate(inflater, container, false)
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

        binding.btnNext.setOnClickListener {
            if (binding.textView56.text == "Enter Payment Password") {
                onDeleteListener()
                setEtTextChangeListener()
                binding.textView56.text = "Enter Payment Password Again"
            } else {
                view?.findNavController()?.navigate(R.id.action_paymentPasswordFragment_to_payMainFragment)
            }
        }
        onDeleteListener()
        setEtTextChangeListener()
        return binding.root
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
            etArray[i].addTextChangedListener (
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        Log.d("beforeTextChanged","$start $count $after")
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
                                if (i == etArray.size-1) {
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