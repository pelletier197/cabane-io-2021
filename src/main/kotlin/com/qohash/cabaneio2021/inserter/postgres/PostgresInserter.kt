package com.qohash.cabaneio2021.inserter.postgres

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.postgres.assembler.setUserRelations
import com.qohash.cabaneio2021.inserter.postgres.assembler.toPostgres
import com.qohash.cabaneio2021.inserter.postgres.entity.PostgresTweetEntity
import com.qohash.cabaneio2021.inserter.postgres.entity.PostgresUserEntity
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.util.*

const val POSTGRES = "postgres"

interface PostgresUserRepository : CrudRepository<PostgresUserEntity, UUID>

@Component
@Qualifier(POSTGRES)
class PostgresInserter(
    private val repository: PostgresUserRepository,
) : Inserter {
    override fun insert(model: TwitterModel) {
        // Users need to be inserted in priority to handle the case of circular references between users in the `follows` relationship
        // Hibernate don't understand the
        val postgresUsers = repository.saveAll(model.users.map { it.toPostgres() })
        val postgresUsersById = postgresUsers.associateBy { it.id }

        val postgresTweets = model.allTweets.toPostgres(postgresUsersById)
        val postgresTweetsById = postgresTweets.associateBy { it.id }

        val allRetweets = model.allRetweets.toPostgres(postgresTweetsById)
        val postgresRetweetsById = allRetweets.associateBy { it.id }

        setUserRelations(model, postgresUsersById, postgresTweetsById, postgresRetweetsById)
        repository.saveAll(postgresUsers)
    }
}

