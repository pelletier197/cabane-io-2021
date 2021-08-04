package com.qohash.cabaneio2021.inserter.neo4j

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.postgres.POSTGRES
import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.User
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

const val NEO4J = "neo4j"

@Component
@Qualifier(NEO4J)
class Neo4jInserter : Inserter {
    override fun insert(model: TwitterModel) {
        TODO("Not yet implemented")
    }
}