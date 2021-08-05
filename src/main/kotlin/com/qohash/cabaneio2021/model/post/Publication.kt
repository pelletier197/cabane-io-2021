package com.qohash.cabaneio2021.model.post

import com.qohash.cabaneio2021.model.user.User
import java.util.*

interface Publication {
    val id: PublicationId
    val author: User
}

@JvmInline
value class PublicationId(
    val value: UUID
)