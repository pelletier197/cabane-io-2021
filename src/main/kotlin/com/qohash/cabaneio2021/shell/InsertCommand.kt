package com.qohash.cabaneio2021.shell

import com.qohash.cabaneio2021.inserter.DataInsertionService
import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.arangodb.ARANGO_DB
import com.qohash.cabaneio2021.inserter.mongo.MONGO
import com.qohash.cabaneio2021.inserter.neo4j.NEO4J
import com.qohash.cabaneio2021.inserter.postgres.POSTGRES
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

@ShellComponent
class InsertCommand(
    private val insertionService: DataInsertionService
) {
    @ShellMethod(
        value = "insert data into all target databases",
        key = ["insert-data"],
    )
    fun insertData(
        @ShellOption(value = ["--count"], defaultValue = "10000") @Positive @Max(value = 100_000) count: UInt,
        @ShellOption(
            value = ["--inserters"],
            defaultValue = "$POSTGRES,$MONGO,$NEO4J,$ARANGO_DB",
        ) inserterNames: Set<@Pattern(regexp = "^($POSTGRES|$MONGO|$NEO4J|$ARANGO_DB)$") String>
    ) {
        insertionService.insertData(
            usersCount = count,
            inserterNames = inserterNames,
        )
    }
}