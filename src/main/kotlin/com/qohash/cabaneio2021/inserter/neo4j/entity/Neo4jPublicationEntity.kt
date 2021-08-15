package com.qohash.cabaneio2021.inserter.neo4j.entity

import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.time.Instant
import java.util.*

@Node("Publication")
abstract class Neo4jPublicationEntity(
    @Id open val id: UUID,
)

@Node("Retweet")
class Neo4jRetweetEntity(
    id: UUID,
) : Neo4jPublicationEntity(
    id = id,
)


@Node("Tweet")
class Neo4jTweetEntity (
    id: UUID,
    val text: String,
    val sourceName: String,
    val timestamp: Instant,
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

@Node("Source")
data class Neo4jSourceEntity(
    @Id val name: String
)