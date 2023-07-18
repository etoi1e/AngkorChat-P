package com.example.feed.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import com.example.feed.screen.FeedDetailScreen
import com.example.model.Feed

class OpenFeedDetailContract : ActivityResultContract<Feed, Unit>() {
    override fun createIntent(context: Context, input: Feed): Intent {
        return Intent(context, FeedDetailActivity::class.java).apply {
            putExtra(INTENT_FEED_DETAIL, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?) {

    }

    companion object {
        const val INTENT_FEED_DETAIL = "INTENT_FEED_DETAIL"
    }
}

class FeedDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val feed = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { //API 33
            intent.extras?.getParcelable(OpenFeedDetailContract.INTENT_FEED_DETAIL, Feed::class.java)
        } else {
            intent.extras?.getParcelable(OpenFeedDetailContract.INTENT_FEED_DETAIL) as Feed?
        }

        if (feed == null) {
            Toast.makeText(this, "Invalid access", Toast.LENGTH_SHORT).show()
            finish()
        }

        setContent {
            FeedDetailScreen(feed = feed!!) {
                finish()
            }
        }
    }
}