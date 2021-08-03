package com.qohash.cabaneio2021.inserter

import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.User

interface TwitterModel {
    val users: Set<User>
    val tweets: Set<Tweet>
    val retweets: Set<Retweet>
    val userTweetLikes: Map<User, Set<Tweet>>
    val userFollows: Map<User, Set<User>>
}