package com.qohash.cabaneio2021.inserter.postgres.assembler

import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.postgres.entity.*
import com.qohash.cabaneio2021.model.contact.web.Link
import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.post.tags.HashTag
import com.qohash.cabaneio2021.model.user.Business
import com.qohash.cabaneio2021.model.user.Individual
import com.qohash.cabaneio2021.model.user.User
import java.util.*

fun assemble(model: TwitterModel): List<PostgresUserEntity> {
    val postgresUsers = model.users.map { it.toPostgres() }
    println("mapped postgres users")
    val postgresUsersById = postgresUsers.associateBy { it.id }
    println("users by id")
    val postgresTweets = model.allTweets().toPostgres(postgresUsersById)
    println("tweets")
    val postgresTweetsById = postgresTweets.associateBy { it.id }
    println("tweet by id")
    val allRetweets = model.allRetweets().toPostgres(postgresTweetsById)
    println("retweets")
    val postgresRetweetsById = allRetweets.associateBy { it.id }
    println("retweets by id")

    setUserRelations(model, postgresUsersById, postgresTweetsById, postgresRetweetsById)
    println("set relations")

    return postgresUsers
}

fun setUserRelations(
    model: TwitterModel,
    postgresUsersById: Map<UUID, PostgresUserEntity>,
    postgresTweetsById: Map<UUID, PostgresTweetEntity>,
    postgresRetweetsById: Map<UUID, PostgresRetweetEntity>,
) {
    model.users.forEach {
        val postgresUser = postgresUsersById[it.id.value]!!
        postgresUser.publications += model.userTweets(it).map { tweet -> postgresTweetsById[tweet.id.value]!! }
        postgresUser.publications += model.userRetweets(it).map { retweet -> postgresRetweetsById[retweet.id.value]!! }
        postgresUser.follows = model.userFollows(it).map { followed -> postgresUsersById[followed.id.value]!! }
        postgresUser.likes = model.userLikes(it).map { like -> postgresTweetsById[like.id.value]!! }
    }
}

fun User.toPostgres(): PostgresUserEntity {
    return when (this) {
        is Individual -> toIndividualEntity()
        is Business -> toBusinessEntity()
    }
}

fun Individual.toIndividualEntity(): PostgresIndividualEntity {
    return PostgresIndividualEntity(
        id = id.value,
        handle = handle.value,
        name = name,
        joinDate = joinDate,
        birthDate = birthDate,
        gender = gender,
        publications = emptyList(),
        follows = emptyList(),
        likes = emptyList(),
    )
}

fun Business.toBusinessEntity(): PostgresBusinessEntity {
    return PostgresBusinessEntity(
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
        publications = emptyList(),
        follows = emptyList(),
        likes = emptyList(),
    )
}

@JvmName("userTweetsToPostgres")
fun Collection<Tweet>.toPostgres(
    postgresUsersById: Map<UUID, PostgresUserEntity>,
): List<PostgresTweetEntity> {
    return map { it.toPostgres(postgresUsersById) }
}

@JvmName("userRetweetsToPostgres")
fun Collection<Retweet>.toPostgres(
    postgresTweetsById: Map<UUID, PostgresTweetEntity>,
): List<PostgresRetweetEntity> {
    return map { it.toPostgres(postgresTweetsById) }
}


fun Tweet.toPostgres(
    postgresUsersById: Map<UUID, PostgresUserEntity>
): PostgresTweetEntity {
    return PostgresTweetEntity(
        id = id.value,
        text = text,
        sourceName = source.name,
        hashTags = hashtags.toPostgres(),
        links = links.toPostgres(),
        mentions = mentions.map { postgresUsersById[it.id.value]!! }
    )
}

fun Retweet.toPostgres(postgresTweetsById: Map<UUID, PostgresTweetEntity>): PostgresRetweetEntity {
    return PostgresRetweetEntity(
        id = id.value,
        tweet = postgresTweetsById[tweet.id.value]!!,
    )
}

@JvmName("hashtagsToPostgres")
fun Collection<HashTag>.toPostgres(): List<PostgresHashTagEntity> {
    return map { PostgresHashTagEntity(it.value) }
}

@JvmName("linksToPostgres")
fun Collection<Link>.toPostgres(): List<PostgresLinkEntity> {
    return map { PostgresLinkEntity(url = it.url.toString()) }
}