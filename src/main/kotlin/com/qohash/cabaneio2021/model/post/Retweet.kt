package com.qohash.cabaneio2021.model.post

import com.qohash.cabaneio2021.model.user.User

data class Retweet(
    override val id: PublicationId,
    val tweet: Tweet,
) : Publication
