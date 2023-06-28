package com.example.angkorchatproto.pay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.angkorchatproto.databinding.ActivityHistorySearchBinding

class HistorySearchActivity : AppCompatActivity() {

    lateinit var binding : ActivityHistorySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistorySearchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



    }
}