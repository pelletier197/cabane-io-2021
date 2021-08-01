package com.qohash.cabaneio2021.generator

import com.qohash.cabaneio2021.model.user.*
import io.github.serpro69.kfaker.Faker
import java.util.*

private val faker = Faker()
private val nonAlphabeticChars = Regex("[^A-Za-z0-9 ]")


fun randomUser(): User {
    return if (randomBoolean()) randomBusiness() else randomIndividual()
}

fun randomBusiness(): Business {
    val companyName = faker.company.name()
    return Business(
        id = UserId(UUID.randomUUID()),
        name = companyName,
        handle = randomHandle(companyName),
        joinDate = randomInstant(),

    )
}

fun randomIndividual(): Individual {

}

fun randomHandle(companyName: String): Handle {
    return Handle("@${nonAlphabeticChars.replace(companyName, "")}")
}