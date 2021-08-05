package com.qohash.cabaneio2021.inserter.neo4j.entity

import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.util.*
import javax.persistence.Id

@Node("publications")
abstract class Neo4jPublicationEntity(
    @Id open val id: UUID,
)

@Node("retweets")
class Neo4jRetweetEntity(
    id: UUID,
    @Relationship(type = "retweet") val tweet: Neo4jTweetEntity,
) : Neo4jPublicationEntity(
    id = id,
)


@Node("tweets")
class Neo4jTweetEntity(
    id: UUID,
    val text: String,
    val sourceName: String,
    @Relationship(type = "has_tag") val hashTags: List<Neo4jHashTagEntity>,
    @Relationship(type = "has_link") val links: List<Neo4jLinkEntity>,
    @Relationship(type = "mention") val mentions: List<Neo4jUserEntity>,
) : Neo4jPublicationEntity(
    id = id,
)


@Node("hash_tags")
data class Neo4jHashTagEntity(
    @Id
    val value: String
)


@Node("links")
data class Neo4jLinkEntity(
    @Id
    val url: String
)