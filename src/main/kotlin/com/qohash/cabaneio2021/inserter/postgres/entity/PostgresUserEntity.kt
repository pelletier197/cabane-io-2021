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
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], targetEntity = PostgresPublicationEntity::class)
    open var publications: List<PostgresPublicationEntity>,
    @ManyToMany(cascade = [CascadeType.MERGE], targetEntity = PostgresUserEntity::class)
    open var follows: List<PostgresUserEntity>,
    @ManyToMany(cascade = [CascadeType.MERGE])
    open var likes: List<PostgresTweetEntity>
) {
    override fun toString(): String {
        return "PostgresUserEntity(id=$id, handle='$handle', name='$name', joinDate=$joinDate)"
    }
}

@Entity
@Table(name = "individuals")
@DiscriminatorValue("individual")
class PostgresIndividualEntity(
    id: UUID,
    handle: String,
    name: String,
    joinDate: Instant,
    publications: List<PostgresPublicationEntity>,
    follows: List<PostgresUserEntity>,
    likes: List<PostgresTweetEntity>,
    @Column(name = "birth_date")
    val birthDate: Instant,
    val gender: String
) : PostgresUserEntity(
    id = id,
    handle = handle,
    name = name,
    joinDate = joinDate,
    publications = publications,
    follows = follows,
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
    publications: List<PostgresPublicationEntity>,
    follows: List<PostgresUserEntity>,
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
    publications = publications,
    follows = follows,
    likes = likes
)