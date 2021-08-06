package com.qohash.cabaneio2021.inserter.postgres.entity

import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(
    name = "type",
    discriminatorType = DiscriminatorType.STRING
)
abstract class PostgresUserEntity(
    @Id open val id: UUID,
    open val handle: String,
    open val name: String,
    @Column(name = "join_date")
    open val joinDate: Instant,
    // Unfortunately, one to many does not work when using the abstract class. We need the specific class :(
    @OneToMany(cascade = [CascadeType.ALL])
    open var tweets: List<PostgresTweetEntity>,
    @OneToMany(cascade = [CascadeType.ALL])
    open var retweets: List<PostgresRetweetEntity>,
    @OneToMany(cascade = [CascadeType.ALL])
    open var followsIndividual: List<PostgresIndividualEntity>,
    @OneToMany(cascade = [CascadeType.ALL])
    open var followsBusiness: List<PostgresBusinessEntity>,
    @OneToMany(cascade = [CascadeType.ALL])
    open var likes: List<PostgresTweetEntity>
)

@Entity
@Table(name = "individuals")
@DiscriminatorValue("individual")
class PostgresIndividualEntity(
    id: UUID,
    handle: String,
    name: String,
    joinDate: Instant,
    tweets: List<PostgresTweetEntity>,
    retweets: List<PostgresRetweetEntity>,
    followsIndividual: List<PostgresIndividualEntity>,
    followsBusiness: List<PostgresBusinessEntity>,
    likes: List<PostgresTweetEntity>,
    @Column(name = "birth_date")
    val birthDate: Instant,
    val gender: String
) : PostgresUserEntity(
    id = id,
    handle = handle,
    name = name,
    joinDate = joinDate,
    tweets = tweets,
    retweets = retweets,
    followsIndividual = followsIndividual,
    followsBusiness = followsBusiness,
    likes = likes
)

@Entity
@Table(name = "businesses")
@DiscriminatorValue("business")
class PostgresBusinessEntity(
    id: UUID,
    handle: String,
    name: String,
    joinDate: Instant,
    tweets: List<PostgresTweetEntity>,
    retweets: List<PostgresRetweetEntity>,
    followsIndividual: List<PostgresIndividualEntity>,
    followsBusiness: List<PostgresBusinessEntity>,
    likes: List<PostgresTweetEntity>,
    @Column(name = "number_of_employees")
    val numberOfEmployees: Long,
    val phone: String?,
    val website: String?,
    @Column(name = "website_name")
    val websiteName: String?,
    @Column(name = "location_address_line")
    val locationAddressLine: String?,
    @Column(name = "location_city")
    val locationCity: String?,
    @Column(name = "location_country")
    val locationCountry: String?,
    val verified: Boolean,
) : PostgresUserEntity(
    id = id,
    handle = handle,
    name = name,
    joinDate = joinDate,
    tweets = tweets,
    retweets = retweets,
    followsIndividual = followsIndividual,
    followsBusiness = followsBusiness,
    likes = likes
)