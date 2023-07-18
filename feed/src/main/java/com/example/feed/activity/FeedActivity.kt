package com.example.feed.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.feed.sample.sampleFeeds
import com.example.feed.sample.sampleStoryCovers
import com.example.feed.sample.sampleUsers
import com.example.feed.screen.FeedScreen

class FeedActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FeedScreen(
                feeds = sampleFeeds,
                storyCovers = sampleStoryCovers,
                suggestedUsers = sampleUsers,
                onBack = { finish() }
            )
        }
    }
}