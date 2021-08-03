package com.qohash.cabaneio2021.inserter.neo4j

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.User

const val NEO4J = "neo4j"

class Neo4jInserter : Inserter {
    override val name: String = NEO4J

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