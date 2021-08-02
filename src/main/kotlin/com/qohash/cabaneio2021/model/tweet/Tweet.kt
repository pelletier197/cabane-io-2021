package com.qohash.cabaneio2021.model.tweet

import com.qohash.cabaneio2021.model.contact.web.Link
import com.qohash.cabaneio2021.model.tweet.tags.HashTag
import com.qohash.cabaneio2021.model.user.User
import java.util.*

data class Tweet(
    val id: TweetId,
    val text: String,
    val author: User,
    val hashtags: Set<HashTag>,
    val links: Set<Link>,
    val mentions: Set<User>,
    val source: Source,
)

data class Retweet(
    val id: TweetId,
    val original: Tweet,
)

@JvmInline
value class TweetId(
    val value: UUID
)