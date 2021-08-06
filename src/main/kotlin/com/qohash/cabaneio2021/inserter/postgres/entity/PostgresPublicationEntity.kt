package com.qohash.cabaneio2021.inserter.postgres.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "publications")
@DiscriminatorColumn(
    name = "type",
    discriminatorType = DiscriminatorType.STRING,
)
abstract class PostgresPublicationEntity(
    @Id open val id: UUID,
)

@Entity
@Table(name = "retweets")
class PostgresRetweetEntity(
    id: UUID,
    @ManyToOne val tweet: PostgresTweetEntity,
) : PostgresPublicationEntity(
    id = id,
)

@Entity
@Table(name = "tweets")
class PostgresTweetEntity(
    id: UUID,
    val text: String,
    @Column(name = "source_name")
    val sourceName: String,
    @ManyToMany val hashTags: List<PostgresHashTagEntity>,
    @ManyToMany val links: List<PostgresLinkEntity>,
    @ManyToMany val mentions: List<PostgresUserEntity>,
) : PostgresPublicationEntity(
    id = id,
)

@Entity
@Table(name = "hash_tags")
data class PostgresHashTagEntity(
    @Id
    val value: String
)

@Entity
@Table(name = "links")
data class PostgresLinkEntity(
    @Id
    val url: String
)