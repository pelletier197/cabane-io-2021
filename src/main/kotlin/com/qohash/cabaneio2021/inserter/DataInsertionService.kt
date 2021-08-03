package com.qohash.cabaneio2021.inserter

import org.hibernate.sql.Insert

class DataInsertionService(
    inserters: List<Inserter>
) {
    private val insertersByName = inserters.associateBy { it.name }

    fun insertData(
        usersCount: UInt,
        inserterNames: Set<String>,
    ) {
        val inserters = findInserters(inserterNames)

    }

    private fun findInserters(inserterNames: Set<String>): List<Inserter> {
        return inserterNames.map { insertersByName[it] ?: error("inserter with name '$it' does not exist") }
    }
}