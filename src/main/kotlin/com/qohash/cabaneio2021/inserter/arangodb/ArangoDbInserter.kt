package com.qohash.cabaneio2021.inserter.arangodb

import com.arangodb.springframework.repository.ArangoRepository
import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.arangodb.assembler.toArango
import com.qohash.cabaneio2021.inserter.arangodb.entity.ArangoUserEntity
import com.qohash.cabaneio2021.inserter.arangodb.assembler.setUserRelations
import com.qohash.cabaneio2021.inserter.arangodb.entity.ArangoPublicationEntity
import com.qohash.cabaneio2021.inserter.arangodb.entity.ArangoRetweetEntity
import com.qohash.cabaneio2021.inserter.arangodb.entity.ArangoTweetEntity
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.*

const val ARANGO_DB = "arangodb"

interface NativeArangoUserRepository : ArangoRepository<ArangoUserEntity, UUID>
interface NativeArangoPublicationRepository : ArangoRepository<ArangoPublicationEntity, UUID>

@Component
@Qualifier(ARANGO_DB)
class ArangoDbInserter(
    private val userRepository: NativeArangoUserRepository,
    private val publicationRepository: NativeArangoPublicationRepository
) : Inserter {
    override fun insert(model: TwitterModel) {
        // Users need to be inserted in priority to handle the case of circular references between users in the `follows` relationship
        val arangoUsers = userRepository.saveAll(model.users.map { it.toArango() })
        val arangoUsersById = arangoUsers.associateBy { it.id }

        val arangoTweets = model.allTweets.toArango(arangoUsersById)
        val arangoTweetsById = arangoTweets.associateBy { it.id }

        val allRetweets = model.allRetweets.toArango(arangoTweetsById)
        val postgresRetweetsById = allRetweets.associateBy { it.id }

        setUserRelations(model, arangoUsersById, arangoTweetsById, postgresRetweetsById)
        // Have to create tweets first, then retweets, then save user relations
        publicationRepository.saveAll(arangoUsers.flatMap { it.publications }.filterIsInstance<ArangoTweetEntity>())
        publicationRepository.saveAll(arangoUsers.flatMap { it.publications }.filterIsInstance<ArangoRetweetEntity>())
        userRepository.saveAll(arangoUsers)
    }
}