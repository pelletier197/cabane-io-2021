package com.qohash.cabaneio2021.model.user

import com.qohash.cabaneio2021.model.contact.ContactInformation
import java.time.Instant

data class Business(
    override val id: UserId,
    override val handle: Handle,
    override val name: String,
    override val joinDate: Instant,
    val numberOfEmployees: UInt,
    val contactInformation: ContactInformation,
    val verified: Boolean,
) : User
