package com.qohash.cabaneio2021.generator

import com.qohash.cabaneio2021.model.contact.ContactInformation
import com.qohash.cabaneio2021.model.contact.Location
import com.qohash.cabaneio2021.model.contact.PhoneNumber
import com.qohash.cabaneio2021.model.contact.web.Link
import com.qohash.cabaneio2021.model.contact.web.WebSite
import com.qohash.cabaneio2021.model.user.*
import io.github.serpro69.kfaker.Faker
import java.net.URL
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
        numberOfEmployees = randomUInt(max = 100_000u),
        contactInformation = ContactInformation(
            phone = PhoneNumber(faker.phoneNumber.phoneNumber()),
            webSite = faker.internet.domain().let {
                WebSite(
                    name = it,
                    link = Link(
                        url = URL("https://$it}")
                    )
                )
            },
            location = Location(
                addressLine = faker.address.streetAddress(),
                city = faker.address.city(),
                country = faker.address.country(),
            )
        ),
        verified = randomBoolean(),
    )
}

fun randomIndividual(): Individual {
    TODO()
}

fun randomHandle(companyName: String): Handle {
    return Handle("@${nonAlphabeticChars.replace(companyName, "")}")
}

fun main() {
    println(randomBusiness())
}