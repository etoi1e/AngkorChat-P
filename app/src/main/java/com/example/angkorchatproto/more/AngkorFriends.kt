package com.example.angkorchatproto.more

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.angkorchatproto.R


class AngkorFriends : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_angkor_friends)

        val webView = findViewById<WebView>(R.id.webView)

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER

        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://angkorfriends.com/")

        val actionBar = supportActionBar
        actionBar?.hide()





    }
}