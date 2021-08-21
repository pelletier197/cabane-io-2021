package com.qohash.cabaneio2021.inserter.arangodb.assembler

import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.arangodb.entity.*
import com.qohash.cabaneio2021.model.contact.web.Link
import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.post.tags.HashTag
import com.qohash.cabaneio2021.model.user.Business
import com.qohash.cabaneio2021.model.user.Individual
import com.qohash.cabaneio2021.model.user.User
import java.util.*

fun setUserRelations(
    model: TwitterModel,
    arangoUsersById: Map<UUID, ArangoUserEntity>,
    arangoTweetsById: Map<UUID, ArangoTweetEntity>,
    arangoRetweetsById: Map<UUID, ArangoRetweetEntity>,
) {
    model.users.forEach {
        val arangoUser = arangoUsersById[it.id.value]!!
        arangoUser.publications += model.userTweets(it).map { tweet -> arangoTweetsById[tweet.id.value]!! }
        arangoUser.publications += model.userRetweets(it).map { retweet -> arangoRetweetsById[retweet.id.value]!! }
        arangoUser.follows = model.userFollows(it).map { followed -> arangoUsersById[followed.id.value]!! }
        arangoUser.likes = model.userLikes(it).map { like -> arangoTweetsById[like.id.value]!! }
    }
}

fun User.toArango(): ArangoUserEntity {
    return when (this) {
        is Individual -> toIndividualEntity()
        is Business -> toBusinessEntity()
    }
}

fun Individual.toIndividualEntity(): ArangoIndividualEntity {
    return ArangoIndividualEntity(
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

fun Business.toBusinessEntity(): ArangoBusinessEntity {
    return ArangoBusinessEntity(
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

@JvmName("userTweetsToArango")
fun Collection<Tweet>.toArango(
    arangoUsersById: Map<UUID, ArangoUserEntity>,
): List<ArangoTweetEntity> {
    return map { it.toArango(arangoUsersById) }
}

@JvmName("userRetweetsToArango")
fun Collection<Retweet>.toArango(
    arangoTweetsById: Map<UUID, ArangoTweetEntity>,
): List<ArangoRetweetEntity> {
    return map { it.toArango(arangoTweetsById) }
}


fun Tweet.toArango(
    arangoUsersById: Map<UUID, ArangoUserEntity>
): ArangoTweetEntity {
    return ArangoTweetEntity(
        id = id.value,
        text = text,
        sourceName = source.name,
        timestamp = timestamp,
        hashTags = hashtags.toArango(),
        links = links.toArango(),
        mentions = mentions.map { arangoUsersById[it.id.value]!! }
    )
}

fun Retweet.toArango(arangoTweetsById: Map<UUID, ArangoTweetEntity>): ArangoRetweetEntity {
    return ArangoRetweetEntity(
        id = id.value,
        tweet = arangoTweetsById[tweet.id.value]!!,
    )
}

@JvmName("hashtagsToArango")
fun Collection<HashTag>.toArango(): List<ArangoHashTagEntity> {
    return map { ArangoHashTagEntity(it.value) }
}

@JvmName("linksToArango")
fun Collection<Link>.toArango(): List<ArangoLinkEntity> {
    return map { ArangoLinkEntity(url = it.url.toString()) }
}