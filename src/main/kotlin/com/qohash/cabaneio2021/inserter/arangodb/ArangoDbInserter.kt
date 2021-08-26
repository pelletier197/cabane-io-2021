package com.qohash.cabaneio2021.inserter.arangodb

import com.arangodb.springframework.core.ArangoOperations
import com.arangodb.springframework.repository.ArangoRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.arangodb.assembler.toArango
import com.qohash.cabaneio2021.inserter.arangodb.entity.ArangoUserEntity
import com.qohash.cabaneio2021.inserter.arangodb.assembler.setUserRelations
import com.qohash.cabaneio2021.inserter.arangodb.entity.ArangoPublicationEntity
import com.qohash.cabaneio2021.inserter.arangodb.entity.ArangoRetweetEntity
import com.qohash.cabaneio2021.inserter.arangodb.entity.ArangoTweetEntity
import com.qohash.cabaneio2021.inserter.neo4j.assembler.*
import com.qohash.cabaneio2021.inserter.neo4j.entity.Neo4jBusinessEntity
import com.qohash.cabaneio2021.inserter.neo4j.entity.Neo4jIndividualEntity
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.*

const val ARANGO_DB = "arangodb"



@Component
@Qualifier(ARANGO_DB)
class ArangoDbInserter(
    private val arangoTemplate: ArangoOperations,
    private val mapper: ObjectMapper,
) : Inserter {
    override fun insert(model: TwitterModel) {
        val users = model.users.map { it.toNeo4j() }
        // Do the same as with neo4j since the ORM cannot handle cascade save
        arangoTemplate.query(
            """
           FOR user IN @@users
            INSERT user into users
        """,
            mapOf(
                "@users" to users,
                "businesses" to users.filterIsInstance<Neo4jBusinessEntity>(),
                "individuals" to users.filterIsInstance<Neo4jIndividualEntity>(),
                "tweets" to model.userTweets.toNeo4jTweets(),
                "retweets" to model.userRetweets.toNeo4jRetweets(),
                "userLikes" to model.userTweetLikes.toNeo4jTweetLikes(),
                "userFollows" to model.userFollows.toNeo4jFollows(),
            ),
            Any::class.java
        )

    }
}