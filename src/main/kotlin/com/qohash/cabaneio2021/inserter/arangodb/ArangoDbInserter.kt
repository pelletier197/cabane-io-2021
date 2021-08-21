package com.qohash.cabaneio2021.inserter.arangodb

import com.arangodb.springframework.repository.ArangoRepository
import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.arangodb.assembler.toArango
import com.qohash.cabaneio2021.inserter.arangodb.entity.ArangoUserEntity
import com.qohash.cabaneio2021.inserter.arangodb.assembler.setUserRelations
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.*

const val ARANGO_DB = "arangodb"

interface NativeArangoUserRepository: ArangoRepository<ArangoUserEntity, UUID>

@Component
@Qualifier(ARANGO_DB)
class ArangoDbInserter(
    private val repository: NativeArangoUserRepository
) : Inserter {
    override fun insert(model: TwitterModel) {
            // Users need to be inserted in priority to handle the case of circular references between users in the `follows` relationship
            val arangoUsers = repository.saveAll(model.users.map { it.toArango() })
            val arangoUsersById = arangoUsers.associateBy { it.id }

            val arangoTweets = model.allTweets.toArango(arangoUsersById)
            val arangoTweetsById = arangoTweets.associateBy { it.id }

            val allRetweets = model.allRetweets.toArango(arangoTweetsById)
            val postgresRetweetsById = allRetweets.associateBy { it.id }

            setUserRelations(model, arangoUsersById, arangoTweetsById, postgresRetweetsById)
            repository.saveAll(arangoUsers)
    }
}