package com.qohash.cabaneio2021.inserter.postgres

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.postgres.assembler.toPostgres
import com.qohash.cabaneio2021.inserter.postgres.entity.PostgresUserEntity
import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.User
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Lazy
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.util.*
import java.util.UUID.randomUUID

const val POSTGRES = "postgres"

interface PostgresUserRepository : CrudRepository<PostgresUserEntity, UUID>

@Component
@Qualifier(POSTGRES)
@Lazy
class PostgresInserter(
    private val repository: PostgresUserRepository,
) : Inserter {
    override fun insert(model: TwitterModel) {
        repository.save(model.users.first().toPostgres())
        println(repository.findAll())
    }
}