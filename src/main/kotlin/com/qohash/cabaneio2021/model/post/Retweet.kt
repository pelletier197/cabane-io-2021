package com.qohash.cabaneio2021.model.post

import com.qohash.cabaneio2021.model.user.User

data class Retweet(
    override val id: PublicationId,
    override val author: User,
    val tweet: Tweet,
) : Publication
