package com.qohash.cabaneio2021.inserter.arangodb.entity

import com.arangodb.springframework.annotation.Document
import com.arangodb.springframework.annotation.Edge
import com.arangodb.springframework.annotation.Relations
import java.time.Instant
import java.util.*
import javax.persistence.Id

@Document("puglications")
abstract class ArangoPublicationEntity(
    @Id open val id: UUID,
)

class ArangoRetweetEntity(
    id: UUID,
    @Relations(edges = [RetweetsRelation::class]) val tweet: ArangoTweetEntity,
) : ArangoPublicationEntity(
    id = id,
)

@Edge("retweets")
class RetweetsRelation

class ArangoTweetEntity(
    id: UUID,
    val text: String,
    val sourceName: String,
    val timestamp: Instant,
    @Relations(edges = [HasTagRelation::class]) val hashTags: List<ArangoHashTagEntity>,
    @Relations(edges = [HasLinkRelation::class]) val links: List<ArangoLinkEntity>,
    @Relations(edges = [MentionsRelation::class]) val mentions: List<ArangoUserEntity>,
) : ArangoPublicationEntity(
    id = id,
)

@Edge("hasTag")
class HasTagRelation


@Edge("hasLink")
class HasLinkRelation

@Edge("mentions")
class MentionsRelation

@Document("hashtags")
data class ArangoHashTagEntity(
    @Id
    val value: String
)

@Document("links")
data class ArangoLinkEntity(
    @Id
    val url: String
)