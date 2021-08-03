package com.qohash.cabaneio2021.inserter.postgres

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.User

const val POSTGRES = "postgres"

class PostgresInserter : Inserter {
    override val name: String = POSTGRES

    override fun insertUsers(users: Set<User>) {
        TODO("Not yet implemented")
    }

    override fun insertTweets(tweets: Set<Tweet>) {
        TODO("Not yet implemented")
    }

    override fun insertRetweets(retweets: Set<Retweet>) {
        TODO("Not yet implemented")
    }

    override fun insertTweetLikes(insertTweetLikes: Map<User, Set<Tweet>>) {
        TODO("Not yet implemented")
    }

    override fun insertUserFollows(usersWithFollowers: Map<User, Set<User>>) {
        TODO("Not yet implemented")
    }
}