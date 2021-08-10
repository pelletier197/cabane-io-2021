package com.qohash.cabaneio2021.inserter.neo4j.entity

import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.util.*

@Node("Publication")
abstract class Neo4jPublicationEntity(
    @Id open val id: UUID,
)

@Node("Retweet")
class Neo4jRetweetEntity @PersistenceConstructor constructor(
    id: UUID,
    @Relationship(type = "RETWEETS") val tweet: Neo4jTweetEntity,
) : Neo4jPublicationEntity(
    id = id,
)


@Node("Tweet")
class Neo4jTweetEntity @PersistenceConstructor constructor(
    id: UUID,
    val text: String,
    val sourceName: String,
    @Relationship(type = "HAS_TAG") var hashTags: List<Neo4jHashTagEntity>,
    @Relationship(type = "HAS_LINK") var links: List<Neo4jLinkEntity>,
    @Relationship(type = "MENTIONS") var mentions: List<Neo4jUserEntity>,
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