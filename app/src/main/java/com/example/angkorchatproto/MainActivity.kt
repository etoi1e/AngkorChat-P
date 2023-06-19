package com.example.angkorchatproto


import android.os.Bundle
import com.example.angkorchatproto.base.BaseActivity
import com.example.angkorchatproto.chat.ChatFragment
import com.example.angkorchatproto.friends.FriendsFragment
import com.example.angkorchatproto.databinding.ActivityMainBinding
import com.example.angkorchatproto.more.MoreFragment


class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigationView.setItemIconTintList(null);


        supportFragmentManager.beginTransaction().replace(
            R.id.frameLayout,
            FriendsFragment()
        ).commit()


        binding.bottomNavigationView.setOnItemSelectedListener{
                item ->
            when(item.itemId){

                R.id.tab1 ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        FriendsFragment()
                    ).commit()
                }


                R.id.tab2 ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        ChatFragment()
                    ).commit()
                }

                R.id.tab3 ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        MoreFragment()
                    ).commit()
                }

            }

            true


        }
    }

}