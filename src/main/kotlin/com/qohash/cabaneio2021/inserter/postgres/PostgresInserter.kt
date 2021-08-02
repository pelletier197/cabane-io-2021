package com.qohash.cabaneio2021.inserter.postgres

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.User

const val POSTGRES = "postgres"

class PostgresInserter : Inserter {
    override val name: String = POSTGRES

    override fun insertUsers(users: List<User>) {
        TODO("Not yet implemented")
    }

    override fun insertTweets(tweets: List<Tweet>) {
        TODO("Not yet implemented")
    }

    override fun insertRetweet(retweet: Retweet) {
        TODO("Not yet implemented")
    }

    override fun insertTweetLikes(insertTweetLikes: Map<User, List<Tweet>>) {
        TODO("Not yet implemented")
    }

    override fun insertUserFollows(usersWithFollowers: Map<User, List<User>>) {
        TODO("Not yet implemented")
    }
}