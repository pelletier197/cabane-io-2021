package com.qohash.cabaneio2021.model.post

import java.util.*

interface Publication {
    val id: PublicationId
}

@JvmInline
value class PublicationId(
    val value: UUID
)