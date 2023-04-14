package com.example.angkorchatproto


import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.angkorchatproto.Friends.FriendsFragment
import com.example.angkorchatproto.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

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
// 어디에있어용

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
                        FriendsFragment()
                    ).commit()
                }

                R.id.tab3 ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        FriendsFragment()
                    ).commit()
                }

            }

            true


        }
    }
}