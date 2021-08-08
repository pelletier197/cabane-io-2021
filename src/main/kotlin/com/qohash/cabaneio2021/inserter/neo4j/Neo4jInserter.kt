package com.qohash.cabaneio2021.inserter.neo4j

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.neo4j.entity.Neo4jUserEntity
import com.qohash.cabaneio2021.inserter.neo4j.assembler.assemble
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
        val neo4jModel = assemble(model)
        repository.saveAll(neo4jModel)
    }
}