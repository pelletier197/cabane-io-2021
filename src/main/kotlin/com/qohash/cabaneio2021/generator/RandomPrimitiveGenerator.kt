package com.qohash.cabaneio2021.generator

import java.time.Instant
import kotlin.random.Random
import kotlin.random.nextUInt

fun <T> randomListOf(size: UInt = randomUInt(10u), creator: () -> T): List<T> {
    return (0 until size.toInt()).map { creator() }
}

fun <T> randomSetOf(size: UInt = randomUInt(10u), creator: () -> T): Set<T> {
    return randomListOf(size, creator).toSet()
}

fun randomUInt(max: UInt = UInt.MAX_VALUE): UInt {
    return Random.nextUInt(max)
}

fun randomBoolean(): Boolean {
    return Random.nextBoolean()
}

fun randomInstant(
    min: Instant = Instant.parse("2010-01-01T00:00:00.000Z"),
    max: Instant = Instant.now()
): Instant {
    return Instant.ofEpochMilli(Random.nextLong(min.toEpochMilli(), max.toEpochMilli()))
}
