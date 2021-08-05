package com.qohash.cabaneio2021.model.post

import com.qohash.cabaneio2021.model.contact.web.Link
import com.qohash.cabaneio2021.model.post.tags.HashTag
import com.qohash.cabaneio2021.model.user.User

data class Tweet(
    override val id: PublicationId,
    override val author: User,
    val text: String,
    val hashtags: Set<HashTag>,
    val links: Set<Link>,
    val mentions: Set<User>,
    val source: Source,
) : Publication

