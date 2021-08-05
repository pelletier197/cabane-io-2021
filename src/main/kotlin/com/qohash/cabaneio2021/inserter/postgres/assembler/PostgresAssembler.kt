package com.qohash.cabaneio2021.inserter.postgres.assembler

import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.postgres.entity.PostgresBusinessEntity
import com.qohash.cabaneio2021.inserter.postgres.entity.PostgresIndividualEntity
import com.qohash.cabaneio2021.inserter.postgres.entity.PostgresPublicationEntity
import com.qohash.cabaneio2021.inserter.postgres.entity.PostgresUserEntity
import com.qohash.cabaneio2021.model.post.Publication
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.Business
import com.qohash.cabaneio2021.model.user.Individual
import com.qohash.cabaneio2021.model.user.User

fun assemble(model: TwitterModel): List<PostgresUserEntity> {
    return model.users.map { it.toPostgres(model) }
}

fun User.toPostgres(model: TwitterModel): PostgresUserEntity {
    return when (this) {
        is Individual -> toIndividualEntity(model)
        is Business -> toBusinessEntity(model)
    }
}

fun Individual.toIndividualEntity(model: TwitterModel): PostgresIndividualEntity {
    return PostgresIndividualEntity(
        id = id.value,
        handle = handle.value,
        name = name,
        joinDate = joinDate,
        birthDate = birthDate,
        gender = gender,
        publications = model.userPublications(this).toPostgres(model),
    )
}

fun Business.toBusinessEntity(model: TwitterModel): PostgresBusinessEntity {
    return PostgresBusinessEntity(
        id = id.value,
        handle = handle.value,
        name = name,
        joinDate = joinDate,
        numberOfEmployees = numberOfEmployees.toLong(),
        phone = contactInformation.phone?.value,
        website = contactInformation.webSite?.link.toString(),
        websiteName = contactInformation.webSite?.name,
        locationAddressLine = contactInformation.location?.addressLine,
        locationCity = contactInformation.location?.city,
        locationCountry = contactInformation.location?.country,
        verified = verified,
        publications = model.userPublications(this).toPostgres(model),
    )
}


fun Collection<Publication>.toPostgres(model: TwitterModel): List<PostgresPublicationEntity> {
    return orEmpty().map { it.toPostgres(model) }
}

fun Publication.toPostgres(model: TwitterModel): PostgresPublicationEntity {
    TODO()
}