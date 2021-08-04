package com.qohash.cabaneio2021.inserter.postgres.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user")
data class PostgresUserEntity(
    @Id val id: UUID
)