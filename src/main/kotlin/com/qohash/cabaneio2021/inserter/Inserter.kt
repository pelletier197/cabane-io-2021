package com.qohash.cabaneio2021.inserter

import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.User

interface Inserter {
    val name: String

    fun insertUsers(users: List<User>)

    fun insertTweets(tweets: List<Tweet>)

    fun insertRetweet(retweet: Retweet)

    fun insertTweetLikes(insertTweetLikes: Map<User, List<Tweet>>)

    fun insertUserFollows(usersWithFollowers: Map<User, List<User>>)
}