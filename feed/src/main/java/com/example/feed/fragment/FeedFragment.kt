package com.example.feed.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.example.feed.sample.sampleFeeds
import com.example.feed.sample.sampleStoryCovers
import com.example.feed.sample.sampleUsers
import com.example.feed.screen.FeedScreen

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            //setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                FeedScreen(
                    feeds = sampleFeeds,
                    storyCovers = sampleStoryCovers,
                    suggestedUsers = sampleUsers,
                    onBack = { requireActivity().finish() }
                )
            }
        }
    }
}