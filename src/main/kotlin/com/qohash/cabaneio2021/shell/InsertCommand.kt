package com.qohash.cabaneio2021.shell

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.arangodb.ARANGO_DB
import com.qohash.cabaneio2021.inserter.mongo.MONGO
import com.qohash.cabaneio2021.inserter.neo4j.NEO4J
import com.qohash.cabaneio2021.inserter.postgres.POSTGRES
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class InsertCommand(
    private val inserters: List<Inserter>
) {
    @ShellMethod(
        value = "insert data into all target databases",
        key = ["insert-data"],
    )
    fun insertData(
        @ShellOption(value = ["the number of users to insert"], defaultValue = "10000") count: UInt,
        @ShellOption(
            value = ["the target inserters"],
            defaultValue = "$POSTGRES,$MONGO,$NEO4J,$ARANGO_DB",
        ) inserterNames: Set<String>
    ) {
        println("count: $count")
        println("inserters: $inserterNames")
    }
}