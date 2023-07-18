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
import com.example.feed.screen.CheckInScreen
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import java.util.ArrayList
import java.util.Locale

class OpenCheckInContract : ActivityResultContract<Unit, Place?>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, CheckInActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Place? {
        if (resultCode != Activity.RESULT_OK) return null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { //API 33
            return intent?.extras?.getParcelable(INTENT_CHECK_IN, Place::class.java)
        }

        return intent?.extras?.getParcelable(INTENT_CHECK_IN)
    }

    companion object {
        const val INTENT_CHECK_IN = "INTENT_CHECK_IN"
    }
}

class CheckInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Places.isInitialized()) {
            Places.initialize(
                applicationContext,
                "AIzaSyAt1BEapqomdK7Gdw5EqAin-g8zX6yqGMo",
                Locale.KOREA
            )
        }

        setContent {
            CheckInScreen(
                onBack = { finish() },
                onCheckIn = {
                    setResult(
                        RESULT_OK,
                        Intent().putExtra(
                            OpenCheckInContract.INTENT_CHECK_IN,
                            it
                        )
                    )
                    finish() }
            )

        }
    }
}