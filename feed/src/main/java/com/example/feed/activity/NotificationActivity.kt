package com.example.feed.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import com.example.feed.sample.sampleNotifications
import com.example.feed.screen.NotificationScreen

class OpenNotificationContract : ActivityResultContract<Unit, Unit>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, NotificationActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?) {
    }
}

class NotificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotificationScreen(notifications = sampleNotifications) {
                finish()
            }
        }
    }
}