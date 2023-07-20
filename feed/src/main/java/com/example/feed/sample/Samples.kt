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
        profileResId = R.drawable.profile_3,
        name = "Cindy Crystal",
        subtitle = "Subtitle"
    ),
    User(
        id = 4,
        profileResId = R.drawable.profile_3,
        name = "Username",
        subtitle = "Subtitle"
    ),
    User(
        id = 5,
        profileResId = R.drawable.profile_2,
        name = "leomessi",
        subtitle = "Subtitle"
    ),
    User(
        id = 6,
        profileResId = R.drawable.egg,
        name = "world_record_egg",
        subtitle = "Subtitle"
    ),
    User(
        id = 7,
        profileResId = R.drawable.zhonya,
        name = "Zhonya's Hourglass",
        subtitle = "Subtitle"
    ),
    User(
        id = 8,
        profileResId = R.drawable.profile_4,
        name = "cristiano",
        subtitle = "Subtitle"
    ),
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
        user = sampleUsers[5],
        authorInfo = "Lusail Stadium ・ 1h ・ \uD83C\uDF0E",
        content = """
            CAMPEONES DEL MUNDO!!!!!!! 🌎🏆

            Tantas veces lo soñé, tanto lo deseaba que aún no caigo, no me lo puedo creer……

            Muchas gracias a mi familia, a todos los que me apoyan y también a todos los que creyeron en nosotros. Demostramos una vez más que los argentinos cuando luchamos juntos y unidos somos capaces de conseguir lo que nos propongamos. El mérito es de este grupo, que está por encima de las individualidades, es la fuerza de todos peleando por un mismo sueño que también era el de todos los argentinos… Lo logramos!!!

            VAMOS ARGENTINA CARAJO!!!!! 🙌🏻🙌🏻

            Nos estamos viendo muy pronto… 🇦🇷🇦🇷🇦🇷
        """.trimIndent(),
        photos = listOf(R.drawable.photos_1_1, R.drawable.photos_1_2, R.drawable.photos_1_3),
        map = null,
        withUser = sampleUsers,
        hearts = 123457,
        replys = 2342,
        isFavorite = true,
        quote = null,
        comments = sampleComments
    ),
    Feed(
        id = 1,
        user = sampleUsers[0],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        photos = listOf(R.drawable.blackpink, R.drawable.profile_3),
        hearts = 1000123,
        replys = 10,
        isFavorite = false
    ),
    Feed(
        id = 2,
        user = sampleUsers[6],
        authorInfo = "1h",
        content = """
            Let’s set a world record together and get the most liked post on Instagram. Beating the current world record held by Kylie Jenner (18 million)! We got this 🙌

            #LikeTheEgg #EggSoldiers #EggGang
        """.trimIndent(),
        hearts = 1003,
        replys = 10,
        isFavorite = false
    ),
    Feed(
        id = 3,
        user = sampleUsers[3],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        photos = listOf(R.drawable.profile_2, R.drawable.profile_3),
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
        photos = listOf(R.drawable.profile_1, R.drawable.profile_3),
        hearts = 1000123,
        replys = 10,
        isFavorite = false
    ),
    Feed(
        id = 7,
        user = sampleUsers[8],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        content = """
            Victory is a State of Mind. A long tradition of crafting trunks photographed by @annieleibovitz for @louisvuitton
        """.trimIndent(),
        photos = listOf(R.drawable.profile_4),
        hearts = 1000123,
        replys = 10,
        isFavorite = false
    ),
    Feed(
        id = 8,
        user = sampleUsers[1],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        hearts = 1003,
        replys = 10,
        isFavorite = false
    ),
    Feed(
        id = 9,
        user = sampleUsers[3],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        content = """
            11K FOLLOWERS Giveaway! You can WIN a USD${'$'}75 gift card to use on anything you want from @thedaydesigner! Swipe to see the
            one I have myself! It's so beautiful!
            4 STEPS to enter:
            [1] Follow @positivethoughts.ca and
            @thedavdesigner (unfollowing = vou will be
            disqualified from giveaways) [2] Tag 1 friend and tell them one of your New Years
            resolution or something nice! Each tag is one entry! [3 Bonus 5 entries: share in story for 24 hours and
            tag me 4 Bonus 10 entries: visit my YouTube (link in bio) and watch the January plan with me giveaway video
            there and follow some easy steps there! If you don't win, you can still buy something at 10% off with my code POSITIVETHOUGHTS10 until March
            31, 2022! Stickers are from @prettylittlestudios! They are super
            cute gift boxes! Use POSITIVE10 for 10% off! No duplicate tags / no tagging vourself / no
            spamming / no giveaway accounts.
            INFO: W Giveaway ends Sunday January 9th at mid-night
            (12am EST).
            Winner will be chosen at random! V This promotion is in no way sponsored,
            administered or associated with Instagram, Inc.
        """.trimIndent(),
        hearts = 1003,
        replys = 10,
        isFavorite = false
    ),
    Feed(
        id = 6,
        user = sampleUsers[7],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        photos = listOf(R.drawable.zhonya_photo),
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        hearts = 3000,
        replys = 750,
        isFavorite = false
    ),
    Feed(
        id = 20,
        user = sampleUsers[5],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        photos = listOf(R.drawable.sample_image_2),
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        hearts = 3000,
        replys = 750,
        isFavorite = false
    ),
    Feed(
        id = 21,
        user = sampleUsers[4],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        photos = listOf(R.drawable.sample_image_1),
        hearts = 3000,
        replys = 750,
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
    StoryCover(id = 1, backgroundResId = R.drawable.reels_sample_1, title = "Name"),
    StoryCover(id = 2, backgroundResId = R.drawable.reels_sample_2, title = "Name"),
    StoryCover(id = 3, backgroundResId = R.drawable.reels_sample_3, title = "Name"),
    StoryCover(id = 4, backgroundResId = R.drawable.reels_sample_4, title = "Name"),
    StoryCover(id = 5, backgroundResId = R.drawable.reels_sample_5, title = "Name"),
    StoryCover(id = 6, backgroundResId = R.drawable.reels_sample_6, title = "Name"),
)