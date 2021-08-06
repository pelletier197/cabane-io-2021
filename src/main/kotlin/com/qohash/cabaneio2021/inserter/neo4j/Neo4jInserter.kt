package com.qohash.cabaneio2021.inserter.neo4j

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.neo4j.assembler.toNeo4j
import com.qohash.cabaneio2021.inserter.neo4j.entity.Neo4jUserEntity
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Component
import java.util.*

const val NEO4J = "neo4j"

interface Neo4jUserRepository : Neo4jRepository<Neo4jUserEntity, UUID>

@Component
@Qualifier(NEO4J)
class Neo4jInserter(
    private val repository: Neo4jUserRepository,
) : Inserter {
    override fun insert(model: TwitterModel) {
        model.users.forEach {
            repository.save(it.toNeo4j(model))
        }
        println(repository.findAll())
    }
}