package com.example.feed.sample

import com.example.feed.R
import com.example.model.Comment
import com.example.model.Feed
import com.example.model.Notification
import com.example.model.StoryCover
import com.example.model.User
import kotlin.random.Random

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


val sampleMessages = listOf(
    """25 years later, she still regretted that specific moment.""",
    """You bite up because of your lower jaw.""",
    """I'd rather be a bird than a fish.""",
    """The ants enjoyed the barbecue more than the family.""",
    """Pat ordered a ghost pepper pie.""",
    """I received a heavy fine but it failed to crush my spirit.""",
    """Too many prisons have become early coffins.""",
    """She thought there'd be sufficient time if she hid her watch.""",
    """The beauty of the sunset was obscured by the industrial cranes.""",
    """David proudly graduated from high school top of his class at age 97.""",
    """The sky is clear; the stars are twinkling.""",
    """The irony of the situation wasn't lost on anyone in the room.""",
    """Sometimes you have to just give up and win by cheating.""",
    """You're good at English when you know the difference between a man eating chicken and a man-eating chicken.""",
    """He had a vague sense that trees gave birth to dinosaurs.""",
    """As the rental car rolled to a stop on the dark road, her fear increased by the moment.""",
    """This book is sure to liquefy your brain.""",
    """The shark-infested South Pine channel was the only way in or out.""",
    """He is good at eating pickles and telling women about his emotional problems.""",
    """Watching the geriatric men’s softball team brought back memories of 3 yr olds playing t-ball.""",
    """Nothing seemed out of place except the washing machine in the bar.""",
    """The beach was crowded with snow leopards.""",
    """It's important to remember to be aware of rampaging grizzly bears.""",
    """Pink horses galloped across the sea.""",
    """The sunblock was handed to the girl before practice, but the burned skin was proof she did not apply it.""",
    """In that instant, everything changed.""",
    """He had unknowingly taken up sleepwalking as a nighttime hobby.""",
    """Today we gathered moss for my uncle's wedding.""",
    """He had a hidden stash underneath the floorboards in the back room of the house.""",
    """The pet shop stocks everything you need to keep your anaconda happy.""",
    """The glacier came alive as the climbers hiked closer.""",
    """My Mum tries to be cool by saying that she likes all the same things that I do.""",
    """Poison ivy grew through the fence they said was impenetrable.""",
    """Behind the window was a reflection that only instilled fear.""",
    """Baby wipes are made of chocolate stardust.""",
    """Last Friday I saw a spotted striped blue worm shake hands with a legless lizard.""",
    """The estate agent quickly marked out his territory on the dance floor.""",
    """Mary plays the piano.""",
    """If my calculator had a history, it would be more embarrassing than my browser history.""",
    """It turns out you don't need all that stuff you insisted you did."""
)

var commentId = -1

fun randomCommentGenerator(postId: Int, reply: Float = 1f): Sequence<Comment> = generateSequence {
    val random = Random(postId)

    commentId++

    Comment(
        id = commentId,
        user = sampleUsers.random(),
        elapsedTime = "1h",
        content = sampleMessages.random(),
        likes = random.nextInt(100),
        isLike = random.nextBoolean(),
        replies = if(random.nextFloat() < reply) {
            randomCommentGenerator(1000000 + commentId, reply / 10).take(Random.nextInt(5)).toList()
        } else {
            null
        }
    )
}


var feedId = 100

val photos = listOf(
    R.drawable.blackpink,
    R.drawable.egg,
    R.drawable.lemon,
    R.drawable.photos_1_1,
    R.drawable.photos_1_2,
    R.drawable.photos_1_3,
    R.drawable.profile_1,
    R.drawable.profile_2,
    R.drawable.profile_3,
    R.drawable.profile_4,
    R.drawable.reels_sample_1,
    R.drawable.reels_sample_2,
    R.drawable.reels_sample_3,
    R.drawable.reels_sample_4,
    R.drawable.reels_sample_5,
    R.drawable.reels_sample_6,
    R.drawable.story_1,
    R.drawable.zhonya_photo,
    R.drawable.sample_image_1,
    R.drawable.sample_image_2,
    R.drawable.sample_photos_1,
    R.drawable.sample_photos_2,
    R.drawable.sample_photos_3,
    R.drawable.sample_photos_4,
    R.drawable.sample_photos_5,
    R.drawable.sample_photos_6,
    R.drawable.sample_photos_7,
    R.drawable.sample_photos_8,
    R.drawable.sample_photos_9,
    R.drawable.sample_photos_10,
    R.drawable.sample_photos_11,
    R.drawable.sample_photos_12,
    R.drawable.sample_photos_13,
    R.drawable.sample_photos_14,
    R.drawable.sample_photos_15,
    R.drawable.sample_photos_16,
    R.drawable.sample_photos_17,
    R.drawable.sample_photos_18,
    R.drawable.sample_photos_19,
    R.drawable.sample_photos_20,
    R.drawable.sample_photos_21,
    R.drawable.sample_photos_22,
    R.drawable.sample_photos_23,
)

fun randomFeedGenerator(): Sequence<Feed> = generateSequence {
    Feed(
        id = feedId++,
        user = sampleUsers.random(),
        authorInfo = "Lusail Stadium ・ 1h ・ \uD83C\uDF0E",
        content = sampleMessages.shuffled().take(Random.nextInt(10)).joinToString("\n"),
        photos = photos.shuffled().take(Random.nextInt(5)),
        map = null,
        withUser = sampleUsers.shuffled().take(Random.nextInt(3)),
        hearts = Random.nextInt(2000000),
        replys = Random.nextInt(5000),
        isFavorite = Random.nextBoolean(),
        quote = null,
        comments = randomCommentGenerator(0).take(Random.nextInt(20)).toList()
    )
}

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
        comments = randomCommentGenerator(0).take(5).toList()
    ),
    Feed(
        id = 1,
        user = sampleUsers[0],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        photos = listOf(R.drawable.blackpink, R.drawable.profile_3),
        hearts = 1000123,
        replys = 10,
        isFavorite = false,
        comments = randomCommentGenerator(1).take(5).toList()
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
        isFavorite = false,
        comments = randomCommentGenerator(2).take(5).toList()
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
        comments = randomCommentGenerator(3).take(5).toList()
    ),
    Feed(
        id = 4,
        user = sampleUsers[0],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        photos = listOf(R.drawable.profile_1, R.drawable.profile_3),
        hearts = 1000123,
        replys = 10,
        isFavorite = false,
        comments = randomCommentGenerator(4).take(5).toList()
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
        isFavorite = false,
        comments = randomCommentGenerator(7).take(5).toList()
    ),
    Feed(
        id = 8,
        user = sampleUsers[1],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        hearts = 1003,
        replys = 10,
        isFavorite = false,
        comments = randomCommentGenerator(8).take(5).toList()
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
        isFavorite = false,
        comments = randomCommentGenerator(9).take(5).toList()
    ),
    Feed(
        id = 6,
        user = sampleUsers[7],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        photos = listOf(R.drawable.zhonya_photo),
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        hearts = 3000,
        replys = 750,
        isFavorite = false,
        comments = randomCommentGenerator(6).take(5).toList()
    ),
    Feed(
        id = 20,
        user = sampleUsers[5],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        photos = listOf(R.drawable.sample_image_2),
        content = "But I must explain to you how all this mistaken idea of denouncing pleasure But I must explain to you how all this mistaken idea of denouncing pleasure",
        hearts = 3000,
        replys = 750,
        isFavorite = false,
        comments = randomCommentGenerator(20).take(5).toList()
    ),
    Feed(
        id = 21,
        user = sampleUsers[4],
        authorInfo = "Phnom Penh ・ 1h ・ \uD83C\uDF0E",
        photos = listOf(R.drawable.sample_image_1),
        hearts = 3000,
        replys = 750,
        isFavorite = false,
        comments = randomCommentGenerator(21).take(5).toList()
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