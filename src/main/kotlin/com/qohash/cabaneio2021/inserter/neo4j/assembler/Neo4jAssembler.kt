package com.qohash.cabaneio2021.inserter.neo4j.assembler

import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.neo4j.entity.*
import com.qohash.cabaneio2021.model.contact.web.Link
import com.qohash.cabaneio2021.model.post.Publication
import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.post.tags.HashTag
import com.qohash.cabaneio2021.model.user.Business
import com.qohash.cabaneio2021.model.user.Individual
import com.qohash.cabaneio2021.model.user.User

fun Collection<User>.toNeo4jUsers(model: TwitterModel): List<Neo4jUserEntity> {
    return orEmpty().map { it.toNeo4jUser(model) }
}

fun User.toNeo4jUser(model: TwitterModel): Neo4jUserEntity {
    return when (this) {
        is Individual -> toIndividualEntity(model)
        is Business -> toBusinessEntity(model)
    }
}

fun Individual.toIndividualEntity(model: TwitterModel): Neo4jIndividualEntity {
    return Neo4jIndividualEntity(
        id = id.value,
        handle = handle.value,
        name = name,
        joinDate = joinDate,
        birthDate = birthDate,
        gender = gender,
        publications = model.userPublications(this).toNeo4jPublications(),
        likedPublications = model.userLikes(this).map { it.toTweetEntity() },
        follows = model.userFollows(this).toNeo4jUsers(model)
    )
}

fun Business.toBusinessEntity(model: TwitterModel): Neo4jBusinessEntity {
    return Neo4jBusinessEntity(
        id = id.value,
        handle = handle.value,
        name = name,
        joinDate = joinDate,
        numberOfEmployees = numberOfEmployees.toLong(),
        phone = contactInformation.phone?.value,
        website = contactInformation.webSite?.link.toString(),
        websiteName = contactInformation.webSite?.name,
        locationAddressLine = contactInformation.location?.addressLine,
        locationCity = contactInformation.location?.city,
        locationCountry = contactInformation.location?.country,
        verified = verified,
        publications = model.userPublications(this).toNeo4jPublications(),
        likedPublications = model.userLikes(this).map { it.toTweetEntity() },
        follows = model.userFollows(this).toNeo4jUsers(model)
    )
}

fun Collection<Publication>.toNeo4jPublications(): List<Neo4jPublicationEntity> {
    return orEmpty().map { it.toNeo4jPublication() }
}

fun Publication.toNeo4jPublication(): Neo4jPublicationEntity {
    return when (this) {
        is Tweet -> toTweetEntity()
        is Retweet -> toRetweetEntity()
    }
}

fun Tweet.toTweetEntity(): Neo4jTweetEntity {
    return Neo4jTweetEntity(
        id = this.id.value,
        text = this.text,
        sourceName = this.source.name,
        hashTags = this.hashtags.map { it.toHashTagEntity() },
        links = this.links.map { it.toLinkEntity() },
        mentions = emptyList()
    )
}

fun Retweet.toRetweetEntity(): Neo4jRetweetEntity {
    return Neo4jRetweetEntity(
        id = this.id.value,
        tweet = this.tweet.toTweetEntity()
    )
}


fun HashTag.toHashTagEntity(): Neo4jHashTagEntity {
    return Neo4jHashTagEntity(
        value = this.value
    )
}

fun Link.toLinkEntity(): Neo4jLinkEntity {
    return Neo4jLinkEntity(
        url = this.url.toString()
    )
}

