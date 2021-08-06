package com.qohash.cabaneio2021.inserter

import com.qohash.cabaneio2021.model.post.Publication
import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.User

interface TwitterModel {
    val users: Set<User>
    val userTweets: Map<User, Set<Tweet>>
    val userRetweets: Map<User, Set<Retweet>>
    val userTweetLikes: Map<User, Set<Tweet>>
    val userFollows: Map<User, Set<User>>

    fun allTweets() : List<Tweet> {
        return userTweets.flatMap { it.value }
    }

    fun allRetweets() : List<Retweet> {
        return userRetweets.flatMap { it.value }
    }

    fun userPublications(user: User): Set<Publication> {
        return userTweets(user) + userRetweets(user)
    }

    fun userTweets(user: User): Set<Tweet> {
        return userTweets[user].orEmpty()
    }

    fun userRetweets(user: User): Set<Retweet> {
        return userRetweets[user].orEmpty()
    }

    fun userLikes(user: User): Set<Tweet> {
        return userTweetLikes[user].orEmpty()
    }

    fun userFollows(user: User): Set<User> {
        return userFollows[user].orEmpty()
    }
}