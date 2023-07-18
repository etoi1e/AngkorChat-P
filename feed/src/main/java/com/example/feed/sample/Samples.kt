package com.example.feed.sample

import com.example.feed.R
import com.example.model.Comment
import com.example.model.Feed
import com.example.model.Notification
import com.example.model.StoryCover
import com.example.model.User

val sampleUsers = listOf(
    User(
        id = 0,
        profileResId = R.drawable.blackpink,
        name = "BLACKPINK",
        subtitle = "Subtitle"
    ),
    User(
        id = 1,
        profileResId = R.drawable.profile_1,
        name = "Joy",
        subtitle = "Subtitle"
    ),
    User(
        id = 2,
        profileResId = R.drawable.profile_2,
        name = "Jennie",
        subtitle = "Subtitle"
    ),
    User(
        id = 3,
        profileResId = R.drawable.profile_1,
        name = "Cindy Crystal",
        subtitle = "Subtitle"
    ),
    User(
        id = 4,
        profileResId = R.drawable.profile_1,
        name = "Username",
        subtitle = "Subtitle"
    )
)

val sampleComment = Comment(
    id = 1,
    user = sampleUsers[0],
    elapsedTime = "1h",
    content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
    likes = 3,
    isLike = false,
    replies = listOf(
        Comment(
            id = 2,
            user = sampleUsers[1],
            elapsedTime = "1h",
            content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
            likes = 32,
            isLike = false,
            replies = listOf(
                Comment(
                    id = 4,
                    user = sampleUsers[2],
                    elapsedTime = "1h",
                    content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
                    likes = 3,
                    isLike = false,
                    replies = null
                )
            )
        ),
        Comment(
            id = 3,
            user = sampleUsers[3],
            elapsedTime = "1h",
            content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
            likes = 3,
            isLike = false,
            replies = null
        )
    )
)

val sampleComments = listOf(
    Comment(
        id = 2,
        user = sampleUsers[1],
        elapsedTime = "1h",
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        likes = 32,
        isLike = false,
        replies = listOf(
            Comment(
                id = 4,
                user = sampleUsers[2],
                elapsedTime = "1h",
                content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
                likes = 3,
                isLike = false,
                replies = null
            )
        )
    ),
    Comment(
        id = 2,
        user = sampleUsers[1],
        elapsedTime = "1h",
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        likes = 32,
        isLike = false,
        replies = listOf(
            Comment(
                id = 4,
                user = sampleUsers[2],
                elapsedTime = "1h",
                content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
                likes = 3,
                isLike = false,
                replies = null
            )
        )
    ),
    sampleComment,
    Comment(
        id = 2,
        user = sampleUsers[1],
        elapsedTime = "1h",
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        likes = 32,
        isLike = false,
        replies = listOf(
            Comment(
                id = 4,
                user = sampleUsers[2],
                elapsedTime = "1h",
                content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
                likes = 3,
                isLike = false,
                replies = null
            )
        )
    )
)

val sampleFeeds = listOf(
    Feed(
        id = 0,
        user = sampleUsers[0],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        photos = listOf(R.drawable.blackpink, R.drawable.profile_1),
        map = null,
        withUser = sampleUsers,
        hearts = 1234,
        replys = 10,
        isFavorite = true,
        quote = null,
        comments = sampleComments
    ),
    Feed(
        id = 1,
        user = sampleUsers[0],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        photos = listOf(R.drawable.blackpink, R.drawable.profile_1),
        hearts = 1000123,
        replys = 10,
        isFavorite = false
    ),
    Feed(
        id = 2,
        user = sampleUsers[1],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        hearts = 1003,
        replys = 10,
        isFavorite = false
    ),
    Feed(
        id = 3,
        user = sampleUsers[0],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        photos = listOf(R.drawable.blackpink, R.drawable.profile_1),
        map = null,
        withUser = sampleUsers,
        hearts = 1234,
        replys = 10,
        isFavorite = true,
        quote = null,
    ),
    Feed(
        id = 4,
        user = sampleUsers[0],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        photos = listOf(R.drawable.blackpink, R.drawable.profile_1),
        hearts = 1000123,
        replys = 10,
        isFavorite = false
    ),
    Feed(
        id = 5,
        user = sampleUsers[1],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        hearts = 1003,
        replys = 10,
        isFavorite = false
    )
)

val sampleNotifications = listOf(
    Notification(
        id = 0,
        users = sampleUsers.shuffled(),
        message = "liked your post",
        elapsedTimeMinute = 5,
        image = null
    ),
    Notification(
        id = 1,
        users = sampleUsers.take(1),
        message = "liked your photos",
        elapsedTimeMinute = 10,
        image = R.drawable.blackpink
    ),
    Notification(
        id = 2,
        users = sampleUsers.take(1),
        message = "liked your photos",
        elapsedTimeMinute = 10,
        image = R.drawable.profile_2
    ),
    Notification(
        id = 3,
        users = sampleUsers.shuffled(),
        message = "liked your post",
        elapsedTimeMinute = 5,
        image = null
    ),
)

val sampleStoryCovers = listOf(
    StoryCover(
        id = 0,
        backgroundResId = R.drawable.profile_1,
        title = "Name"
    ),
    StoryCover(
        id = 1,
        backgroundResId = R.drawable.profile_2,
        title = "Name"
    ),
    StoryCover(
        id = 2,
        backgroundResId = R.drawable.profile_1,
        title = "Name"
    ),
    StoryCover(
        id = 3,
        backgroundResId = R.drawable.profile_2,
        title = "Name"
    ),
)