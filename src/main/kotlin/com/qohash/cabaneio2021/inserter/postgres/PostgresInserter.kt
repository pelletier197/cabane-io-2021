package com.qohash.cabaneio2021.inserter.postgres

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.postgres.assembler.assemble
import com.qohash.cabaneio2021.inserter.postgres.entity.PostgresUserEntity
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Lazy
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.util.*

const val POSTGRES = "postgres"

interface PostgresUserRepository : CrudRepository<PostgresUserEntity, UUID>

@Component
@Qualifier(POSTGRES)
@Lazy
class PostgresInserter(
    private val repository: PostgresUserRepository,
) : Inserter {
    override fun insert(model: TwitterModel) {
        val postgresModel = assemble(model)
        repository.saveAll(postgresModel)
    }
}