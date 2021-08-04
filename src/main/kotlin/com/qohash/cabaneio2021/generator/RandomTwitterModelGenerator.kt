package com.qohash.cabaneio2021.generator

import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.User

private data class LazyLoadedTwitterModel(
    val usersCount: UInt,
) : TwitterModel {
    private val tweetCount = usersCount * 10u
    private val retweetCount = tweetCount * 10u
    private val maxLikesPerUser = 100u
    private val maxFollowPerUser = 100u

    // Lists are used because random() is much faster on `List` than on `Set`
    private val usersList by lazy { randomListOf(size = usersCount) { randomUser() } }
    private val tweetList by lazy { randomListOf(size = tweetCount) { randomTweet(usersList) } }

    override val users: Set<User> by lazy { usersList.toSet() }
    override val tweets: Set<Tweet> by lazy { tweetList.toSet() }
    override val retweets: Set<Retweet> by lazy {
        randomSetOf(size = retweetCount) {
            randomRetweet(
                usersList,
                tweetList
            )
        }
    }
    override val userTweetLikes: Map<User, Set<Tweet>> by lazy {
        usersList.associateWith {
            (0u..randomUInt(max = maxLikesPerUser)).mapTo(HashSet()) { tweets.random() }
        }
    }
    override val userFollows: Map<User, Set<User>> by lazy {
        usersList.associateWith {
            (0u..randomUInt(maxFollowPerUser)).mapTo(HashSet()) { usersList.random() }
        }
    }
}

fun randomTwitterModel(usersCount: UInt): TwitterModel {
    return LazyLoadedTwitterModel(
        usersCount = usersCount,
    )
}

