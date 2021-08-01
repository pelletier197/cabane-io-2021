package com.qohash.cabaneio2021.model.user

import java.time.Instant
import java.util.*

interface User {
    val id: UserId
    val handle: Handle
    val name: String
    val joinDate: Instant
}

@JvmInline
value class UserId(
    val value: UUID
)




