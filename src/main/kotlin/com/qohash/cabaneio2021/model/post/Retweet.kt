package com.qohash.cabaneio2021.model.post

data class Retweet(
    override val id: PublicationId,
    val tweet: Tweet,
) : Publication
