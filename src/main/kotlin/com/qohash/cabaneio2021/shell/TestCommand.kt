package com.qohash.cabaneio2021.shell

import com.qohash.cabaneio2021.inserter.DataInsertionService
import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.arangodb.ARANGO_DB
import com.qohash.cabaneio2021.inserter.mongo.MONGO
import com.qohash.cabaneio2021.inserter.neo4j.NEO4J
import com.qohash.cabaneio2021.inserter.postgres.POSTGRES
import com.qohash.cabaneio2021.inserter.postgres.entity.PostgresTweetEntity
import org.springframework.beans.factory.InitializingBean
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive


interface PostgresTweetEntityRepository : CrudRepository<PostgresTweetEntity, UUID>

@ShellComponent
class TestCommand(
    private val repository: PostgresTweetEntityRepository
) {
    @ShellMethod(
        value = "Test anything you'd like here",
        key = ["test"],
    )
    @Transactional
    fun test() {
        val t = repository.findByIdOrNull(UUID.fromString("b9fa3e48-8bf7-425b-8352-979235f841b2"))!!
        println(t.id)
        println(t.links)
        println(t.hashTags)
    }
}