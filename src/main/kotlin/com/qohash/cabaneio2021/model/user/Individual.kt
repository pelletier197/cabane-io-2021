package com.qohash.cabaneio2021.model.user

import java.time.Instant

data class Individual(
    override val id: UserId,
    override val handle: Handle,
    override val name: String,
    override val joinDate: Instant,
    val birthDate: Instant,
    val gender: String,
) : User

