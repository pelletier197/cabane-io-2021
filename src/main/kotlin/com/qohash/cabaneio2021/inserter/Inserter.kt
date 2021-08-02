package com.qohash.cabaneio2021.inserter

import com.qohash.cabaneio2021.model.tweet.Retweet
import com.qohash.cabaneio2021.model.tweet.Tweet
import com.qohash.cabaneio2021.model.user.User

interface Inserter {
    val name: String

    fun insertUsers(users: List<User>)

    fun insertTweets(tweets: List<Tweet>)

    fun insertRetweets(userRetweets: Map<User, List<Retweet>>)

    fun insertTweetLikes(insertTweetLikes: Map<User, List<Tweet>>)

    fun insertUserFollows(usersWithFollowers: Map<User, List<User>>)
}