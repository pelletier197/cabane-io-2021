package com.qohash.cabaneio2021.generator

import com.qohash.cabaneio2021.model.contact.ContactInformation
import com.qohash.cabaneio2021.model.contact.Location
import com.qohash.cabaneio2021.model.contact.PhoneNumber
import com.qohash.cabaneio2021.model.contact.web.Link
import com.qohash.cabaneio2021.model.contact.web.WebSite
import com.qohash.cabaneio2021.model.tweet.Source
import com.qohash.cabaneio2021.model.tweet.Tweet
import com.qohash.cabaneio2021.model.tweet.TweetId
import com.qohash.cabaneio2021.model.tweet.tags.HashTag
import com.qohash.cabaneio2021.model.user.*
import io.github.serpro69.kfaker.Faker
import java.net.URL
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.random.Random

private val faker = Faker()
private val nonAlphabeticChars = Regex("[^A-Za-z0-9 ]")
private val allHashTags = randomSetOf(size = 1000u) { faker.quote.famousLastWords() }
private val allLinks = randomSetOf { URL("https://${faker.internet.domain()}") }

fun randomUser(): User {
    return if (randomBoolean()) randomBusiness() else randomIndividual()
}

fun randomBusiness(): Business {
    val companyName = faker.company.name()
    return Business(
        id = UserId(UUID.randomUUID()),
        name = companyName,
        handle = randomHandle(companyName = companyName),
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
    val firstName = faker.name.firstName()
    val lastName = faker.name.lastName()
    val joinDate = randomInstant()
    return Individual(
        id = UserId(UUID.randomUUID()),
        handle = randomHandle(firstName = firstName, lastName = lastName),
        name = "$firstName $lastName",
        joinDate = joinDate,
        birthDate = randomInstant(min = joinDate.minus(6570, ChronoUnit.DAYS), max = joinDate),
        gender = faker.gender.types()
    )
}

fun randomTweet(users: List<User>): Tweet {
    return Tweet(
        id = TweetId(UUID.randomUUID()),
        text = faker.movie.quote(),
        author = users.random(),
        hashtags = randomSetOf(size = randomUInt(max = 5u)) { randomHashTag() },
        links = randomSetOf(size = randomUInt(max = 3u)) { randomLink() },
        mentions = randomListOf(size = randomUInt(4u)) { users.random() }.distinct().toSet(),
        source = Source(
            name = faker.device.modelName()
        ),
    )
}

fun randomHandle(companyName: String): Handle {
    return Handle("@${nonAlphabeticChars.replace(companyName, "")}")
}

fun randomHandle(firstName: String, lastName: String): Handle {
    val useFirstName = randomBoolean()
    val useLastName = randomBoolean()
    val value = when {
        useFirstName && useLastName -> "@${firstName}${lastName}"
        useFirstName -> "@$firstName"
        else -> "@$lastName"
    }

    return Handle(value)
}

fun randomHashTag(): HashTag {
    return HashTag(allHashTags.random())
}

fun randomLink(): Link {
    return Link(allLinks.random())
}

fun main() {
    println(randomBusiness())
    println(randomIndividual())
    println(allHashTags)
}