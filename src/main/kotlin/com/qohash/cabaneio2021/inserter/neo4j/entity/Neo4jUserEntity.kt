package com.qohash.cabaneio2021.inserter.neo4j.entity

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import java.time.Instant
import java.util.*

@Node("User")
abstract class Neo4jUserEntity(
    @Id val id: UUID,
    val handle: String,
    val name: String,
    val joinDate: Instant,
)


@Node("Individual")
class Neo4jIndividualEntity(
    id: UUID,
    handle: String,
    name: String,
    joinDate: Instant,
    val birthDate: Instant,
    val gender: String
) : Neo4jUserEntity(
    id = id,
    handle = handle,
    name = name,
    joinDate = joinDate,
)

@Node("Business")
class Neo4jBusinessEntity(
    id: UUID,
    handle: String,
    name: String,
    joinDate: Instant,
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
)