package com.qohash.cabaneio2021.inserter.arangodb.entity

import com.arangodb.springframework.annotation.Document
import com.arangodb.springframework.annotation.Edge
import com.arangodb.springframework.annotation.Relations
import java.time.Instant
import java.util.*
import javax.persistence.*

@Document("users")
abstract class ArangoUserEntity(
    @Id open val id: UUID,
    open val handle: String,
    open val name: String,
    open val joinDate: Instant,
    @Relations(edges = [PostedRelation::class], direction = Relations.Direction.OUTBOUND) open var publications: List<ArangoPublicationEntity>,
    @Relations(edges = [FollowsRelation::class], direction = Relations.Direction.OUTBOUND) open var follows: List<ArangoUserEntity>,
    @Relations(edges = [LikesRelation::class], direction = Relations.Direction.OUTBOUND) open var likes: List<ArangoTweetEntity>
) {
    override fun toString(): String {
        return "ArangoUserEntity(id=$id, handle='$handle', name='$name', joinDate=$joinDate)"
    }
}

@Edge("posted")
class PostedRelation

@Edge("follows")
class FollowsRelation

@Edge("likes")
class LikesRelation

class ArangoIndividualEntity(
    id: UUID,
    handle: String,
    name: String,
    joinDate: Instant,
    publications: List<ArangoPublicationEntity>,
    follows: List<ArangoUserEntity>,
    likes: List<ArangoTweetEntity>,
    val birthDate: Instant,
    val gender: String
) : ArangoUserEntity(
    id = id,
    handle = handle,
    name = name,
    joinDate = joinDate,
    publications = publications,
    follows = follows,
    likes = likes
)

class ArangoBusinessEntity(
    id: UUID,
    handle: String,
    name: String,
    joinDate: Instant,
    publications: List<ArangoPublicationEntity>,
    follows: List<ArangoUserEntity>,
    likes: List<ArangoTweetEntity>,
    val numberOfEmployees: Long,
    val phone: String?,
    val website: String?,
    val websiteName: String?,
    val locationAddressLine: String?,
    val locationCity: String?,
    val locationCountry: String?,
    val verified: Boolean,
) : ArangoUserEntity(
    id = id,
    handle = handle,
    name = name,
    joinDate = joinDate,
    publications = publications,
    follows = follows,
    likes = likes
)