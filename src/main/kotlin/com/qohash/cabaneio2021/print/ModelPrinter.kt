package com.qohash.cabaneio2021.print

import com.qohash.cabaneio2021.inserter.TwitterModel

fun TwitterModel.printStatistics() {
    val usersCount = users.size
    val tweetsCount = allTweets.size
    val retweetsCount = allRetweets.size
    val userFollowsCount = userFollows.values.fold(0) { acc, current -> acc + current.size }
    val tweetLikeCount = userTweetLikes.values.fold(0) { acc, current -> acc + current.size }

    println("users: $usersCount")
    println("tweets: $tweetsCount")
    println("retweets: $retweetsCount")
    println("follows links: $userFollowsCount")
    println("tweet likes links: $tweetLikeCount")
    println("total: ${usersCount + tweetsCount + retweetsCount + userFollowsCount + tweetLikeCount}")
}