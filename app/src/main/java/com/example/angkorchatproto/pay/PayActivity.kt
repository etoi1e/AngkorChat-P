package com.example.angkorchatproto.pay

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.angkorchatproto.R
import com.example.angkorchatproto.base.BaseActivity
import com.example.angkorchatproto.databinding.ActivityPayBinding
import com.example.angkorchatproto.emojistore.viewmodel.PayViewModel

/**
 * Package Name : com.example.angkorchatproto.pay
 * Class Name : PayJoinActivity
 * Description :
 * Created by de5ember on 2023/06/12.
 */
class PayActivity: BaseActivity() {
    lateinit var binding: ActivityPayBinding
    private var mNavController: NavController? = null
    private var mNavHostFragment: NavHostFragment? = null
    private var viewModel: PayViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[PayViewModel::class.java]
        viewModel?.payType = intent.getStringExtra("type")
        mNavHostFragment = supportFragmentManager.findFragmentById(R.id.pay_container) as NavHostFragment
        mNavController = mNavHostFragment?.navController
        setContentView(binding.root)
    }

    override fun onBackPressed() {
    }
}