package com.qohash.cabaneio2021.inserter.neo4j.entity

import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.util.*
import javax.persistence.Id

@Node("Publication")
abstract class Neo4jPublicationEntity(
    @Id open val id: UUID,
)

@Node("Retweet")
class Neo4jRetweetEntity(
    id: UUID,
    @Relationship(type = "RETWEETS") val tweet: Neo4jTweetEntity,
) : Neo4jPublicationEntity(
    id = id,
)


@Node("Tweet")
class Neo4jTweetEntity(
    id: UUID,
    val text: String,
    val sourceName: String,
    @Relationship(type = "HAS_TAG") val hashTags: List<Neo4jHashTagEntity>,
    @Relationship(type = "HAS_LINK") val links: List<Neo4jLinkEntity>,
    @Relationship(type = "MENTIONS") val mentions: List<Neo4jUserEntity>,
) : Neo4jPublicationEntity(
    id = id,
)


@Node("HashTag")
data class Neo4jHashTagEntity(
    @Id
    val value: String
)


@Node("Link")
data class Neo4jLinkEntity(
    @Id
    val url: String
)