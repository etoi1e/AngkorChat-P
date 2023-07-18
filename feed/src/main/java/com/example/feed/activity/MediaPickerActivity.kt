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
import com.example.feed.screen.MediaPicker
import java.util.ArrayList

class OpenAngkorMediaPickerContract : ActivityResultContract<Unit, List<Uri>>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, MediaPickerActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<Uri> {
        if (resultCode != Activity.RESULT_OK) return emptyList()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { //API 33
            return intent?.extras?.getParcelableArrayList(INTENT_MEDIA_PICK, Uri::class.java)
                ?: emptyList()
        }

        return intent?.extras?.getParcelableArrayList(INTENT_MEDIA_PICK) ?: emptyList()
    }

    companion object {
        const val INTENT_MEDIA_PICK = "INTENT_MEDIA_PICK"
    }
}

class MediaPickerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MediaPicker(
                onAddClick = {
                    setResult(
                        RESULT_OK,
                        Intent().putParcelableArrayListExtra(
                            OpenAngkorMediaPickerContract.INTENT_MEDIA_PICK,
                            ArrayList(it)
                        )
                    )
                    finish()
                },
                onBack = {
                    finish()
                }
            )
        }
    }
}