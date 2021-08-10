package com.qohash.cabaneio2021.inserter.neo4j.assembler

import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.neo4j.RetweetImport
import com.qohash.cabaneio2021.inserter.neo4j.TweetImport
import com.qohash.cabaneio2021.inserter.neo4j.UserFollows
import com.qohash.cabaneio2021.inserter.neo4j.UserLike
import com.qohash.cabaneio2021.inserter.neo4j.entity.*
import com.qohash.cabaneio2021.model.contact.web.Link
import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Source
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.post.tags.HashTag
import com.qohash.cabaneio2021.model.user.Business
import com.qohash.cabaneio2021.model.user.Individual
import com.qohash.cabaneio2021.model.user.User
import com.qohash.cabaneio2021.model.user.UserId
import java.util.*
import kotlin.collections.HashSet

fun User.toNeo4j(): Neo4jUserEntity {
    return when (this) {
        is Individual -> toIndividualEntity()
        is Business -> toBusinessEntity()
    }
}

fun Individual.toIndividualEntity(): Neo4jIndividualEntity {
    return Neo4jIndividualEntity(
        id = id.value,
        handle = handle.value,
        name = name,
        joinDate = joinDate,
        birthDate = birthDate,
        gender = gender,
    )
}

fun Business.toBusinessEntity(): Neo4jBusinessEntity {
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
    )
}

fun Map<User, Set<Tweet>>.toNeo4jTweets(): List<TweetImport> {
    return flatMap { (user, tweets) ->
        tweets.map { tweet ->
            TweetImport(
                tweet = tweet.toNeo4j(),
                authorId = user.id.value,
                hashTags = tweet.hashtags.toNeo4j(),
                source = tweet.source.toNeo4j(),
                links = tweet.links.toNeo4j(),
                mentionUserIds = tweet.mentions.map { it.id.value }
            )
        }
    }
}

fun Map<User, Set<Retweet>>.toNeo4jRetweets() : List<RetweetImport> {
    @Suppress("CANDIDATE_CHOSEN_USING_OVERLOAD_RESOLUTION_BY_LAMBDA_ANNOTATION")
    return flatMap { (user, retweets) ->
        return retweets.map { retweet ->
            RetweetImport(
                retweet =  retweet.toNeo4j(),
                authorId = user.id.value,
                tweetId = retweet.tweet.id.value,
            )
        }
    }
}

fun Map<User, Set<Tweet>>.toNeo4jTweetLikes(): List<UserLike> {
    return flatMap { (user, tweets) ->
        tweets.map { tweet ->
            UserLike(
                userId = user.id.value,
                tweetId = tweet.id.value,
            )
        }
    }
}

fun Map<User, Set<User>>.toNeo4jFollows(): List<UserFollows> {
    return flatMap { (user, allFollowed) ->
        allFollowed.map { followed ->
            UserFollows(
                followedId = user.id.value,
                followerId = followed.id.value,
            )
        }
    }
}

fun Tweet.toNeo4j(): Neo4jTweetEntity {
    return Neo4jTweetEntity(
        id = id.value,
        text = text,
        sourceName = source.name,
    )
}

fun Retweet.toNeo4j(): Neo4jRetweetEntity {
    return Neo4jRetweetEntity(
        id = id.value,
    )
}

@JvmName("hashtagsToNeo4j")
fun Collection<HashTag>.toNeo4j(): List<Neo4jHashTagEntity> {
    return map { Neo4jHashTagEntity(it.value) }
}

@JvmName("linksToNeo4j")
fun Collection<Link>.toNeo4j(): List<Neo4jLinkEntity> {
    return map { Neo4jLinkEntity(url = it.url.toString()) }
}

fun Source.toNeo4j(): Neo4jSourceEntity {
    return Neo4jSourceEntity(name)
}