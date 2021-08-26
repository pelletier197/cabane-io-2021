package com.qohash.cabaneio2021.inserter

import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.User

interface Inserter {
    fun insert(model: TwitterModel)
}