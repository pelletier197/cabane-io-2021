package com.qohash.cabaneio2021.shell

import com.qohash.cabaneio2021.inserter.DataInsertionService
import com.qohash.cabaneio2021.inserter.neo4j.NEO4J
import com.qohash.cabaneio2021.inserter.postgres.POSTGRES
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import javax.validation.constraints.Max
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

@ShellComponent
class InsertCommand(
    private val insertionService: DataInsertionService
) {
    @ShellMethod(
        value = "insert the exact same random users into all target databases. The tweets, follows, likes, etc. will also be randomly created and stored.",
        key = ["insert-users"],
    )
    fun insertUsers(
        @ShellOption(value = ["--count"], defaultValue = "500") @Positive @Max(value = 100_000) count: UInt,
        @ShellOption(
            value = ["--inserters"],
            defaultValue = "$POSTGRES,$NEO4J",
        ) inserterNames: Set<@Pattern(regexp = "^($POSTGRES|$NEO4J)$") String>
    ) {
        insertionService.insertUsers(
            usersCount = count,
            inserterNames = inserterNames,
        )
    }
}