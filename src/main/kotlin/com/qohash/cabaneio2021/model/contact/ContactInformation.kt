package com.qohash.cabaneio2021.model.contact

import com.qohash.cabaneio2021.model.contact.web.WebSite

data class ContactInformation(
    val phone: PhoneNumber?,
    val webSite: WebSite?,
    val location: Location?,
)
