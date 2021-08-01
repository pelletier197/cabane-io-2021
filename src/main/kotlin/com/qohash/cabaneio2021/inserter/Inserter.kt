package com.qohash.cabaneio2021.inserter

import com.qohash.cabaneio2021.model.tweet.Tweet
import com.qohash.cabaneio2021.model.user.User

interface Inserter {
    val name: String

    fun insert(users: List<User>)

    fun insertTweetsPerUsers(users: Map<User, List<Tweet>>)

    fun insertTweetLikes(insertTweetLikes: Map<Tweet, List<User>>)

    fun insertUserFollows(usersWithFollowers: Map<User, List<User>>)
}