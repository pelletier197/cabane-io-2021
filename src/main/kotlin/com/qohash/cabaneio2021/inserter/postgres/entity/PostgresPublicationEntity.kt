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
    @ManyToOne open val author: PostgresUserEntity,
)

@Entity
@Table(name = "retweets")
class PostgresRetweetEntity(
    id: UUID,
    author: PostgresUserEntity,
    @ManyToOne val tweet: PostgresTweetEntity,
) : PostgresPublicationEntity(
    id = id,
    author = author,
)

@Entity
@Table(name = "tweets")
class PostgresTweetEntity(
    id: UUID,
    author: PostgresUserEntity,
    val text: String,
    @Column(name = "source_name")
    val sourceName: String,
    @ManyToMany val hashTags: List<HashTagEntity>,
    @ManyToMany val links: List<LinkEntity>,
    @ManyToMany val mentions: List<PostgresUserEntity>,
) : PostgresPublicationEntity(
    id = id,
    author = author,
)

@Entity
@Table(name = "hash_tags")
data class HashTagEntity(
    @Id
    val value: String
)

@Entity
@Table(name = "links")
data class LinkEntity(
    @Id
    val url: String
)