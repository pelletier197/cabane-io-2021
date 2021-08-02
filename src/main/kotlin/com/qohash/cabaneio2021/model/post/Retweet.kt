package com.qohash.cabaneio2021.model.post

import com.qohash.cabaneio2021.model.user.User

data class Retweet(
    override val id: PostId,
    override val author: User,
    val tweet: Tweet,
) : Post
