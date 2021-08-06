package com.qohash.cabaneio2021.inserter.neo4j.entity

import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.time.Instant
import java.util.*
import javax.persistence.Id

@Node("User")
abstract class Neo4jUserEntity(
    @Id open val id: UUID,
    open val handle: String,
    open val name: String,
    open val joinDate: Instant,
    @Relationship(type = "POSTED")
    open val publications: List<Neo4jPublicationEntity>,
    @Relationship(type = "LIKES")
    open val likedPublications: List<Neo4jTweetEntity>,
    @Relationship(type = "FOLLOWS")
    open val follows: List<Neo4jUserEntity>
)


@Node("Individual")
class Neo4jIndividualEntity(
    id: UUID,
    handle: String,
    name: String,
    joinDate: Instant,
    publications: List<Neo4jPublicationEntity>,
    likedPublications: List<Neo4jTweetEntity>,
    follows: List<Neo4jUserEntity>,
    val birthDate: Instant,
    val gender: String
) : Neo4jUserEntity(
    id = id,
    handle = handle,
    name = name,
    joinDate = joinDate,
    publications = publications,
    likedPublications = likedPublications,
    follows = follows,
)

@Node("Business")
class Neo4jBusinessEntity(
    id: UUID,
    handle: String,
    name: String,
    joinDate: Instant,
    publications: List<Neo4jPublicationEntity>,
    likedPublications: List<Neo4jTweetEntity>,
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
    likedPublications = likedPublications,
    follows = follows,
)