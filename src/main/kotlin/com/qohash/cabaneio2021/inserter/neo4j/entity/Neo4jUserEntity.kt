package com.qohash.cabaneio2021.inserter.neo4j.entity

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.time.Instant
import java.util.*

@Node("User")
abstract class Neo4jUserEntity(
    @Id open val id: UUID,
    open val handle: String,
    open val name: String,
    open val joinDate: Instant,
    @Relationship(type = "POSTED")
    open var publications: List<Neo4jPublicationEntity>,
    @Relationship(type = "LIKES")
    open var likes: List<Neo4jTweetEntity>,
    @Relationship(type = "FOLLOWS")
    open var follows: List<Neo4jUserEntity>
)


@Node("Individual")
class Neo4jIndividualEntity(
    id: UUID,
    handle: String,
    name: String,
    joinDate: Instant,
    publications: List<Neo4jPublicationEntity>,
    likes: List<Neo4jTweetEntity>,
    follows: List<Neo4jUserEntity>,
    val birthDate: Instant,
    val gender: String
) : Neo4jUserEntity(
    id = id,
    handle = handle,
    name = name,
    joinDate = joinDate,
    publications = publications,
    likes = likes,
    follows = follows,
)

@Node("Business")
class Neo4jBusinessEntity(
    id: UUID,
    handle: String,
    name: String,
    joinDate: Instant,
    publications: List<Neo4jPublicationEntity>,
    likes: List<Neo4jTweetEntity>,
    follows: List<Neo4jUserEntity>,
    val numberOfEmployees: Long,
    val phone: String?,
    val website: String?,
    val websiteName: String?,
    val locationAddressLine: String?,
    val locationCity: String?,
    val locationCountry: String?,
    val verified: Boolean,
) : Neo4jUserEntity(
    id = id,
    handle = handle,
    name = name,
    joinDate = joinDate,
    publications = publications,
    likes = likes,
    follows = follows,
)