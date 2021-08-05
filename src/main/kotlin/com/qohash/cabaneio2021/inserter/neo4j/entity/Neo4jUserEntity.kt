package com.qohash.cabaneio2021.inserter.neo4j.entity

import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.time.Instant
import java.util.*
import javax.persistence.Id

@Node("user")
abstract class Neo4jUserEntity(
    @Id open val id: UUID,
    open val handle: String,
    open val name: String,
    open val joinDate: Instant,
    @Relationship(type = "posted")
    open val publications: List<Neo4jPublicationEntity>
)


@Node("individual")
class Neo4jIndividualEntity(
    id: UUID,
    handle: String,
    name: String,
    joinDate: Instant,
    publications: List<Neo4jPublicationEntity>,
    val birthDate: Instant,
    val gender: String
) : Neo4jUserEntity(
    id = id,
    handle = handle,
    name = name,
    joinDate = joinDate,
    publications = publications
)

@Node("businesses")
class Neo4jBusinessEntity(
    id: UUID,
    handle: String,
    name: String,
    joinDate: Instant,
    publications: List<Neo4jPublicationEntity>,
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
    publications = publications
)