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

    fun userPublications(user: User) : Set<Publication> {
        return userTweets[user].orEmpty() + userRetweets[user].orEmpty()
    }
}