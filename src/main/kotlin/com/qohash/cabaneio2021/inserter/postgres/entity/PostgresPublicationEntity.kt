package com.qohash.cabaneio2021.inserter.postgres.entity

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
    @ManyToMany(cascade = [CascadeType.MERGE]) val hashTags: List<PostgresHashTagEntity>,
    @ManyToMany(cascade = [CascadeType.MERGE]) val links: List<PostgresLinkEntity>,
    @ManyToMany(cascade = [CascadeType.MERGE]) val mentions: List<PostgresUserEntity>,
) : PostgresPublicationEntity(
    id = id,
)

@Entity
@Table(name = "hash_tags")
data class PostgresHashTagEntity(
    @Id
    val value: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PostgresHashTagEntity) return false

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

@Entity
@Table(name = "links")
data class PostgresLinkEntity(
    @Id
    val url: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PostgresLinkEntity) return false

        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        return url.hashCode()
    }
}