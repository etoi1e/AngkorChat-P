package com.example.angkorchatproto.friends.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.angkorchatproto.UserVO
import com.example.angkorchatproto.databinding.FragmentResultBinding
import com.example.angkorchatproto.utils.FBdataBase

class ResultFragment : Fragment() {
    lateinit var binding: FragmentResultBinding
    private var userVO: UserVO? = null
    private var userAdded: Boolean? = false
    private var userNumber: String? = null
    private val friendRef = FBdataBase.getFriendRef()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userVO = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("user", UserVO::class.java) as UserVO
        } else {
            arguments?.getSerializable("user") as UserVO
        }

        if (userVO == null) {
            view?.findNavController()?.popBackStack()
        }

        userAdded = arguments?.getBoolean("phoneNumberAdded")
        userNumber = arguments?.getString("userNumber")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        binding.tvUserName.text = userVO?.name

        if (userAdded == false) {
            binding.btnAdd.isEnabled = true
        } else {
            binding.btnAdd.isEnabled = false
            binding.btnAdd.text = "Added"
        }

        binding.btnAdd.setOnClickListener {
            if (userNumber != null) {
                friendRef.child(userNumber!!).child(userVO?.phone!!)
                    .setValue(userVO)
                requireActivity().finish()
            }
        }
        binding.ivBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        return binding.root
    }
}