package com.qohash.cabaneio2021.inserter.neo4j

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.neo4j.entity.Neo4jUserEntity
import com.qohash.cabaneio2021.inserter.neo4j.assembler.toNeo4j
import com.qohash.cabaneio2021.inserter.neo4j.entity.Neo4jPublicationEntity
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Component
import java.util.*

const val NEO4J = "neo4j"

interface Neo4jUserRepository : Neo4jRepository<Neo4jUserEntity, UUID>
interface Neo4jPublicationRepository: Neo4jRepository<Neo4jPublicationEntity, UUID>

@Component
@Qualifier(NEO4J)
class Neo4jInserter(
    private val usersRepository: Neo4jUserRepository,
    private val publicationRepository: Neo4jPublicationRepository
) : Inserter {
    override fun insert(model: TwitterModel) {
        val neo4jUsers = model.users.map { it.toNeo4j() }
        val neo4jUsersById = neo4jUsers.associateBy { it.id }
        usersRepository.saveAll(neo4jUsers)

        val neo4jTweets = model.allTweets.toNeo4j(neo4jUsersById)
        val neo4jTweetsById = neo4jTweets.associateBy { it.id }
        publicationRepository.saveAll(neo4jTweets)

        val allRetweets = model.allRetweets.toNeo4j(neo4jTweetsById)
        val neo4jRetweetsById = allRetweets.associateBy { it.id }
        publicationRepository.saveAll(allRetweets)
    }
}