package com.example.angkorchatproto


import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.digitalangkor.mobility.activity.AngkorMobilityActivity
import com.digitalangkor.mobility.screen.AngkorMobilityMainScreen
import com.example.angkorchatproto.base.BaseActivity
import com.example.angkorchatproto.chat.ChatFragment
import com.example.angkorchatproto.friends.FriendsFragment
import com.example.angkorchatproto.databinding.ActivityMainBinding
import com.example.angkorchatproto.more.MoreFragment
import com.example.angkorchatproto.shopping.ShopFragment
import com.example.feed.fragment.FeedFragment


class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, AngkorMobilityActivity::class.java))
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
                        FeedFragment()
                    ).commit()
                }

                R.id.tab4 ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        ShopFragment()
                    ).commit()
                }

                R.id.tab5 ->{
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