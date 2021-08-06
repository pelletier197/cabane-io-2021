package com.qohash.cabaneio2021.print

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import java.time.Duration
import java.time.Instant

fun Inserter.insertWithPrintedStatistics(model: TwitterModel) {
    val start = Instant.now()
    insert(model)
    println("insertion with ${this::class.simpleName} took ${Duration.between(start, Instant.now()).toMillis()}ms")
}