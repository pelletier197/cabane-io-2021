package com.qohash.cabaneio2021.inserter.postgres.entity

import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "publications")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(
    name = "type",
    discriminatorType = DiscriminatorType.STRING,
)
abstract class PostgresPublicationEntity(
    @Id open val id: UUID,
)

@Entity
@Table(name = "retweets")
@DiscriminatorValue("retweet")
class PostgresRetweetEntity(
    id: UUID,
    @ManyToOne(cascade = [CascadeType.MERGE]) val tweet: PostgresTweetEntity,
) : PostgresPublicationEntity(
    id = id,
)

@Entity
@Table(name = "tweets")
@DiscriminatorValue("tweet")
class PostgresTweetEntity(
    id: UUID,
    val text: String,
    @Column(name = "source_name")
    val sourceName: String,
    val timestamp: Instant,
    @ManyToMany(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)  val hashTags: List<PostgresHashTagEntity>,
    @ManyToMany(cascade = [CascadeType.MERGE]) val links: List<PostgresLinkEntity>,
    @ManyToMany(cascade = []) val mentions: List<PostgresUserEntity>,
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