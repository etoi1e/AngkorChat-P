package com.example.feed.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.feed.screen.FeedEditor
import com.example.feed.ui.theme.AngKorChatProtoFeedTheme
import com.example.model.EditorVisibility
import com.example.model.Feed

class OpenFeedEditorContract : ActivityResultContract<Feed?, Int>() {
    override fun createIntent(context: Context, input: Feed?): Intent {
        return Intent(context, FeedEditorActivity::class.java).apply {
            putExtra(INTENT_FEED, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int {
        return resultCode
    }

    companion object {
        const val INTENT_FEED = "INTENT_FEED"
    }

}

class FeedEditorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val _quote = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { //API 33
            intent.extras?.getParcelable(OpenFeedEditorContract.INTENT_FEED, Feed::class.java)
        } else {
            intent.extras?.getParcelable(OpenFeedEditorContract.INTENT_FEED) as Feed?
        }

        setContent {
            var quote by remember {
                mutableStateOf(_quote)
            }
            var caption by remember {
                mutableStateOf("")
            }

            AngKorChatProtoFeedTheme {
                FeedEditor(
                    caption = caption,
                    visibility = EditorVisibility.Public,
                    quote = quote,
                    onCaptionChange = { caption = it },
                    onEditorVisibilityChange = {},
                    onPostClick = {},
                    onRemoveQuote = { quote = null },
                    onBack = { finish() }
                )
            }
        }
    }
}