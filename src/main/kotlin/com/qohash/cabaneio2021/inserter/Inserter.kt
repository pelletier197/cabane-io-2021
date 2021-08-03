package com.qohash.cabaneio2021.inserter

import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.User

interface Inserter {
    val name: String

    fun insertUsers(users: Set<User>)

    fun insertTweets(tweets: Set<Tweet>)

    fun insertRetweets(retweets: Set<Retweet>)

    fun insertTweetLikes(insertTweetLikes: Map<User, Set<Tweet>>)

    fun insertUserFollows(usersWithFollowers: Map<User, Set<User>>)
}