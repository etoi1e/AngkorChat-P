package com.example.angkorwebtoonprototype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.angkorwebtoonprototype.detail.WebtoonDetail

class AngkorWebtoonDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val ep = intent.getStringExtra("ep").toString()
            WebtoonDetail(name = ep, { this@AngkorWebtoonDetailActivity.finish() })
        }
    }
}