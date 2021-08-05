package com.qohash.cabaneio2021.inserter.postgres.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tweets")
abstract class PostgresPublicationEntity(
    @Id val id: UUID
)